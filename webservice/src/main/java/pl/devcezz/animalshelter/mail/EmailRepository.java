package pl.devcezz.animalshelter.mail;

import io.vavr.control.Option;
import pl.devcezz.animalshelter.commons.notification.Notification.NotificationType;
import pl.devcezz.animalshelter.mail.model.EmailData;

public interface EmailRepository {

    Option<EmailData> findEmailDataBy(NotificationType type);
}
