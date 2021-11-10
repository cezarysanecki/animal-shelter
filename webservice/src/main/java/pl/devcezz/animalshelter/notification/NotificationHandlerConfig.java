package pl.devcezz.animalshelter.notification;

import io.vavr.collection.HashSet;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import pl.devcezz.cqrs.event.EventHandler;

import java.util.Set;

@Configuration(proxyBeanMethods = false)
class NotificationHandlerConfig {

    @Bean
    ZookeeperContactRepository zookeeperContactRepository(JdbcTemplate jdbcTemplate) {
        return new ZookeeperContactDatabaseRepository(jdbcTemplate);
    }

    @Bean
    HandleAcceptanceFailure handleAcceptanceFailure(
            ZookeeperContactRepository zookeeperContactRepository,
            Set<Notifier> notifiers
    ) {
        return new HandleAcceptanceFailure(zookeeperContactRepository, HashSet.ofAll(notifiers));
    }

    @Bean
    HandleAcceptanceWarning handleAcceptanceWarning(
            ZookeeperContactRepository zookeeperContactRepository,
            Set<Notifier> notifiers
    ) {
        return new HandleAcceptanceWarning(zookeeperContactRepository, HashSet.ofAll(notifiers));
    }

    @Bean
    HandleSuccessfulAcceptance handleAcceptanceSuccess(
            ZookeeperContactRepository zookeeperContactRepository,
            Set<Notifier> notifiers
    ) {
        return new HandleSuccessfulAcceptance(zookeeperContactRepository, HashSet.ofAll(notifiers));
    }

    @Bean
    HandleSuccessfulAdoption handleSuccessfulAdoption(
            ZookeeperContactRepository zookeeperContactRepository,
            Set<Notifier> notifiers
    ) {
        return new HandleSuccessfulAdoption(zookeeperContactRepository, HashSet.ofAll(notifiers));
    }
}
