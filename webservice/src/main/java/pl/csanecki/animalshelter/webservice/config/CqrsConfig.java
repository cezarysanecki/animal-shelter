package pl.csanecki.animalshelter.webservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.devcezz.cqrs.command.AutoCommandsBus;
import pl.devcezz.cqrs.command.CommandHandler;
import pl.devcezz.cqrs.command.CommandsBus;
import pl.devcezz.cqrs.event.AutoEventsBus;
import pl.devcezz.cqrs.event.EventHandler;
import pl.devcezz.cqrs.event.EventsBus;

import java.util.Set;

@Configuration
class CqrsConfig {

    @Bean
    CommandsBus commandsBus(Set<CommandHandler> handlers) {
        return new AutoCommandsBus(handlers);
    }

    @Bean
    EventsBus eventsBus(Set<EventHandler> handlers) {
        return new AutoEventsBus(handlers);
    }
}
