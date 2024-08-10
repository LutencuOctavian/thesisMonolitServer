package dom.com.thesismonolitserver.services.email_service;

import dom.com.thesismonolitserver.enteties.UserDataEntity;
import dom.com.thesismonolitserver.services.verification_code_service.StoreOfVerificationCodeForEmailService;
import dom.com.thesismonolitserver.services.verification_code_service.VerificationCodeGenerator;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Service("emailSenderServiceGMail")
public class EmailSenderService implements IEmailSenderService {

    private static final String NOTIFICATION_FOR_NEW_USER = "%s, welcome aboard! Let's get you started.";
    private static final String UPDATE_USER_DATA = "Hi %s, admin of platform updated your data.";
    private static final String DELETE_USER = "Hi %s, admin of platform deleted your account.";
    private static final String FROM = "octavv20011@gmail.com";
    private final JavaMailSender emailSender;
    private final SpringTemplateEngine templateEngine;
    private final StoreOfVerificationCodeForEmailService storeOfVerificationCodeForEmailService;
    private final VerificationCodeGenerator verificationCodeGenerator;


    @Autowired
    public EmailSenderService(JavaMailSender emailSender, SpringTemplateEngine templateEngine,
                              StoreOfVerificationCodeForEmailService storeOfVerificationCodeForEmailService,
                              VerificationCodeGenerator verificationCodeGenerator) {
        this.emailSender = emailSender;
        this.templateEngine = templateEngine;
        this.storeOfVerificationCodeForEmailService = storeOfVerificationCodeForEmailService;
        this.verificationCodeGenerator = verificationCodeGenerator;
    }

    @Override
    public void sendEmailNotificationNewUserWasCreated(UserDataEntity userDataEntity) throws MessagingException {
        Integer randomCode = verificationCodeGenerator.getRandomVerificationCode();
        storeOfVerificationCodeForEmailService.addVerificationCode(randomCode, userDataEntity);
        EmailDTO emailDto = createEmailDTOWithRandomCodeNewUserWasCreated(userDataEntity, randomCode);
        sendEmail(emailDto);
    }

    @Override
    public void sendEmailForUpdateUserData(UserDataEntity userDataEntity) throws MessagingException {
        EmailDTO emailDto = createEmailDTOForUpdateUserData(userDataEntity);
        sendEmail(emailDto);
    }

    @Override
    public void sendEmailForDeleteUser(UserDataEntity userDataEntity) throws MessagingException {
        EmailDTO emailDto = createEmailDTOForDeleteUser(userDataEntity);
        sendEmail(emailDto);
    }

    private void sendEmail(EmailDTO emailDto) throws MessagingException{
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,
                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name());

        Context context = new Context();
        context.setVariables(emailDto.getProps());

        String html = templateEngine.process(emailDto.getTemplatePath(), context);
        helper.setTo(emailDto.getTo());
        helper.setFrom(emailDto.getFrom());
        helper.setSubject(emailDto.getSubject());
        helper.setText(html, true);
        emailSender.send(message);
    }


    private EmailDTO createEmailDTOWithRandomCodeNewUserWasCreated(UserDataEntity userDataEntity, Integer randomCode){
        EmailDTO emailDTO = createEmailDTO(userDataEntity);
        Map<String, Object> properties = emailDTO.getProps();
        properties.put("randomCode", randomCode);
        emailDTO.setProps(properties);
        emailDTO.setTemplatePath("email/email_verification");
        emailDTO.setSubject(String.format(NOTIFICATION_FOR_NEW_USER, userDataEntity.getFirstName()));
        return emailDTO;
    }

    private EmailDTO createEmailDTOForUpdateUserData(UserDataEntity userDataEntity){
        EmailDTO emailDTO = createEmailDTO(userDataEntity);
        emailDTO.setSubject(String.format(UPDATE_USER_DATA, userDataEntity.getFirstName()));
        emailDTO.setTemplatePath("email/email_update");
        return emailDTO;
    }

    private EmailDTO createEmailDTOForDeleteUser(UserDataEntity userDataEntity){
        EmailDTO emailDTO = createEmailDTO(userDataEntity);
        emailDTO.setSubject(String.format(DELETE_USER, userDataEntity.getFirstName()));
        emailDTO.setTemplatePath("email/email_delete");
        return emailDTO;
    }

    private EmailDTO createEmailDTO(UserDataEntity userDataEntity) {
        Map<String, Object> properties = new HashMap<>();
        properties.put("firstName", userDataEntity.getFirstName());
        properties.put("userId", userDataEntity.getId());
        return new EmailDTO.EmailDTOBuilder(FROM)
                .to("octavv2001@yahoo.com")
//                .to(userDataEntity.getEmail())
                .props(properties)
                .build();
    }
}
