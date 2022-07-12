package pl.devcezz.shelter.adoption;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import pl.devcezz.shelter.commons.events.publisher.DomainEventsConfig;
import pl.devcezz.shelter.commons.infrastructure.DatabaseConfig;

@Configuration
@Import({AdoptionConfig.class,
        DatabaseConfig.class,
        DomainEventsConfig.class})
public class AdoptionTestContext {
}

