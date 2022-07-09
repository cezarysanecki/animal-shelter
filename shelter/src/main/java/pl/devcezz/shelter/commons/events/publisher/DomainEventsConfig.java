package pl.devcezz.shelter.commons.events.publisher;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.devcezz.shelter.commons.events.DomainEvents;

@Configuration
public class DomainEventsConfig {

    @Bean
    DomainEvents domainEvents(ApplicationEventPublisher applicationEventPublisher) {
        return new JustForwardDomainEventPublisher(applicationEventPublisher);
    }
}
