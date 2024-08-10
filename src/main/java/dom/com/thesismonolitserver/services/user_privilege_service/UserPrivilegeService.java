package dom.com.thesismonolitserver.services.user_privilege_service;

import dom.com.thesismonolitserver.enteties.PrivilegeEntity;
import dom.com.thesismonolitserver.enteties.UserDataEntity;
import dom.com.thesismonolitserver.enteties.UserDataPrivilegeEntity;
import dom.com.thesismonolitserver.repositories.UserDataPrivilegeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userPrivilegeService")
public class UserPrivilegeService implements IUserPrivilegeService {
    private final UserDataPrivilegeRepository userPrivilegeRepository;

    @Autowired
    public UserPrivilegeService(UserDataPrivilegeRepository userPrivilegeRepository) {
        this.userPrivilegeRepository = userPrivilegeRepository;
    }

    @Override
    public void saveUserPrivilege(UserDataEntity user, PrivilegeEntity privilege){
        userPrivilegeRepository.save(new UserDataPrivilegeEntity(user, privilege));
    }
    @Override
    public void deleteUserPrivilege(UserDataEntity userFromDB, PrivilegeEntity privilege){
        userPrivilegeRepository.deleteUserPrivilegeByUserAndPrivilegeId(userFromDB.getId(), privilege.getId());
    }
}
