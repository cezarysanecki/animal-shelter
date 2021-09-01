package pl.devcezz.animalshelter.mail;

import org.springframework.mail.javamail.JavaMailSender;
import pl.devcezz.animalshelter.commons.notification.Notification;
import pl.devcezz.animalshelter.commons.notification.Notifier;

class EmailNotifier implements Notifier {

    private final EmailFactory emailFactory;
    private final JavaMailSender mailSender;

    EmailNotifier(final EmailFactory emailFactory, final JavaMailSender mailSender) {
        this.emailFactory = emailFactory;
        this.mailSender = mailSender;
    }

    @Override
    public void notify(final Notification notification) {
        Email email = emailFactory.createEmailFor(notification);
        mailSender.send(email.fillWith("csanecki@gmail.com"));
    }
}
