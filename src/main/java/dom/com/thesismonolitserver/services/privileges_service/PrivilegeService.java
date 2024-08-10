package dom.com.thesismonolitserver.services.privileges_service;

import dom.com.thesismonolitserver.dtos.UserDTO;
import dom.com.thesismonolitserver.enteties.PrivilegeEntity;
import dom.com.thesismonolitserver.enteties.UserDataEntity;
import dom.com.thesismonolitserver.exceptions.PrivilegeException;
import dom.com.thesismonolitserver.repositories.PrivilegeRepository;
import dom.com.thesismonolitserver.services.user_privilege_service.IUserPrivilegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("privilegeService")
public class PrivilegeService implements IPrivilegeService {
    private final PrivilegeRepository privilegeRepository;
    private final IUserPrivilegeService userPrivilegeService;

    @Autowired
    public PrivilegeService(PrivilegeRepository privilegeRepository,
                            @Qualifier("userPrivilegeService") IUserPrivilegeService userPrivilegeService) {
        this.privilegeRepository = privilegeRepository;
        this.userPrivilegeService = userPrivilegeService;
    }

    @Override
    public void savePrivilegesForUser(UserDTO userDTO, UserDataEntity userData){
        List<String> privilegesString = userDTO.getPrivileges();
        List<PrivilegeEntity> listOfPrivilegesUserDTO = new java.util.ArrayList<>(privilegesString.stream()
                .map(this::findPrivilegeByName)
                .toList());
        List<PrivilegeEntity> listOfPrivilegesUserFromDB = findPrivilegesForUserId(userData.getId());

        listOfPrivilegesUserFromDB.forEach(listOfPrivilegesUserDTO::remove);
        listOfPrivilegesUserDTO.forEach(privilege->userPrivilegeService.saveUserPrivilege(userData, privilege));
    }

    @Override
    public void deletePrivilegesForUser(UserDTO userDTO, UserDataEntity userFromDB){
        List<String> privilegesString = userDTO.getPrivileges();
        List<PrivilegeEntity> listOfPrivilegesUserDTO = new java.util.ArrayList<>(privilegesString.stream()
                .map(this::findPrivilegeByName)
                .toList());

        listOfPrivilegesUserDTO.forEach(privilege->userPrivilegeService.deleteUserPrivilege(userFromDB, privilege));
    }

    private PrivilegeEntity findPrivilegeByName(String privilege){
        return privilegeRepository.findPrivilegeByName(privilege).orElseThrow(()-> new PrivilegeException("No such privilege: " + privilege));
    }

    private List<PrivilegeEntity> findPrivilegesForUserId(Long userId){
        return privilegeRepository.findPrivilegesForUserId(userId).orElseThrow(()->new PrivilegeException("Privilege not found"));
    }
}
