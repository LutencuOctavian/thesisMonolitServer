package dom.com.thesismonolitserver.services.verification_code_service;

import dom.com.thesismonolitserver.enteties.UserDataEntity;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class StoreOfVerificationCode implements IStoreOfVerificationCodeService {

    private static final Long THIRTY_MINUTES = 30L;
    private final Map<Integer, UserVerificationCode> mapVerificationCodeAndUser;

    public StoreOfVerificationCode() {
        this.mapVerificationCodeAndUser = new ConcurrentHashMap<>();
    }

    @Override
    public void addVerificationCode(Integer verificationCode, UserDataEntity userDataEntity){
        mapVerificationCodeAndUser.put(verificationCode, new UserVerificationCode(userDataEntity, verificationCode));
    }

    @Override
    public void deleteExpiredVerificationsCodes(){
        LocalDateTime now = LocalDateTime.now();

        for(Map.Entry<Integer, UserVerificationCode> entry : mapVerificationCodeAndUser.entrySet()){
            Integer verificationCode = entry.getKey();
            UserVerificationCode userVerificationCode = entry.getValue();
            LocalDateTime verificationCodeCreatedAt = userVerificationCode.getLocalDateTime();
            Duration duration = Duration.between(verificationCodeCreatedAt, now);

            if(duration.toMinutes()>THIRTY_MINUTES){
                mapVerificationCodeAndUser.remove(verificationCode);
            }
        }
    }

    @Override
    public UserVerificationCode getUserVerificationCode(Integer code){
        return mapVerificationCodeAndUser.get(code);
    }

    @Override
    public void deleteValidatedVerificationCode(Integer code){
        mapVerificationCodeAndUser.remove(code);
    }
}
