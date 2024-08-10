package dom.com.thesismonolitserver.services.sms_service;

import com.google.gson.Gson;
import dom.com.thesismonolitserver.enteties.UserDataEntity;
import dom.com.thesismonolitserver.services.verification_code_service.StoreOfVerificationCodeForPhoneNumberService;
import dom.com.thesismonolitserver.services.verification_code_service.VerificationCodeGenerator;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service("infobipSMSSenderService")
public class InfobipSMSSenderService implements IInfobipSMSSenderService {
    private static final String USER_MESSAGE = "Use verification code: %d for registration";

    @Value("${infobip.apiURL}")
    private String apiURL;

    @Value("${infobip.apiKey}")
    private String apiKey ;

    private final VerificationCodeGenerator verificationCodeGenerator;
    private final StoreOfVerificationCodeForPhoneNumberService storeOfVerificationCodeForPhoneNumberService;

    public InfobipSMSSenderService(VerificationCodeGenerator verificationCodeGenerator, StoreOfVerificationCodeForPhoneNumberService storeOfVerificationCodeForPhoneNumberService) {
        this.verificationCodeGenerator = verificationCodeGenerator;
        this.storeOfVerificationCodeForPhoneNumberService = storeOfVerificationCodeForPhoneNumberService;
    }

    @Override
    public void sendSMS(UserDataEntity user) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();

        RequestBody body = createRequestBody(user);
        Request request = new Request.Builder()
                .url(apiURL)
                .method("POST", body)
                .addHeader("Authorization", apiKey)
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .build();
//        Response response = client.newCall(request).execute();
    }

    private RequestBody createRequestBody(UserDataEntity user){
        int randomInt6Digits = verificationCodeGenerator.getRandomVerificationCode();
        System.out.println(randomInt6Digits);
        storeOfVerificationCodeForPhoneNumberService.addVerificationCode(randomInt6Digits, user);
        SMSMessage smsMessage = new SMSMessage.SMSMessageBuilder()
                .setTo(user.getPhoneNumber())
                .setText(String.format(USER_MESSAGE, randomInt6Digits))
                .build();

        String message = new Gson().toJson(smsMessage);
        MediaType mediaType = MediaType.parse("application/json");
        return RequestBody.create(mediaType, message);
    }
}
