package dom.com.thesismonolitserver.services.verification_code_service;

import org.springframework.stereotype.Service;

@Service("storeOfVerificationCodeForEmailService")
public class StoreOfVerificationCodeForEmailService extends StoreOfVerificationCode {
    public StoreOfVerificationCodeForEmailService() {
        super();
    }
}
