package pl.devcezz.shelter.catalogue;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import pl.devcezz.shelter.commons.events.DomainEvents;

@Configuration
@Import({CatalogueDatabaseConfig.class})
public class CatalogueConfig {

    @Bean
    CatalogueDatabase catalogueDatabase(@Qualifier("catalogue") JdbcTemplate jdbcTemplate) {
        return new CatalogueDatabase(jdbcTemplate);
    }

    @Bean
    Catalogue catalogue(
            CatalogueDatabase catalogueDatabase,
            DomainEvents publisher) {
        return new Catalogue(catalogueDatabase, publisher);
    }

    @Bean
    CatalogueEventHandler catalogueEventHandler(
            CatalogueDatabase catalogueDatabase) {
        return new CatalogueEventHandler(catalogueDatabase);
    }
}