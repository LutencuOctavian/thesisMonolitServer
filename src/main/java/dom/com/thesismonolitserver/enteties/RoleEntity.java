package dom.com.thesismonolitserver.enteties;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="roles")
public class RoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name="role")
    private String role;

    @OneToMany(mappedBy = "role", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<RolePrivilegeEntity> rolePrivilegeEntityList;

    @OneToMany(mappedBy = "role")
    private List<UserRoleEntity> userRoleEntityList;

    public RoleEntity() {}

    public RoleEntity(Long id, String role, List<RolePrivilegeEntity> rolePrivilegeEntityList, List<UserRoleEntity> userRoleEntityList) {
        this.id = id;
        this.role = role;
        this.rolePrivilegeEntityList = rolePrivilegeEntityList;
        this.userRoleEntityList = userRoleEntityList;
    }

    public RoleEntity(String role, List<RolePrivilegeEntity> rolePrivilegeEntityList, List<UserRoleEntity> userRoleEntityList) {
        this.role = role;
        this.rolePrivilegeEntityList = rolePrivilegeEntityList;
        this.userRoleEntityList = userRoleEntityList;
    }

    public RoleEntity(String role) {
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<RolePrivilegeEntity> getRolePrivilegeEntityList() {
        return rolePrivilegeEntityList;
    }

    public void setRolePrivilegeEntityList(List<RolePrivilegeEntity> rolePrivilegeEntityList) {
        this.rolePrivilegeEntityList = rolePrivilegeEntityList;
    }

    public List<UserRoleEntity> getUserRoleEntityList() {
        return userRoleEntityList;
    }

    public void setUserRoleEntityList(List<UserRoleEntity> userRoleEntityList) {
        this.userRoleEntityList = userRoleEntityList;
    }
}
