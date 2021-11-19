package pl.devcezz.animalshelter.notification;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.devcezz.animalshelter.notification.mail.EmailFacade;

@Configuration
class NotificationConfig {

    @Bean
    EmailNotifier emailNotifier(EmailFacade emailFacade) {
        return new EmailNotifier(emailFacade);
    }
}
