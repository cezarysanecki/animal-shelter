package pl.devcezz.animalshelter.notification.mail;

import io.vavr.control.Option;
import pl.devcezz.animalshelter.notification.dto.Notification.NotificationType;
import pl.devcezz.animalshelter.notification.mail.dto.EmailData;

public interface EmailRepository {

    Option<EmailData> findEmailDataBy(NotificationType type);
}
