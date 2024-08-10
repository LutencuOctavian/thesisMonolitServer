package dom.com.thesismonolitserver.repositories;

import dom.com.thesismonolitserver.enteties.PrivilegeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PrivilegeRepository extends JpaRepository<PrivilegeEntity, Long> {
    @Query("select privilege.privilege " +
            "from PrivilegeEntity as privilege " +
            "inner join RolePrivilegeEntity rolePrivilege on privilege.id = rolePrivilege.privilege.id " +
            "inner join RoleEntity role on role.id = rolePrivilege.role.id " +
            "inner join UserRoleEntity userRole on userRole.role.id = role.id " +
            "where userRole.userDataEntity.id = :userId ")
    Optional<List<String>> findPrivilegesStringForUserId(@Param("userId") Long userId);

    @Query("select privilegeEnt " +
            "from PrivilegeEntity privilegeEnt " +
            "where privilegeEnt.privilege = :privilegeString")
    Optional<PrivilegeEntity> findPrivilegeByName(@Param("privilegeString") String privilegeString);

    @Query("select privilege " +
            "from PrivilegeEntity privilege " +
            "inner join UserDataPrivilegeEntity usrPrivelege on privilege.id = usrPrivelege.privilegeEntity.id " +
            "where usrPrivelege.userDataEntity.id = :userId")
    Optional<List<PrivilegeEntity>> findPrivilegesForUserId(Long userId);

    @Query("select priv.privilege " +
            "from PrivilegeEntity priv " +
            "inner join UserDataPrivilegeEntity usrPriv on usrPriv.privilegeEntity.id = priv.id " +
            "where usrPriv.userDataEntity.id = :userId ")
    Optional<List<String>> findExtraPrivilegesForUser(@Param("userId") Long userId);
}
