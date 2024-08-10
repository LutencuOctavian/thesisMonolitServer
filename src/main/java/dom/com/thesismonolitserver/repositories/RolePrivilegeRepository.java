package dom.com.thesismonolitserver.repositories;

import dom.com.thesismonolitserver.enteties.RolePrivilegeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolePrivilegeRepository extends JpaRepository<RolePrivilegeEntity, Long> {
}
