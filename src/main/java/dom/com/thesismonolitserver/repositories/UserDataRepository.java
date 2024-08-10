package dom.com.thesismonolitserver.repositories;

import dom.com.thesismonolitserver.enteties.UserDataEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserDataRepository extends JpaRepository<UserDataEntity, Long> {

    @Query("select userData" +
            " from UserDataEntity userData" +
            " where userData.userName = :userName")
    Optional<UserDataEntity> findUserByUserName(@Param("userName") String userName);

    @Query("select userData "+
            "from UserDataEntity userData " +
            "where userData.id = :userId" )
    Optional<UserDataEntity> findUserById(@Param("userId") Long userId);

    @Query("select user " +
            "from UserDataEntity user " +
            "inner join fetch user.userRoleEntityList usrRole " +
            "inner join fetch usrRole.role ")
    Optional<List<UserDataEntity>> getAllUserWithRoles();

    @Query("select user " +
            "from UserDataEntity user " +
            "inner join fetch user.userDataPrivilegeEntityList usrPriv " +
            "inner join fetch  usrPriv.privilegeEntity")
    Optional<List<UserDataEntity>> getAllUserWithPrivileges();
}
