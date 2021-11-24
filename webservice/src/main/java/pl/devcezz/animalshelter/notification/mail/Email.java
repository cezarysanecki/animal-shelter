package pl.devcezz.animalshelter.notification.mail;

import io.vavr.collection.List;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import pl.devcezz.animalshelter.notification.mail.exception.AddingAttachmentFailedException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

class Email {

    private final EmailContentProperties contentProperties;
    private final EmailContent content;
    private List<EmailAttachment> attachments;

    private Email(final EmailContentProperties contentProperties, final EmailContent content) {
        this.contentProperties = contentProperties;
        this.content = content;
        this.attachments = List.empty();
    }

    static EmailBuilder builder() {
        return new EmailBuilder();
    }

    void addAttachment(EmailAttachment attachment) {
        this.attachments = attachments.append(attachment);
    }

    MimeMessagePreparator fillWith(String userEmail) {
        return new EmailPreparer(contentProperties, content, attachments, userEmail);
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
        private final List<EmailAttachment> attachments;

        private EmailPreparer(final EmailContentProperties contentProperties,
                              final EmailContent content,
                              final List<EmailAttachment> attachments,
                              final String userEmail) {
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
            attachments.forEach(attachment -> {
                try {
                    attachment.attachTo(message);
                } catch (MessagingException e) {
                    throw new AddingAttachmentFailedException(e);
                }
            });
        }
    }
}
