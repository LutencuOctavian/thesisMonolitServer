package dom.com.thesismonolitserver.services.verification_code_service;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class VerificationCodeGenerator {
    private static final int RANGE = 900000;
    private static final int START = 100000;
    private final Random random;

    public VerificationCodeGenerator() {
        this.random = new Random();
    }

    public int getRandomVerificationCode(){
        return random.nextInt(RANGE)+START;
    }
}
