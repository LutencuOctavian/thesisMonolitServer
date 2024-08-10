package dom.com.thesismonolitserver.configuration;

import dom.com.thesismonolitserver.dtos.UserDTO;
import dom.com.thesismonolitserver.dtos.UserRegisterDTO;
import dom.com.thesismonolitserver.enteties.*;
import dom.com.thesismonolitserver.repositories.*;
import dom.com.thesismonolitserver.services.user_service.UserService;
import jakarta.mail.MessagingException;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.context.event.EventListener;
import java.io.IOException;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class SeedDataBase {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    private final PrivilegeRepository privilegeRepository;
    private final RoleRepository roleRepository;
    private final RolePrivilegeRepository rolePrivilegeRepository;
    private final UserRoleRepository userRoleRepository;
    private final UserDataRepository userRepository;

    public SeedDataBase(UserService userService, PasswordEncoder passwordEncoder,
                        PrivilegeRepository privilegeRepository, RoleRepository roleRepository,
                        RolePrivilegeRepository rolePrivilegeRepository, UserRoleRepository userRoleRepository,
                        UserDataRepository userRepository) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.privilegeRepository = privilegeRepository;
        this.roleRepository = roleRepository;
        this.rolePrivilegeRepository = rolePrivilegeRepository;
        this.userRoleRepository = userRoleRepository;
        this.userRepository = userRepository;
    }

    @EventListener
    public void seed(ContextRefreshedEvent event) throws MessagingException, IOException {
        List<UserDTO> allUserWithRolesAndPrivilegesData = userService.getAllUserWithRolesAndPrivilegesData();
        if (!allUserWithRolesAndPrivilegesData.isEmpty()) return;


        RoleEntity adminRole = roleRepository.save(new RoleEntity("ADMIN"));
        RoleEntity userRole = roleRepository.save(new RoleEntity("USER"));

        PrivilegeEntity createNewUserPrivilege = privilegeRepository.save(new PrivilegeEntity("CREATE_NEW_USER"));
        PrivilegeEntity updateUserPrivilege = privilegeRepository.save(new PrivilegeEntity("UPDATE_USER"));
        PrivilegeEntity readUserPrivilege = privilegeRepository.save(new PrivilegeEntity("READ_USER"));
        PrivilegeEntity deleteUserPrivilege = privilegeRepository.save(new PrivilegeEntity("DELETE_USER"));

        PrivilegeEntity createNewCategoryPrivilege = privilegeRepository.save(new PrivilegeEntity("CREATE_NEW_CATEGORY"));
        PrivilegeEntity updateCategoryPrivilege = privilegeRepository.save(new PrivilegeEntity("UPDATE_CATEGORY"));
        PrivilegeEntity readCategoryPrivilege = privilegeRepository.save(new PrivilegeEntity("READ_CATEGORY"));
        PrivilegeEntity deleteCategoryPrivilege = privilegeRepository.save(new PrivilegeEntity("DELETE_CATEGORY"));

        PrivilegeEntity createNewSubcategoryPrivilege = privilegeRepository.save(new PrivilegeEntity("CREATE_NEW_SUBCATEGORY"));
        PrivilegeEntity updateSubcategoryPrivilege = privilegeRepository.save(new PrivilegeEntity("UPDATE_SUBCATEGORY"));
        PrivilegeEntity readSubcategoryPrivilege = privilegeRepository.save(new PrivilegeEntity("READ_SUBCATEGORY"));
        PrivilegeEntity deleteSubcategoryPrivilege = privilegeRepository.save(new PrivilegeEntity("DELETE_SUBCATEGORY"));

        PrivilegeEntity createNewImagePrivilege = privilegeRepository.save(new PrivilegeEntity("CREATE_NEW_IMAGE"));
        PrivilegeEntity updateImagePrivilege = privilegeRepository.save(new PrivilegeEntity("UPDATE_IMAGE"));
        PrivilegeEntity readImagePrivilege = privilegeRepository.save(new PrivilegeEntity("READ_IMAGE"));
        PrivilegeEntity deleteImagePrivilege = privilegeRepository.save(new PrivilegeEntity("DELETE_IMAGE"));

        List<PrivilegeEntity> userPrivilegesList = List.of(createNewCategoryPrivilege, updateCategoryPrivilege, readCategoryPrivilege, deleteCategoryPrivilege,
                                                    createNewSubcategoryPrivilege, updateSubcategoryPrivilege, readSubcategoryPrivilege, deleteSubcategoryPrivilege,
                                                    createNewImagePrivilege, updateImagePrivilege, readImagePrivilege, deleteImagePrivilege);

        List<PrivilegeEntity> adminPrivilegesList = new ArrayList<>(userPrivilegesList);
        adminPrivilegesList.add(createNewUserPrivilege);
        adminPrivilegesList.add(updateUserPrivilege);
        adminPrivilegesList.add(readUserPrivilege);
        adminPrivilegesList.add(deleteUserPrivilege);

        userPrivilegesList.forEach(userPrivilege-> rolePrivilegeRepository.save(new RolePrivilegeEntity(userRole, userPrivilege)));
        adminPrivilegesList.forEach(adminPrivilege-> rolePrivilegeRepository.save(new RolePrivilegeEntity(adminRole, adminPrivilege)));

        UserRegisterDTO userDTO = new UserRegisterDTO("octavian", "octavian", "Octavian", "Lutencu", "+40740912509", "octavv2001@yahoo.com");
        userService.createUser(userDTO);

        UserDataEntity user = userRepository.findById(1L).orElseThrow(() -> new RuntimeException("no such user"));
        user.setValidPhone(true);
        user.setValidEmail(true);
        userRepository.save(user);
        //manual from db
        //userRoleRepository.save(new UserRoleEntity(adminRole, user));
    }
}
