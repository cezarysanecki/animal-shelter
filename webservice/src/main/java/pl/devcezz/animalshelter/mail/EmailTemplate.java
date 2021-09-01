package pl.devcezz.animalshelter.mail;

import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;

import javax.mail.internet.MimeMessage;

class EmailTemplate {

    private final EmailContent emailContent;
    private final EmailContentProperties properties;

    private EmailTemplate(final EmailContent emailContent, final EmailContentProperties properties) {
        this.emailContent = emailContent;
        this.properties = properties;
    }

    static EmailTemplateBuilder builder() {
        return new EmailTemplateBuilder();
    }

    MimeMessagePreparator fillWith(String userEmail) {
        return new EmailPreparator(userEmail, emailContent, properties);
    }

    static class EmailTemplateBuilder {

        private EmailContent emailContent;
        private EmailContentProperties properties;

        private EmailTemplateBuilder() {}

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

    class EmailPreparator implements MimeMessagePreparator {

        private final String userEmail;
        private final EmailContent setup;
        private final EmailContentProperties properties;

        private EmailPreparator(final String userEmail, final EmailContent setup, final EmailContentProperties properties) {
            this.userEmail = userEmail;
            this.setup = setup;
            this.properties = properties;
        }

        @Override
        public void prepare(final MimeMessage mimeMessage) throws Exception {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, properties.multipart());
            message.setFrom(properties.from());
            message.setTo(userEmail);
            message.setSubject(setup.subject());
            message.setText(setup.text(), properties.mailHtml());
        }
    }
}
