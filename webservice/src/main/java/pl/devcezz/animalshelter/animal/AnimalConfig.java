package pl.devcezz.animalshelter.animal;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.devcezz.animalshelter.commons.mail.EmailSender;
import pl.devcezz.animalshelter.read.AnimalProjection;
import pl.devcezz.cqrs.event.EventHandler;

@Configuration(proxyBeanMethods = false)
public class AnimalConfig {

    @Bean
    ShelterFactory shelterFactory(ShelterRepository shelterRepository) {
        return new ShelterFactory(shelterRepository);
    }

    @Bean
    AcceptingAnimal acceptingAnimal(Animals animals, ShelterFactory shelterFactory) {
        return new AcceptingAnimal(animals, shelterFactory);
    }

    @Bean
    EditingAnimal editingAnimal(Animals animals) {
        return new EditingAnimal(animals);
    }

    @Bean
    DeletingAnimal deletingAnimal(Animals animals) {
        return new DeletingAnimal(animals);
    }

    @Bean
    EventHandler handleFailedAcceptance() {
        return new HandleFailedAcceptance();
    }

    @Bean
    EventHandler handleWarnedAcceptance() {
        return new HandleWarnedAcceptance();
    }

    @Bean
    EventHandler handleSucceededAcceptance() {
        return new HandleSucceededAcceptance();
    }

    @Bean
    AdoptingAnimal adoptingAnimal(Animals animals) {
        return new AdoptingAnimal(animals);
    }

    @Bean
    EventHandler handleAdoptingAnimal(EmailSender emailSender, AnimalProjection animalProjection) {
        return new HandleAdoptingAnimal(emailSender, animalProjection);
    }
}
