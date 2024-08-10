package dom.com.thesismonolitserver.services.user_role_service;

import dom.com.thesismonolitserver.enteties.RoleEntity;
import dom.com.thesismonolitserver.enteties.UserDataEntity;

public interface IUserRoleService {
    void saveUserRole(UserDataEntity user, RoleEntity role);
    void deleteUserRole(UserDataEntity user, RoleEntity role);
}
