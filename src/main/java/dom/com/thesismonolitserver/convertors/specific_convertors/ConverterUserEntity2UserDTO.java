package dom.com.thesismonolitserver.convertors.specific_convertors;

import dom.com.thesismonolitserver.dtos.UserDTO;
import dom.com.thesismonolitserver.enteties.UserDataEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import dom.com.thesismonolitserver.enteties.*;

import java.util.List;

@Component("converterUserEntity2UserDTO")
public class ConverterUserEntity2UserDTO implements Converter<UserDataEntity, UserDTO> {

    @Override
    public UserDTO convert(UserDataEntity source) {

        List<String> privileges = source.getUserDataPrivilegeEntityList().parallelStream()
                .map(UserDataPrivilegeEntity::getPrivilegeEntity)
                .map(PrivilegeEntity::getPrivilege)
                .filter(privilege -> !privilege.isBlank())
                .toList();

        List<String> roles = source.getUserRoleEntityList().parallelStream()
                .map(UserRoleEntity::getRole)
                .map(RoleEntity::getRole)
                .toList();

        return UserDTO.builder()
                .id(source.getId())
                .userName(source.getUserName())
                .firstName(source.getFirstName())
                .lastName(source.getLastName())
                .emailAddress(source.getEmail())
                .phoneNumber(source.getPhoneNumber())
                .privileges(privileges)
                .roles(roles)
                .build();
    }
}
