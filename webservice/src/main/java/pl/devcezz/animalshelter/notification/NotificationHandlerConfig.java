package pl.devcezz.animalshelter.notification;

import io.vavr.collection.HashSet;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Set;

@Configuration(proxyBeanMethods = false)
class NotificationHandlerConfig {

    @Bean
    ContactDatabaseRepository zookeeperContactRepository(JdbcTemplate jdbcTemplate) {
        return new ContactDatabaseRepository(jdbcTemplate);
    }

    @Bean
    HandleFailedAcceptance handleFailedAcceptance(
            ContactRepository contactRepository,
            Set<Notifier> notifiers
    ) {
        return new HandleFailedAcceptance(contactRepository, HashSet.ofAll(notifiers));
    }

    @Bean
    HandleWarnedAcceptance handleWarnedAcceptance(
            ContactRepository contactRepository,
            Set<Notifier> notifiers
    ) {
        return new HandleWarnedAcceptance(contactRepository, HashSet.ofAll(notifiers));
    }

    @Bean
    HandleSuccessfulAcceptance handleSuccessfulAcceptance(
            ContactRepository contactRepository,
            Set<Notifier> notifiers
    ) {
        return new HandleSuccessfulAcceptance(contactRepository, HashSet.ofAll(notifiers));
    }

    @Bean
    HandleSuccessfulAdoption handleSuccessfulAdoption(
            ContactRepository contactRepository,
            Set<Notifier> notifiers
    ) {
        return new HandleSuccessfulAdoption(contactRepository, HashSet.ofAll(notifiers));
    }

    @Bean
    HandleZookeeperAdded handleZookeeperAdded(
            ContactDatabaseRepository zookeeperContactRepository
    ) {
        return new HandleZookeeperAdded(zookeeperContactRepository);
    }

    @Bean
    HandleZookeeperDeleted handleZookeeperDeleted(ContactDatabaseRepository zookeeperContactRepository) {
        return new HandleZookeeperDeleted(zookeeperContactRepository);
    }
}
