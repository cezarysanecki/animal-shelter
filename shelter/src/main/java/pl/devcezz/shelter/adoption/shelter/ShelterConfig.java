package pl.devcezz.shelter.adoption.shelter;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import pl.devcezz.shelter.adoption.shelter.application.ShelterApplicationConfig;
import pl.devcezz.shelter.adoption.shelter.infrastructure.ShelterDatabaseConfig;
import pl.devcezz.shelter.adoption.shelter.readmodel.ShelterReadModelConfig;
import pl.devcezz.shelter.adoption.shelter.web.ShelterWebConfig;

@Configuration
@Import({ShelterApplicationConfig.class,
        ShelterDatabaseConfig.class,
        ShelterReadModelConfig.class,
        ShelterWebConfig.class})
public class ShelterConfig {
}

