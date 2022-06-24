package pl.devcezz.shelter.adoption;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import pl.devcezz.shelter.adoption.proposal.infrastructure.ProposalConfig;
import pl.devcezz.shelter.adoption.shelter.infrastructure.ShelterConfig;

@Configuration
@Import({AdoptionDatabaseConfig.class,
        ProposalConfig.class,
        ShelterConfig.class})
public class AdoptionConfig {
}

