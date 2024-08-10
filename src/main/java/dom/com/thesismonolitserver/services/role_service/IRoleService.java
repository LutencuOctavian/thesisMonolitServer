package dom.com.thesismonolitserver.services.role_service;

import dom.com.thesismonolitserver.dtos.UserDTO;
import dom.com.thesismonolitserver.enteties.UserDataEntity;
import dom.com.thesismonolitserver.exceptions.RoleException;

import java.util.List;

public interface IRoleService {
    List<String> findRolesStringForUserId(Long userId) throws RoleException;
    void saveNewAccountWithRoleUSER(UserDataEntity user);
    void saveRolesForUser(UserDTO userDTO, UserDataEntity userData);
    void deleteRolesForUser(UserDTO userDTO, UserDataEntity userData);
    List<String> getAllRoles();
}
