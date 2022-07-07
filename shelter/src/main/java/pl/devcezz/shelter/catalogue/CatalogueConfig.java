package pl.devcezz.shelter.catalogue;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import pl.devcezz.shelter.commons.events.DomainEvents;

@Configuration
@Import({CatalogueDatabaseConfig.class})
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class CatalogueConfig {

    private final CatalogueRepository catalogueRepository;
    private final DomainEvents publisher;

    @Bean
    Catalogue catalogue() {
        return new Catalogue(catalogueRepository, publisher);
    }

}