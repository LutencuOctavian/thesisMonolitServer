package dom.com.thesismonolitserver.repositories;

import dom.com.thesismonolitserver.enteties.UserDataPrivilegeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserDataPrivilegeRepository extends JpaRepository<UserDataPrivilegeEntity, Long> {

    @Modifying
    @Query("delete from UserDataPrivilegeEntity usrPrivilege " +
            "where usrPrivilege.privilegeEntity.id = :privilegeId and usrPrivilege.userDataEntity.id = :userId " )
    void deleteUserPrivilegeByUserAndPrivilegeId(@Param("userId")Long userId,
                                                 @Param("privilegeId") Long privilegeId);
}
