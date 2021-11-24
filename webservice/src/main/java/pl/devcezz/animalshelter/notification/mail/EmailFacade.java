package pl.devcezz.animalshelter.notification.mail;

import io.vavr.collection.Set;
import org.springframework.mail.javamail.JavaMailSender;
import pl.devcezz.animalshelter.notification.dto.Notification;

public class EmailFacade {

    private final EmailFactory emailFactory;
    private final EmailAttachmentFactory emailAttachmentFactory;
    private final JavaMailSender mailSender;

    EmailFacade(final EmailFactory emailFactory,
                final EmailAttachmentFactory emailAttachmentFactory,
                final JavaMailSender mailSender) {
        this.emailFactory = emailFactory;
        this.emailAttachmentFactory = emailAttachmentFactory;
        this.mailSender = mailSender;
    }

    public void sendEmail(final Set<String> emailAddresses, final Notification notification) {
        Email email = emailFactory.createEmailFor(notification);
        emailAttachmentFactory.createUsing(notification).forEach(email::addAttachment);
        emailAddresses.forEach(emailAddress -> mailSender.send(email.fillWith(emailAddress)));
    }
}
