package pl.devcezz.animalshelter.notification;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import pl.devcezz.animalshelter.notification.mail.EmailFacade;

@Configuration
@EnableAsync
class NotificationConfig {

    @Bean
    EmailNotifier emailNotifier(EmailFacade emailFacade) {
        return new EmailNotifier(emailFacade);
    }
}
