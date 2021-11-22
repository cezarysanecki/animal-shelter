package pl.devcezz.animalshelter.notification.mail;

import io.vavr.collection.List;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

class Email {

    private final EmailContentProperties contentProperties;
    private final EmailContent content;
    private final List<EmailAttachment> attachments;

    private Email(final EmailContentProperties contentProperties, final EmailContent content,
                  final List<EmailAttachment> attachments) {
        this.contentProperties = contentProperties;
        this.content = content;
        this.attachments = attachments;
    }

    static EmailBuilder builder() {
        return new EmailBuilder();
    }

    MimeMessagePreparator fillWith(String userEmail) {
        return new EmailPreparer(contentProperties, content, userEmail, attachments);
    }

    static class EmailBuilder {

        private EmailContentProperties contentProperties;

        private EmailBuilder() {}

        EmailContentPropertiesNeeded contentProperties(EmailContentProperties contentProperties) {
            this.contentProperties = contentProperties;
            return new EmailContentPropertiesNeeded();
        }

        class EmailContentPropertiesNeeded {

            private List<EmailAttachment> attachments;

            private EmailContentPropertiesNeeded() {
                this.attachments = List.empty();
            }

            public EmailContentPropertiesNeeded addAttachment(String filename, File file) {
                this.attachments = attachments.append(new EmailAttachment(filename, file));
                return this;
            }

            public Email content(EmailContent content) {
                return new Email(contentProperties, content, attachments);
            }
        }
    }

    record EmailAttachment(String filename, File file) {
        private void addAttachmentTo(MimeMessageHelper message) {
            try {
                message.addAttachment(filename, file);
            } catch (MessagingException e) {
                throw new RuntimeException("Cannot attach file to email: " + filename);
            }
        }
    }

    class EmailPreparer implements MimeMessagePreparator {

        private final EmailContentProperties contentProperties;
        private final EmailContent content;
        private final String userEmail;
        private final List<EmailAttachment> attachments;

        private EmailPreparer(final EmailContentProperties contentProperties,
                              final EmailContent content,
                              final String userEmail,
                              final List<EmailAttachment> attachments) {
            this.contentProperties = contentProperties;
            this.content = content;
            this.userEmail = userEmail;
            this.attachments = attachments;
        }

        @Override
        public void prepare(final MimeMessage mimeMessage) throws Exception {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, contentProperties.multipart());
            message.setFrom(contentProperties.from());
            message.setTo(userEmail);
            message.setSubject(content.subject());
            message.setText(content.content(), contentProperties.mailHtml());
            attachments.forEach(attachment -> attachment.addAttachmentTo(message));
        }
    }
}
