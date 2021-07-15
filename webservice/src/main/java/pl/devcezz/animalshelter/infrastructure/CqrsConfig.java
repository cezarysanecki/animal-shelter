package pl.devcezz.animalshelter.infrastructure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.devcezz.cqrs.command.AutoCommandsBus;
import pl.devcezz.cqrs.command.CommandHandler;
import pl.devcezz.cqrs.command.CommandsBus;
import pl.devcezz.cqrs.event.AutoEventsBus;
import pl.devcezz.cqrs.event.EventHandler;
import pl.devcezz.cqrs.event.EventsBus;

import java.util.Set;

@Configuration(proxyBeanMethods = false)
class CqrsConfig {

    @Bean
    CommandsBus commandsBus(Set<CommandHandler> commandHandlers) {
        return new AutoCommandsBus(commandHandlers);
    }

    @Bean
    EventsBus eventsBus(Set<EventHandler> eventHandlers) {
        return new AutoEventsBus(eventHandlers);
    }
}
