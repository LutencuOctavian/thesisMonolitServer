package dom.com.thesismonolitserver.repositories;

import dom.com.thesismonolitserver.enteties.UserRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRoleRepository extends JpaRepository<UserRoleEntity, Long> {
    @Modifying
    @Query("delete from UserRoleEntity usrRole " +
            "where usrRole.role.id = :roleId and usrRole.userDataEntity.id = :userId")
    void deleteUserRoleByUserAndRoleId(@Param("userId") Long userId,
                                       @Param("roleId") Long roleId);
}
