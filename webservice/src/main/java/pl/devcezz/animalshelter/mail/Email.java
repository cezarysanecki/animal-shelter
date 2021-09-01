package pl.devcezz.animalshelter.mail;

import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import pl.devcezz.animalshelter.mail.model.EmailTemplate;

import javax.mail.internet.MimeMessage;

class Email {

    private final EmailContentProperties properties;
    private final EmailTemplate emailTemplate;

    private Email(final EmailContentProperties properties, final EmailTemplate emailTemplate) {
        this.properties = properties;
        this.emailTemplate = emailTemplate;
    }

    static EmailTemplateBuilder builder() {
        return new EmailTemplateBuilder();
    }

    MimeMessagePreparator fillWith(String userEmail) {
        return new EmailPreparator(properties, emailTemplate, userEmail);
    }

    static class EmailTemplateBuilder {

        private EmailContentProperties properties;
        private EmailTemplate emailTemplate;

        private EmailTemplateBuilder() {}

        EmailContentNeeded template(EmailTemplate emailTemplate) {
            this.emailTemplate = emailTemplate;
            return new EmailContentNeeded();
        }

        class EmailContentNeeded {
            private EmailContentNeeded() {}

            public Email properties(EmailContentProperties props) {
                EmailTemplateBuilder.this.properties = props;
                return new Email(properties, emailTemplate);
            }
        }
    }

    class EmailPreparator implements MimeMessagePreparator {

        private final EmailContentProperties properties;
        private final EmailTemplate emailTemplate;
        private final String userEmail;

        private EmailPreparator(final EmailContentProperties properties,
                                final EmailTemplate emailTemplate,
                                final String userEmail) {
            this.properties = properties;
            this.emailTemplate = emailTemplate;
            this.userEmail = userEmail;
        }

        @Override
        public void prepare(final MimeMessage mimeMessage) throws Exception {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, properties.multipart());
            message.setFrom(properties.from());
            message.setTo(userEmail);
            message.setSubject(emailTemplate.subject());
            message.setText(emailTemplate.content(), properties.mailHtml());
        }
    }
}
