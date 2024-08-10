package dom.com.thesismonolitserver.enteties;

import jakarta.persistence.*;

@Entity
@Table(name="role_privilege")
public class RolePrivilegeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="role_id")
    private RoleEntity role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="privilege_id")
    private PrivilegeEntity privilege;

    public RolePrivilegeEntity() {}

    public RolePrivilegeEntity(Long id, RoleEntity role, PrivilegeEntity privilege) {
        this.id = id;
        this.role = role;
        this.privilege = privilege;
    }

    public RolePrivilegeEntity(RoleEntity role, PrivilegeEntity privilege) {
        this.role = role;
        this.privilege = privilege;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RoleEntity getRole() {
        return role;
    }

    public void setRole(RoleEntity role) {
        this.role = role;
    }

    public PrivilegeEntity getPrivilege() {
        return privilege;
    }

    public void setPrivilege(PrivilegeEntity privilege) {
        this.privilege = privilege;
    }
}
