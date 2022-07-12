package pl.devcezz.shelter.adoption.proposal;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import pl.devcezz.shelter.adoption.proposal.application.ProposalApplicationConfig;
import pl.devcezz.shelter.adoption.proposal.infrastructure.ProposalDatabaseConfig;

@Configuration
@Import({ProposalApplicationConfig.class,
        ProposalDatabaseConfig.class})
public class ProposalConfig {
}

