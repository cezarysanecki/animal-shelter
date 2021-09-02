package pl.devcezz.animalshelter.mail.content;

import io.vavr.control.Option;
import pl.devcezz.animalshelter.commons.notification.Notification.NotificationType;

public interface EmailRepository {

    Option<EmailData> findEmailDataBy(NotificationType type);
}
