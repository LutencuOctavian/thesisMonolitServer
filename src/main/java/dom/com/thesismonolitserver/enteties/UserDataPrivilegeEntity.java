package dom.com.thesismonolitserver.enteties;

import jakarta.persistence.*;

@Entity
@Table(name="user_privilege")
public class UserDataPrivilegeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private UserDataEntity userDataEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="privilege_id")
    private PrivilegeEntity privilegeEntity;

    public UserDataPrivilegeEntity() {}

    public Long getId() {
        return id;
    }

    public UserDataPrivilegeEntity(UserDataEntity userDataEntity, PrivilegeEntity privilegeEntity) {
        this.userDataEntity = userDataEntity;
        this.privilegeEntity = privilegeEntity;
    }
    public UserDataPrivilegeEntity(Long id, UserDataEntity userDataEntity, PrivilegeEntity privilegeEntity) {
        this.id = id;
        this.userDataEntity = userDataEntity;
        this.privilegeEntity = privilegeEntity;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserDataEntity getUserDataEntity() {
        return userDataEntity;
    }

    public void setUserDataEntity(UserDataEntity userDataEntity) {
        this.userDataEntity = userDataEntity;
    }

    public PrivilegeEntity getPrivilegeEntity() {
        return privilegeEntity;
    }

    public void setPrivilegeEntity(PrivilegeEntity privilegeEntity) {
        this.privilegeEntity = privilegeEntity;
    }
}
