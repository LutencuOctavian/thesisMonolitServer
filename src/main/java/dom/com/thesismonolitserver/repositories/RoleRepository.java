package dom.com.thesismonolitserver.repositories;

import dom.com.thesismonolitserver.enteties.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {

    @Query("select role.role " +
            "from RoleEntity role " +
            "inner join UserRoleEntity userRole on userRole.role.id = role.id " +
            "where userRole.userDataEntity.id = :userId")
    Optional<List<String>> findRolesStringForUserId(@Param("userId") Long userId);

    @Query("select role " +
            "from RoleEntity role " +
            "where role.role = :roleString")
    Optional<RoleEntity> findRoleByName(@Param("roleString") String roleString);

    @Query("select role " +
            "from RoleEntity role " +
            "inner join UserRoleEntity usrRole on role.id = usrRole.role.id " +
            "where usrRole.userDataEntity.id = :userId")
    Optional<List<RoleEntity>> findRolesForUserId(@Param("userId") Long userId);

    @Query("select role " +
            "from RoleEntity role ")
    Optional<List<RoleEntity>> getAllRolesFromDB();
}
