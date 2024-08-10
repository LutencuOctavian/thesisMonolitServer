package dom.com.thesismonolitserver.services.role_service;

import dom.com.thesismonolitserver.dtos.UserDTO;
import dom.com.thesismonolitserver.enteties.RoleEntity;
import dom.com.thesismonolitserver.enteties.UserDataEntity;
import dom.com.thesismonolitserver.exceptions.RoleException;
import dom.com.thesismonolitserver.repositories.RoleRepository;
import dom.com.thesismonolitserver.services.user_role_service.IUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("roleService")
public class RoleService implements IRoleService {

    private static final String ROLE_USER = "USER";
    private final RoleRepository roleRepository;
    private final IUserRoleService userRoleService;

    @Autowired
    public RoleService(RoleRepository roleRepository,
                       @Qualifier("userRoleService") IUserRoleService userRoleService) {
        this.roleRepository = roleRepository;
        this.userRoleService = userRoleService;
    }

    @Override
    public List<String> findRolesStringForUserId(Long userId) throws RoleException {
        return roleRepository.findRolesStringForUserId(userId)
                .orElseThrow(() -> new RoleException("Roles not found"));
    }

    @Override
    public void saveNewAccountWithRoleUSER(UserDataEntity user){
        RoleEntity roleOfUser = findRoleByName(ROLE_USER);
        userRoleService.saveUserRole(user, roleOfUser);
    }

    @Override
    public void saveRolesForUser(UserDTO userDTO, UserDataEntity userData){
        List<String> rolesStrings = userDTO.getRoles();
        List<RoleEntity> listOfRolesUserDTO = new java.util.ArrayList<>(rolesStrings.stream()
                .map(this::findRoleByName)
                .toList());
        List<RoleEntity> listOfRolesUserFromDB = findRolesForUserId(userData.getId());

        listOfRolesUserFromDB.forEach(listOfRolesUserDTO::remove);
        listOfRolesUserDTO.forEach(role->userRoleService.saveUserRole(userData, role));
    }

    @Override
    public void deleteRolesForUser(UserDTO userDTO, UserDataEntity userData){
        List<String> rolesStringToBeDeleted = userDTO.getRoles();
        List<RoleEntity> listOfRolesUserDTOToBeDeleted = rolesStringToBeDeleted.stream()
                .map(this::findRoleByName)
                .toList();
        listOfRolesUserDTOToBeDeleted.forEach(roleToBeDeleted-> userRoleService.deleteUserRole(userData, roleToBeDeleted));
    }

    @Override
    public List<String> getAllRoles(){
        List<RoleEntity> roleList = roleRepository.getAllRolesFromDB().orElseThrow(() -> new RoleException("Role table in DB is empty"));
        return roleList.parallelStream()
                .map(RoleEntity::getRole)
                .toList();
    }

    private RoleEntity findRoleByName(String roleString) throws RoleException {
        return roleRepository.findRoleByName(roleString.toUpperCase())
                .orElseThrow(() -> new RoleException("Role not found, doesn't exit such role  " + roleString));
    }

    private List<RoleEntity> findRolesForUserId(Long userId) throws RoleException{
        return roleRepository.findRolesForUserId(userId)
                .orElseThrow(() -> new RoleException("Roles not found"));
    }
}
