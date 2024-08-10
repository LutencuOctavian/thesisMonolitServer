package dom.com.thesismonolitserver.enteties;

import jakarta.persistence.*;

@Entity
@Table(name="users_roles")
public class UserRoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="role_id")
    private RoleEntity role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private UserDataEntity userDataEntity;

    public UserRoleEntity() {}

    public UserRoleEntity(Long id, RoleEntity role, UserDataEntity userDataEntity) {
        this.id = id;
        this.role = role;
        this.userDataEntity = userDataEntity;
    }

    public UserRoleEntity(RoleEntity role, UserDataEntity userDataEntity) {
        this.role = role;
        this.userDataEntity = userDataEntity;
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

    public UserDataEntity getUserData() {
        return userDataEntity;
    }

    public void setUserData(UserDataEntity userDataEntity) {
        this.userDataEntity = userDataEntity;
    }
}
