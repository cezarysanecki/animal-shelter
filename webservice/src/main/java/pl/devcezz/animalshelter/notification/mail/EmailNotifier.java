package pl.devcezz.animalshelter.notification.mail;

import io.vavr.collection.Set;
import org.springframework.mail.javamail.JavaMailSender;
import pl.devcezz.animalshelter.administration.dto.ZookeeperContact;
import pl.devcezz.animalshelter.notification.Notifier;
import pl.devcezz.animalshelter.notification.dto.Notification;

class EmailNotifier implements Notifier {

    private final EmailFactory emailFactory;
    private final JavaMailSender mailSender;

    EmailNotifier(final EmailFactory emailFactory, final JavaMailSender mailSender) {
        this.emailFactory = emailFactory;
        this.mailSender = mailSender;
    }

    @Override
    public void notify(final Notification notification, Set<ZookeeperContact> contacts) {
        Email email = emailFactory.createEmailFor(notification);
        contacts.forEach(contact -> mailSender.send(email.fillWith(contact.email().value())));
    }
}
