package dom.com.thesismonolitserver.services.user_service;

import dom.com.thesismonolitserver.dtos.UserDTO;
import dom.com.thesismonolitserver.dtos.UserRegisterDTO;
import dom.com.thesismonolitserver.enteties.UserDataEntity;
import dom.com.thesismonolitserver.exceptions.InvalidCodeException;
import dom.com.thesismonolitserver.exceptions.RoleException;
import dom.com.thesismonolitserver.exceptions.UserChangedPhoneNumberException;
import dom.com.thesismonolitserver.exceptions.UserException;
import jakarta.mail.MessagingException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import java.io.IOException;
import java.util.List;

public interface IUserService {

    Long createUser(UserRegisterDTO userRegisterDTO) throws RoleException, IOException, MessagingException;
    void userValidateEmail(Long userId, Integer code) throws UsernameNotFoundException, InvalidCodeException, UserException;
    void userValidatePhone(Long userId, Integer code)throws UsernameNotFoundException, InvalidCodeException, UserException;
    void updateUser(UserRegisterDTO userRegisterDTO) throws UsernameNotFoundException, MessagingException, IOException, UserChangedPhoneNumberException;
    void deleteUser(Long userId) throws UsernameNotFoundException, MessagingException;
    List<UserDTO> getAllUserWithRolesAndPrivilegesData();
    void saveNewRolesForUser(UserDTO userDTO);
    void deleteRolesForUser(UserDTO userDTO);
    void saveNewPrivilegesForUser(UserDTO userDTO);
    void deletePrivilegesForUser(UserDTO userDTO);
    UserDataEntity findUserById(Long userId);
}
