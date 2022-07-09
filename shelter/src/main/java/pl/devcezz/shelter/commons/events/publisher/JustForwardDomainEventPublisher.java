package pl.devcezz.shelter.commons.events.publisher;

import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import pl.devcezz.shelter.commons.events.DomainEvent;
import pl.devcezz.shelter.commons.events.DomainEvents;

@AllArgsConstructor
public class JustForwardDomainEventPublisher implements DomainEvents {

    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void publish(DomainEvent event) {
        applicationEventPublisher.publishEvent(event);
    }
}
