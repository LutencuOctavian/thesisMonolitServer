package dom.com.thesismonolitserver.services.verification_code_service;

import dom.com.thesismonolitserver.enteties.UserDataEntity;

import java.time.LocalDateTime;

public class UserVerificationCode {

    private final UserDataEntity userDataEntity;
    private final LocalDateTime localDateTime;
    private final Integer verificationCode;

    public UserVerificationCode(UserDataEntity userDataEntity, Integer verificationCode) {
        this.userDataEntity = userDataEntity;
        this.verificationCode = verificationCode;
        this.localDateTime = LocalDateTime.now();
    }

    public UserDataEntity getUserDataEntity() {
        return userDataEntity;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public Integer getVerificationCode() {
        return verificationCode;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
