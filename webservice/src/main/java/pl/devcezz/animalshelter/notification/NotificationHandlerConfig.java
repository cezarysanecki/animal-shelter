package pl.devcezz.animalshelter.notification;

import io.vavr.collection.HashSet;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Set;

@Configuration(proxyBeanMethods = false)
class NotificationHandlerConfig {

    @Bean
    ZookeeperContactDatabaseRepository zookeeperContactRepository(JdbcTemplate jdbcTemplate) {
        return new ZookeeperContactDatabaseRepository(jdbcTemplate);
    }

    @Bean
    HandleFailedAcceptance handleFailedAcceptance(
            ZookeeperContactRepository zookeeperContactRepository,
            Set<Notifier> notifiers
    ) {
        return new HandleFailedAcceptance(zookeeperContactRepository, HashSet.ofAll(notifiers));
    }

    @Bean
    HandleWarnedAcceptance handleWarnedAcceptance(
            ZookeeperContactRepository zookeeperContactRepository,
            Set<Notifier> notifiers
    ) {
        return new HandleWarnedAcceptance(zookeeperContactRepository, HashSet.ofAll(notifiers));
    }

    @Bean
    HandleSuccessfulAcceptance handleSuccessfulAcceptance(
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

    @Bean
    HandleZookeeperAdded handleZookeeperAdded(
            ZookeeperContactDatabaseRepository zookeeperContactRepository
    ) {
        return new HandleZookeeperAdded(zookeeperContactRepository);
    }

    @Bean
    HandleZookeeperDeleted handleZookeeperDeleted(ZookeeperContactDatabaseRepository zookeeperContactRepository) {
        return new HandleZookeeperDeleted(zookeeperContactRepository);
    }
}
