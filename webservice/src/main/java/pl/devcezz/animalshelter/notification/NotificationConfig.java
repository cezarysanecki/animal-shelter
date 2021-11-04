package pl.devcezz.animalshelter.notification;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
}
