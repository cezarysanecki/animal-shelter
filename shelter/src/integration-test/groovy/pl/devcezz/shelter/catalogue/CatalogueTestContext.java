package pl.devcezz.shelter.catalogue;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import pl.devcezz.shelter.commons.events.publisher.DomainEventsConfig;
import pl.devcezz.shelter.commons.infrastructure.DatabaseConfig;

@Configuration
@Import({CatalogueConfig.class,
        DatabaseConfig.class,
        DomainEventsConfig.class})
class CatalogueTestContext {
}
