package dom.com.thesismonolitserver.services.privileges_service;

import dom.com.thesismonolitserver.dtos.UserDTO;
import dom.com.thesismonolitserver.enteties.UserDataEntity;

public interface IPrivilegeService {
    void savePrivilegesForUser(UserDTO userDTO, UserDataEntity userData);

    void deletePrivilegesForUser(UserDTO userDTO, UserDataEntity userFromDB);
}
