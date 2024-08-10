package dom.com.thesismonolitserver.services.user_role_service;

import dom.com.thesismonolitserver.enteties.RoleEntity;
import dom.com.thesismonolitserver.enteties.UserDataEntity;
import dom.com.thesismonolitserver.enteties.UserRoleEntity;
import dom.com.thesismonolitserver.repositories.UserRoleRepository;
import org.springframework.stereotype.Service;

@Service("userRoleService")
public class UserRoleService implements IUserRoleService {
    private final UserRoleRepository userRoleRepository;

    public UserRoleService(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public void saveUserRole(UserDataEntity user, RoleEntity role){
        userRoleRepository.save(new UserRoleEntity(role, user));
    }

    @Override
    public void deleteUserRole(UserDataEntity user, RoleEntity role){
        userRoleRepository.deleteUserRoleByUserAndRoleId(user.getId(), role.getId());
    }
}
