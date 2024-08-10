package dom.com.thesismonolitserver.dtos;


import dom.com.thesismonolitserver.validators.OnCreate;
import dom.com.thesismonolitserver.validators.OnUpdate;
import dom.com.thesismonolitserver.validators.Phone;
import jakarta.validation.constraints.*;

public class UserRegisterDTO {

    @Null(groups = {OnCreate.class})
    @NotNull(groups = {OnUpdate.class})
    private Long id;

    @NotBlank
    @Size(min=2, max=64)
    private String userName;

    @NotBlank
    private String password;

    @NotBlank
    @Size(min=2, max=64)
    private String firstName;

    @NotBlank
    @Size(min=2, max=64)
    private String lastName;

    @Phone
    private String phoneNumber;

    @Email
    private String emailAddress;

    public UserRegisterDTO() {}

    public UserRegisterDTO(String userName, String password, String firstName,
                           String lastName, String phoneNumber, String emailAddress) {
        this.userName = userName;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
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

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "UserRegisterDTO{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                '}';
    }
}
