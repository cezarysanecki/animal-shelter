package pl.devcezz.shelter.adoption;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import pl.devcezz.shelter.adoption.proposal.application.ProposalApplicationConfig;
import pl.devcezz.shelter.adoption.proposal.infrastructure.ProposalDatabaseConfig;
import pl.devcezz.shelter.adoption.shelter.infrastructure.ShelterConfig;

@Configuration
@Import({AdoptionDatabaseConfig.class,
        ProposalApplicationConfig.class,
        ProposalDatabaseConfig.class,
        ShelterConfig.class})
public class AdoptionConfig {
}

