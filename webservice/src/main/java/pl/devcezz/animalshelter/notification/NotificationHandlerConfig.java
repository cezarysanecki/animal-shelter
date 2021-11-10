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
    EventHandler handleAcceptanceFailure(
            ZookeeperContactRepository zookeeperContactRepository,
            Set<Notifier> notifiers
    ) {
        return new HandleAcceptanceFailure(zookeeperContactRepository, HashSet.ofAll(notifiers));
    }

    @Bean
    EventHandler handleAcceptanceWarning(
            ZookeeperContactRepository zookeeperContactRepository,
            Set<Notifier> notifiers
    ) {
        return new HandleAcceptanceWarning(zookeeperContactRepository, HashSet.ofAll(notifiers));
    }

    @Bean
    EventHandler handleAcceptanceSuccess(
            ZookeeperContactRepository zookeeperContactRepository,
            Set<Notifier> notifiers
    ) {
        return new HandleSuccessfulAcceptance(zookeeperContactRepository, HashSet.ofAll(notifiers));
    }

    @Bean
    EventHandler handleSuccessfulAdoption(
            ZookeeperContactRepository zookeeperContactRepository,
            Set<Notifier> notifiers
    ) {
        return new HandleSuccessfulAdoption(zookeeperContactRepository, HashSet.ofAll(notifiers));
    }
}
