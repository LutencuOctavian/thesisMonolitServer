package dom.com.thesismonolitserver.enteties;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="privileges")
public class PrivilegeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name="privilege")
    private String privilege;

    @OneToMany(mappedBy = "privilege", fetch = FetchType.LAZY)
    private List<RolePrivilegeEntity> privilegeEntityList;

    @OneToMany(mappedBy = "privilegeEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<UserDataPrivilegeEntity> userDataPrivilegeEntityList;

    public PrivilegeEntity() {}

    public PrivilegeEntity(Long id, String privilege, List<RolePrivilegeEntity> privilegeEntityList, List<UserDataPrivilegeEntity> userDataPrivilegeEntityList) {
        this.id = id;
        this.privilege = privilege;
        this.privilegeEntityList = privilegeEntityList;
        this.userDataPrivilegeEntityList = userDataPrivilegeEntityList;
    }
    public PrivilegeEntity(String privilege) {
        this.privilege = privilege;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPrivilege() {
        return privilege;
    }

    public void setPrivilege(String privilege) {
        this.privilege = privilege;
    }

    public List<RolePrivilegeEntity> getPrivilegeEntityList() {
        return privilegeEntityList;
    }

    public void setPrivilegeEntityList(List<RolePrivilegeEntity> privilegeEntityList) {
        this.privilegeEntityList = privilegeEntityList;
    }

    public List<UserDataPrivilegeEntity> getUserDataPrivilegeEntityList() {
        return userDataPrivilegeEntityList;
    }

    public void setUserDataPrivilegeEntityList(List<UserDataPrivilegeEntity> userDataPrivilegeEntityList) {
        this.userDataPrivilegeEntityList = userDataPrivilegeEntityList;
    }
}
