package pl.devcezz.animalshelter.notification.mail;

import io.vavr.collection.Set;
import org.springframework.mail.javamail.JavaMailSender;
import pl.devcezz.animalshelter.notification.dto.Notification;

public class EmailFacade {

    private final EmailFactory emailFactory;
    private final JavaMailSender mailSender;

    EmailFacade(final EmailFactory emailFactory, final JavaMailSender mailSender) {
        this.emailFactory = emailFactory;
        this.mailSender = mailSender;
    }

    public void sendEmail(final Set<String> emailAddresses, final Notification notification) {
        Email email = emailFactory.createEmailFor(notification);
        emailAddresses.forEach(emailAddress -> mailSender.send(email.fillWith(emailAddress)));
    }
}
