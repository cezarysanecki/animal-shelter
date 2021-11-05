package pl.devcezz.animalshelter.notification;

import io.vavr.collection.Set;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.devcezz.animalshelter.notification.mail.EmailFacade;
import pl.devcezz.cqrs.event.EventHandler;

@Configuration
class NotificationConfig {

    @Bean
    EventHandler handleFailedAcceptance() {
        return new HandleFailedAcceptance();
    }

    @Bean
    EventHandler handleWarnedAcceptance() {
        return new HandleWarnedAcceptance();
    }

    @Bean
    EventHandler handleSucceededAcceptance() {
        return new HandleSucceededAcceptance();
    }

    @Bean
    Notifier emailNotifier(EmailFacade emailFacade) {
        return new EmailNotifier(emailFacade);
    }

    @Bean
    EventHandler handleSuccessfulAdoption(
            ZookeeperContactRepository zookeeperContactRepository,
            Set<Notifier> notifiers
    ) {
        return new SuccessfulAdoptionNotifier(zookeeperContactRepository, notifiers);
    }
}
