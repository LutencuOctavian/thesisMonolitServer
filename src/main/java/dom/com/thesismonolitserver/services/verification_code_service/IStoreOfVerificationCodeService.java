package dom.com.thesismonolitserver.services.verification_code_service;

import dom.com.thesismonolitserver.enteties.UserDataEntity;

public interface IStoreOfVerificationCodeService {
    void addVerificationCode(Integer verificationCode, UserDataEntity userDataEntity);
    void deleteExpiredVerificationsCodes();
    UserVerificationCode getUserVerificationCode(Integer code);
    void deleteValidatedVerificationCode(Integer code);
}
