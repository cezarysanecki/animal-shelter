package pl.devcezz.animalshelter.notification;

import io.vavr.collection.HashSet;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import pl.devcezz.cqrs.event.EventHandler;

import java.util.Set;

class NotificationHandlerConfig {

    @Bean
    ZookeeperContactRepository zookeeperContactRepository(JdbcTemplate jdbcTemplate) {
        return new ZookeeperContactDatabaseRepository(jdbcTemplate);
    }

    @Bean
    EventHandler handleAcceptanceFailure() {
        return new HandleAcceptanceFailure();
    }

    @Bean
    EventHandler handleAcceptanceWarning() {
        return new HandleAcceptanceWarning();
    }

    @Bean
    EventHandler handleAcceptanceSuccess() {
        return new HandleAcceptanceSuccess();
    }

    @Bean
    EventHandler handleSuccessfulAdoption(
            ZookeeperContactRepository zookeeperContactRepository,
            Set<Notifier> notifiers
    ) {
        return new HandleSuccessfulAdoption(zookeeperContactRepository, HashSet.ofAll(notifiers));
    }
}
