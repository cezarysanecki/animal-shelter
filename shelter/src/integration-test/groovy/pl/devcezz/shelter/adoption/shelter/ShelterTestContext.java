package pl.devcezz.shelter.adoption.shelter;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import pl.devcezz.shelter.adoption.AdoptionDatabaseConfig;
import pl.devcezz.shelter.commons.events.publisher.DomainEventsConfig;
import pl.devcezz.shelter.commons.infrastructure.DatabaseConfig;

@Configuration
@Import({ShelterConfig.class,
        AdoptionDatabaseConfig.class,
        DatabaseConfig.class,
        DomainEventsConfig.class})
public class ShelterTestContext {
}

