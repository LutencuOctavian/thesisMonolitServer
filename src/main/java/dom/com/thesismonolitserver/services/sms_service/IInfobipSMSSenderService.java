package dom.com.thesismonolitserver.services.sms_service;

import dom.com.thesismonolitserver.enteties.UserDataEntity;

import java.io.IOException;

public interface IInfobipSMSSenderService {
    void sendSMS(UserDataEntity user) throws IOException;
}
