package dom.com.thesismonolitserver.services.sms_service.aux_json_classes;

import java.util.ArrayList;
import java.util.List;

public class Destination {
    private List<To> destinations;

    private final String from = "ServiceSMS";
    private String text;

    public Destination() {
        this.destinations = new ArrayList<>();
    }

    public List<To> getDestinations() {
        return destinations;
    }

    public void setDestinations(List<To> destinations) {
        this.destinations = destinations;
    }

    public String getFrom() {
        return from;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void addNewDestination(String to){
        destinations.add(new To(to));
    }
}
