package dom.com.thesismonolitserver.services.email_service;

import dom.com.thesismonolitserver.enteties.UserDataEntity;
import jakarta.mail.MessagingException;

public interface IEmailSenderService {

    void sendEmailNotificationNewUserWasCreated(UserDataEntity userDataEntity) throws MessagingException;
    void sendEmailForUpdateUserData(UserDataEntity userDataEntity) throws MessagingException;
    void sendEmailForDeleteUser(UserDataEntity userDataEntity) throws MessagingException;
}
