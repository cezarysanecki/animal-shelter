package pl.devcezz.animalshelter.mail;

class EmailTemplateBuilder {

    private EmailContent emailContent;
    private EmailContentProperties properties;

    private EmailTemplateBuilder() {}

    static EmailTemplateBuilder builder() {
        return new EmailTemplateBuilder();
    }

    EmailContentNeeded content(EmailContent emailContent) {
        this.emailContent = emailContent;
        return new EmailContentNeeded();
    }

    class EmailContentNeeded {
        private EmailContentNeeded() {}

        public EmailTemplate properties(EmailContentProperties props) {
            EmailTemplateBuilder.this.properties = props;
            return new EmailTemplate(emailContent, properties);
        }
    }
}


