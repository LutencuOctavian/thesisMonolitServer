package dom.com.thesismonolitserver.services.user_privilege_service;

import dom.com.thesismonolitserver.enteties.PrivilegeEntity;
import dom.com.thesismonolitserver.enteties.UserDataEntity;

public interface IUserPrivilegeService {
    void saveUserPrivilege(UserDataEntity user, PrivilegeEntity privilege);
    void deleteUserPrivilege(UserDataEntity userFromDB, PrivilegeEntity privilege);
}
