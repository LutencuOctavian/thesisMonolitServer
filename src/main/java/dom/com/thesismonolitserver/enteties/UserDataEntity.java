package dom.com.thesismonolitserver.enteties;

import dom.com.thesismonolitserver.dtos.UserRegisterDTO;
import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name="user_data")
public class UserDataEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "userName", unique = true)
    private String userName;

    @Column(name = "password")
    private String password;

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "lastName")
    private String lastName;

    @Column(name = "phoneNumber")
    private String phoneNumber;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "validEmail")
    private Boolean validEmail;

    @Column(name = "validPhone")
    private Boolean validPhone;

    @OneToMany(mappedBy = "userDataEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<UserRoleEntity> userRoleEntityList;

    @OneToMany(mappedBy = "userDataEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<UserDataPrivilegeEntity> userDataPrivilegeEntityList;

    @OneToMany(mappedBy = "userDataEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<CategoryEntity> categoryEntityList;
    public UserDataEntity() {
    }

    public UserDataEntity(Long id, String userName, String password, String firstName,
                          String lastName, String phoneNumber, String email,
                          List<UserRoleEntity> userRoleEntityList,
                          List<UserDataPrivilegeEntity> userDataPrivilegeEntityList,
                          List<CategoryEntity> categoryEntityList) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.userRoleEntityList = userRoleEntityList;
        this.userDataPrivilegeEntityList = userDataPrivilegeEntityList;
        this.validEmail = false;
        this.validPhone = false;
        this.categoryEntityList = categoryEntityList;
    }

    public UserDataEntity(Long id, String userName, String password, String firstName,
                          String lastName, String phoneNumber, String email) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.validEmail = false;
        this.validPhone = false;
    }

    public UserDataEntity(UserRegisterDTO userRegisterDTO) {
        this.userName = userRegisterDTO.getUserName();
        this.password = userRegisterDTO.getPassword();
        this.firstName = userRegisterDTO.getFirstName();
        this.lastName = userRegisterDTO.getLastName();
        this.phoneNumber = userRegisterDTO.getPhoneNumber();
        this.email = userRegisterDTO.getEmailAddress();
        this.validEmail = false;
        this.validPhone = false;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<UserRoleEntity> getUserRoleEntityList() {
        return userRoleEntityList;
    }

    public void setUserRoleEntityList(List<UserRoleEntity> userRoleEntityList) {
        this.userRoleEntityList = userRoleEntityList;
    }

    public List<UserDataPrivilegeEntity> getUserDataPrivilegeEntityList() {
        return userDataPrivilegeEntityList;
    }

    public void setUserDataPrivilegeEntityList(List<UserDataPrivilegeEntity> userDataPrivilegeEntityList) {
        this.userDataPrivilegeEntityList = userDataPrivilegeEntityList;
    }

    public Boolean getValidEmail() {
        return validEmail;
    }

    public void setValidEmail(Boolean validEmail) {
        this.validEmail = validEmail;
    }

    public Boolean getValidPhone() {
        return validPhone;
    }

    public void setValidPhone(Boolean validPhone) {
        this.validPhone = validPhone;
    }

    public List<CategoryEntity> getCategoryEntityList() {
        return categoryEntityList;
    }

    public void setCategoryEntityList(List<CategoryEntity> categoryEntityList) {
        this.categoryEntityList = categoryEntityList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserDataEntity that)) return false;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getUserName(), that.getUserName()) && Objects.equals(getEmail(), that.getEmail());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUserName(), getEmail());
    }
}
