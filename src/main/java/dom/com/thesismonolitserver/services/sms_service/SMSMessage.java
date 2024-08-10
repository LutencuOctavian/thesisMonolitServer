package dom.com.thesismonolitserver.services.sms_service;

import dom.com.thesismonolitserver.services.sms_service.aux_json_classes.Destination;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SMSMessage implements Serializable {

    private List<Destination> messages;

    private SMSMessage(SMSMessageBuilder smsMessageBuilder){
        this.messages = new ArrayList<>();
        messages.add(smsMessageBuilder.destination);
    }

    public static class SMSMessageBuilder{

        private final Destination destination;

        public SMSMessageBuilder() {
            this.destination = new Destination();
        }

        public SMSMessageBuilder setTo(String to){
            destination.addNewDestination(to);
            return this;
        }

        public SMSMessageBuilder setText(String text){
            destination.setText(text);
            return this;
        }

        public SMSMessage build(){
            return new SMSMessage(this);
        }
    }
}
