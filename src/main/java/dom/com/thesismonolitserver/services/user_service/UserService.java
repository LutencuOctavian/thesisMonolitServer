package dom.com.thesismonolitserver.services.user_service;

import dom.com.thesismonolitserver.convertors.GenericConverterType;
import dom.com.thesismonolitserver.dtos.UserDTO;
import dom.com.thesismonolitserver.dtos.UserRegisterDTO;
import dom.com.thesismonolitserver.enteties.UserDataEntity;
import dom.com.thesismonolitserver.enteties.UserDataPrivilegeEntity;
import dom.com.thesismonolitserver.exceptions.InvalidCodeException;
import dom.com.thesismonolitserver.exceptions.RoleException;
import dom.com.thesismonolitserver.exceptions.UserChangedPhoneNumberException;
import dom.com.thesismonolitserver.exceptions.UserException;
import dom.com.thesismonolitserver.repositories.PrivilegeRepository;
import dom.com.thesismonolitserver.repositories.UserDataRepository;
import dom.com.thesismonolitserver.security.UserSecurity;
import dom.com.thesismonolitserver.services.email_service.IEmailSenderService;
import dom.com.thesismonolitserver.services.privileges_service.IPrivilegeService;
import dom.com.thesismonolitserver.services.role_service.IRoleService;
import dom.com.thesismonolitserver.services.sms_service.IInfobipSMSSenderService;
import dom.com.thesismonolitserver.services.verification_code_service.IStoreOfVerificationCodeService;
import dom.com.thesismonolitserver.services.verification_code_service.UserVerificationCode;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service("defaultUserService")
public class UserService implements UserDetailsService, IUserService {

    private final UserDataRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final IRoleService roleService;
    private final PrivilegeRepository privilegeRepository;
    private final IInfobipSMSSenderService infobipSMSSenderService;
    private final IEmailSenderService emailSenderService;
    private final IStoreOfVerificationCodeService storeOfVerificationCodeForPhoneNumberService;
    private final IStoreOfVerificationCodeService storeOfVerificationCodeForEmailService;
    private final IPrivilegeService privilegeService;
    private final GenericConverterType genericConverterType;

    @Autowired
    public UserService(UserDataRepository userRepository, PasswordEncoder passwordEncoder,
                       @Qualifier("roleService") IRoleService roleService, PrivilegeRepository privilegeRepository,
                       @Qualifier("infobipSMSSenderService") IInfobipSMSSenderService infobipSMSSenderService,
                       @Qualifier("emailSenderServiceGMail") IEmailSenderService emailSenderService,
                       @Qualifier("storeOfVerificationCodeForPhoneNumberService") IStoreOfVerificationCodeService storeOfVerificationCodeForPhoneNumberService,
                       @Qualifier("storeOfVerificationCodeForEmailService") IStoreOfVerificationCodeService storeOfVerificationCodeForEmailService,
                       @Qualifier("privilegeService") IPrivilegeService privilegeService,
                       @Qualifier("genericConverterType") GenericConverterType genericConverterType) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
        this.privilegeRepository = privilegeRepository;
        this.infobipSMSSenderService = infobipSMSSenderService;
        this.emailSenderService = emailSenderService;
        this.storeOfVerificationCodeForPhoneNumberService = storeOfVerificationCodeForPhoneNumberService;
        this.storeOfVerificationCodeForEmailService = storeOfVerificationCodeForEmailService;
        this.privilegeService = privilegeService;
        this.genericConverterType = genericConverterType;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserDataEntity user = userRepository.findUserByUserName(username).orElseThrow(() -> new UsernameNotFoundException("User " + username + " not found"));
        Long userId = user.getId();
        List<String> roles = roleService.findRolesStringForUserId(userId);
        List<String> privileges = privilegeRepository.findPrivilegesStringForUserId(userId).orElseThrow(() -> new UsernameNotFoundException("User " + username + " not found PRIVILEGES"));
        List<String> extraPrivilegeForUser = privilegeRepository.findExtraPrivilegesForUser(userId).orElseThrow(() -> new UsernameNotFoundException("User " + username + " not found EXTRA PRIVILEGES"));
        privileges.addAll(extraPrivilegeForUser);
        privileges = privileges.stream()
                .distinct()
                .toList();
        return new UserSecurity(user, roles, privileges);
    }

    @Override
    public List<UserDTO> getAllUserWithRolesAndPrivilegesData(){
        Optional<List<UserDataEntity>> allUserWithRolesOptional = userRepository.getAllUserWithRoles();
        List<UserDataEntity> allUserWithRoles = allUserWithRolesOptional.orElse(new ArrayList<>());
        Optional<List<UserDataEntity>> allUsersWithPrivilegesOptional = userRepository.getAllUserWithPrivileges();
        Map<Long, List<UserDataPrivilegeEntity>> mapOfUserAndListPrivileges = allUsersWithPrivilegesOptional.orElse(new ArrayList<>()).stream()
                .collect(Collectors.toMap(UserDataEntity::getId, UserDataEntity::getUserDataPrivilegeEntityList));

        allUserWithRoles.forEach(user->{
            List<UserDataPrivilegeEntity> userPrivileges = Optional.ofNullable(mapOfUserAndListPrivileges.get(user.getId())).orElse(new ArrayList<>());
            user.setUserDataPrivilegeEntityList(userPrivileges);
        });

        Converter<UserDataEntity, UserDTO> converter = (Converter<UserDataEntity, UserDTO>)genericConverterType.getConverter(UserDTO.class);
        return allUserWithRoles.parallelStream()
                .map(converter::convert)
                .toList();
    }

    @Transactional
    @Override
    public Long createUser(UserRegisterDTO userRegisterDTO) throws RoleException, IOException, MessagingException {
        String password = userRegisterDTO.getPassword();
        userRegisterDTO.setPassword(passwordEncoder.encode(password));
        UserDataEntity newUser = new UserDataEntity(userRegisterDTO);
        UserDataEntity userSavedInDB = userRepository.save(newUser);

        roleService.saveNewAccountWithRoleUSER(userSavedInDB);
        //send message
        infobipSMSSenderService.sendSMS(userSavedInDB);
        //send email
        emailSenderService.sendEmailNotificationNewUserWasCreated(userSavedInDB);

        return userSavedInDB.getId();
    }

    @Override
    public void userValidateEmail(Long userId, Integer code) throws UsernameNotFoundException, InvalidCodeException, UserException{
        UserDataEntity userFromDB = findUserById(userId);
        UserVerificationCode userVerification = Optional.ofNullable(storeOfVerificationCodeForEmailService.getUserVerificationCode(code))
                .orElseThrow(()-> new InvalidCodeException("This code " + code + " is wrong or it was used!"));

        UserDataEntity userFromVerificationStore = userVerification.getUserDataEntity();
        if(! userFromDB.equals(userFromVerificationStore)){
            throw new UserException("User changed path URL => user has bad intentions");
        }

        userFromDB.setValidEmail(true);
        userRepository.save(userFromDB);
        storeOfVerificationCodeForEmailService.deleteValidatedVerificationCode(code);
    }

    @Override
    public void userValidatePhone(Long userId, Integer code)throws UsernameNotFoundException, InvalidCodeException, UserException {
        UserDataEntity userFromDB = findUserById(userId);
        UserVerificationCode userVerification = Optional.ofNullable(storeOfVerificationCodeForPhoneNumberService.getUserVerificationCode(code))
                .orElseThrow(()-> new InvalidCodeException("This code " + code + "is wrong!"));

        UserDataEntity userFromVerificationStore = userVerification.getUserDataEntity();
        if(! userFromDB.equals(userFromVerificationStore)){
            throw new UserException("User changed path URL => user has bad intentions");
        }

        userFromDB.setValidPhone(true);
        userRepository.save(userFromDB);
        storeOfVerificationCodeForPhoneNumberService.deleteValidatedVerificationCode(code);
    }

    @Transactional
    @Override
    public void updateUser(UserRegisterDTO userRegisterDTO) throws UsernameNotFoundException, MessagingException, IOException, UserChangedPhoneNumberException{
        UserDataEntity userFromDB = findUserById(userRegisterDTO.getId());
        boolean hasUserChangedPhoneNumber = updateDataForUser(userFromDB, userRegisterDTO);
        userRepository.save(userFromDB);
        emailSenderService.sendEmailForUpdateUserData(userFromDB);
        if(hasUserChangedPhoneNumber){
            throw new UserChangedPhoneNumberException("User changed phone number");
        }
    }

    @Transactional
    @Override
    public void deleteUser(Long userId) throws UsernameNotFoundException, MessagingException {
        UserDataEntity userFromDB = findUserById(userId);
        userRepository.delete(userFromDB);
        emailSenderService.sendEmailForDeleteUser(userFromDB);
    }

    @Override
    public void saveNewRolesForUser(UserDTO userDTO){
        UserDataEntity userFromDB = findUserById(userDTO.getId());
        roleService.saveRolesForUser(userDTO, userFromDB);
    }

    @Transactional
    @Override
    public void deleteRolesForUser(UserDTO userDTO){
        UserDataEntity userFromDB = findUserById(userDTO.getId());
        roleService.deleteRolesForUser(userDTO, userFromDB);
    }

    @Override
    public void saveNewPrivilegesForUser(UserDTO userDTO){
        UserDataEntity userFromDB = findUserById(userDTO.getId());
        privilegeService.savePrivilegesForUser(userDTO, userFromDB);
    }

    @Transactional
    @Override
    public void deletePrivilegesForUser(UserDTO userDTO){
        UserDataEntity userFromDB = findUserById(userDTO.getId());
        privilegeService.deletePrivilegesForUser(userDTO, userFromDB);
    }

    @Override
    public UserDataEntity findUserById(Long userId){
        return userRepository.findUserById(userId).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    private boolean updateDataForUser(UserDataEntity userFromDB, UserRegisterDTO userRegisterDTO) throws MessagingException, IOException {
        userFromDB.setUserName(userRegisterDTO.getUserName());
        userFromDB.setLastName(userRegisterDTO.getLastName());
        userFromDB.setFirstName(userRegisterDTO.getFirstName());

        //need to think what happened when admin changed password for user
        String password = userRegisterDTO.getPassword();
        userFromDB.setPassword(passwordEncoder.encode(password));

        if(! userFromDB.getEmail().equals(userRegisterDTO.getEmailAddress())){
            userFromDB.setEmail(userRegisterDTO.getEmailAddress());
            userFromDB.setValidEmail(false);
            emailSenderService.sendEmailNotificationNewUserWasCreated(userFromDB);
        }

        if(! userFromDB.getPhoneNumber().equals(userRegisterDTO.getPhoneNumber())){
            userFromDB.setPhoneNumber(userRegisterDTO.getPhoneNumber());
            userFromDB.setValidPhone(false);
            infobipSMSSenderService.sendSMS(userFromDB);
            return true;
        }
        return false;
    }
}
