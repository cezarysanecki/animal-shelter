package pl.csanecki.animalshelter.webservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.csanecki.animalshelter.webservice.foo.ShelterWriteRepository;
import pl.csanecki.animalshelter.webservice.foo.add.AddAnimalCommandHandler;
import pl.csanecki.animalshelter.webservice.foo.adopt.AdoptAnimalCommandHandler;
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
    AddAnimalCommandHandler addAnimalCommandHandler(ShelterWriteRepository shelterWriteRepository) {
        return new AddAnimalCommandHandler(shelterWriteRepository);
    }

    @Bean
    AdoptAnimalCommandHandler adoptAnimalCommandHandler(ShelterWriteRepository shelterWriteRepository) {
        return new AdoptAnimalCommandHandler(shelterWriteRepository);
    }

    @Bean
    CommandsBus commandsBus(Set<CommandHandler> handlers) {
        return new AutoCommandsBus(handlers);
    }

    @Bean
    EventsBus eventsBus(Set<EventHandler> handlers) {
        return new AutoEventsBus(handlers);
    }
}
