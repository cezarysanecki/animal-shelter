package pl.devcezz.animalshelter.mail;

import io.vavr.control.Option;
import pl.devcezz.animalshelter.commons.notification.Notification.NotificationType;
import pl.devcezz.animalshelter.mail.model.EmailTemplate;

public interface EmailRepository {

    Option<EmailTemplate> findTemplateBy(NotificationType type);
}
