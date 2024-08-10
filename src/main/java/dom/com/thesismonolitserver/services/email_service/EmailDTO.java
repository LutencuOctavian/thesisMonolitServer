package dom.com.thesismonolitserver.services.email_service;

import java.util.Map;

public class EmailDTO {
    private String from;
    private String to;
    private String subject;
    private Map<String, Object> props;
    private String templatePath;

    private EmailDTO(EmailDTO.EmailDTOBuilder emailDTOBuilder) {
        this.from = emailDTOBuilder.from;
        this.to = emailDTOBuilder.to;
        this.subject = emailDTOBuilder.subject;
        this.props = emailDTOBuilder.props;
        this.templatePath = emailDTOBuilder.templatePath;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public String getSubject() {
        return subject;
    }

    public Map<String, Object> getProps() {
        return props;
    }

    public String getTemplatePath() {
        return templatePath;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setProps(Map<String, Object> props) {
        this.props = props;
    }

    public void setTemplatePath(String templatePath) {
        this.templatePath = templatePath;
    }

    public static class EmailDTOBuilder{
        private String from;
        private String to;
        private String subject;
        private Map<String, Object> props;
        private String templatePath;

        public EmailDTOBuilder(String from) {
            this.from=from;
        }

        public EmailDTO.EmailDTOBuilder to(String to){
            this.to=to;
            return this;
        }

        public EmailDTO.EmailDTOBuilder subject(String subject){
            this.subject=subject;
            return this;
        }

        public EmailDTO.EmailDTOBuilder props(Map<String, Object> props){
            this.props=props;
            return this;
        }

        public EmailDTO.EmailDTOBuilder templatePath(String templatePath){
            this.templatePath=templatePath;
            return this;
        }

        public EmailDTO build(){
            return new EmailDTO(this);
        }
    }
}
