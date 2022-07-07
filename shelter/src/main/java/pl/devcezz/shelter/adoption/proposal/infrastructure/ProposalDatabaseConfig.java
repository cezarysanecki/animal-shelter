package pl.devcezz.shelter.adoption.proposal.infrastructure;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
class ProposalDatabaseConfig {

    @Bean
    ProposalDatabaseRepository proposalDatabaseRepository(@Qualifier("adoption") JdbcTemplate jdbcTemplate) {
        return new ProposalDatabaseRepository(jdbcTemplate);
    }
}
