package pl.devcezz.shelter.generator.external;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.devcezz.shelter.adoption.shelter.readmodel.ShelterReadModel;
import pl.devcezz.shelter.catalogue.CatalogueReadModelRepository;
import pl.devcezz.shelter.generator.DataFetcher;

@Configuration
public class GeneratorExternalConfig {

    @Bean
    DataFetcher dataFetcher(ShelterReadModel shelterReadModel,
                            CatalogueReadModelRepository catalogueReadModelRepository) {
        ShelterListDataFetcher shelterListDataFetcher = new ShelterListDataFetcher(
                shelterReadModel, catalogueReadModelRepository);
        return new ShelterDataFetcher(shelterListDataFetcher);
    }
}
