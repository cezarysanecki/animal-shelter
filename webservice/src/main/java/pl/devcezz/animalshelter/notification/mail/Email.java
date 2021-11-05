package pl.devcezz.animalshelter.notification.mail;

import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;

import javax.mail.internet.MimeMessage;

class Email {

    private final EmailContentProperties contentProperties;
    private final EmailContent content;

    private Email(final EmailContentProperties contentProperties, final EmailContent content) {
        this.contentProperties = contentProperties;
        this.content = content;
    }

    static EmailBuilder builder() {
        return new EmailBuilder();
    }

    MimeMessagePreparator fillWith(String userEmail) {
        return new EmailPreparer(contentProperties, content, userEmail);
    }

    static class EmailBuilder {

        private EmailContentProperties contentProperties;

        private EmailBuilder() {}

        EmailContentPropertiesNeeded contentProperties(EmailContentProperties contentProperties) {
            this.contentProperties = contentProperties;
            return new EmailContentPropertiesNeeded();
        }

        class EmailContentPropertiesNeeded {
            private EmailContentPropertiesNeeded() {}

            public Email content(EmailContent content) {
                return new Email(contentProperties, content);
            }
        }
    }

    class EmailPreparer implements MimeMessagePreparator {

        private final EmailContentProperties contentProperties;
        private final EmailContent content;
        private final String userEmail;

        private EmailPreparer(final EmailContentProperties contentProperties,
                              final EmailContent content,
                              final String userEmail) {
            this.contentProperties = contentProperties;
            this.content = content;
            this.userEmail = userEmail;
        }

        @Override
        public void prepare(final MimeMessage mimeMessage) throws Exception {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, contentProperties.multipart());
            message.setFrom(contentProperties.from());
            message.setTo(userEmail);
            message.setSubject(content.subject());
            message.setText(content.content(), contentProperties.mailHtml());
        }
    }
}
