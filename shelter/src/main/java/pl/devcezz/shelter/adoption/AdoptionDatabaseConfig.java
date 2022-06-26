package pl.devcezz.shelter.adoption;

import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.jdbc.JdbcRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jdbc.core.convert.DataAccessStrategy;
import org.springframework.data.jdbc.core.convert.DefaultDataAccessStrategy;
import org.springframework.data.jdbc.core.convert.JdbcConverter;
import org.springframework.data.jdbc.core.convert.SqlGeneratorSource;
import org.springframework.data.jdbc.core.mapping.JdbcMappingContext;
import org.springframework.data.jdbc.repository.config.DialectResolver;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import pl.devcezz.shelter.adoption.proposal.infrastructure.ProposalDatabaseRepository;

import javax.sql.DataSource;

@Configuration
@EnableAutoConfiguration(
        exclude = {DataSourceAutoConfiguration.class, JdbcRepositoriesAutoConfiguration.class}
)
@EnableJdbcRepositories(
        transactionManagerRef = "adoptionTransactionManager",
        basePackages = {"pl.devcezz.shelter.adoption"},
        dataAccessStrategyRef = "adoptionDataAccessStrategy",
        jdbcOperationsRef = "adoptionNamedParameterJdbcTemplate")
class AdoptionDatabaseConfig {

    @Bean
    ProposalDatabaseRepository proposalDatabaseRepository(@Qualifier("adoption") JdbcTemplate jdbcTemplate) {
        return new ProposalDatabaseRepository(jdbcTemplate);
    }

    @Bean
    @Qualifier("adoption")
    JdbcTemplate adoptionJdbcTemplate(@Qualifier("adoption") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    @Qualifier("adoption")
    @ConfigurationProperties(prefix = "spring.datasource-adoption")
    DataSource adoptionDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    @Qualifier("adoption")
    NamedParameterJdbcOperations adoptionNamedParameterJdbcTemplate(
            @Qualifier("adoption") DataSource dataSource) {
        return new NamedParameterJdbcTemplate(dataSource);
    }

    @Bean
    @Qualifier("adoption")
    PlatformTransactionManager adoptionTransactionManager(
            @Qualifier("adoption") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    @Qualifier("adoption")
    public DataAccessStrategy adoptionDataAccessStrategy(
            @Qualifier("adoption") NamedParameterJdbcOperations operations,
            JdbcConverter jdbcConverter,
            JdbcMappingContext context
    ) {
        return new DefaultDataAccessStrategy(
                new SqlGeneratorSource(context, jdbcConverter,
                        DialectResolver.getDialect(operations.getJdbcOperations())),
                context, jdbcConverter, operations);
    }

    @Bean
    @Qualifier("adoption")
    @ConfigurationProperties(prefix = "spring.datasource-adoption.liquibase")
    LiquibaseProperties adoptionLiquibaseProperties() {
        return new LiquibaseProperties();
    }

    @Bean
    @Qualifier("adoption")
    SpringLiquibase adoptionSpringLiquibase(
            @Qualifier("adoption") DataSource dataSource,
            @Qualifier("adoption") LiquibaseProperties properties) {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setDataSource(dataSource);
        liquibase.setChangeLog(properties.getChangeLog());
        liquibase.setContexts(properties.getContexts());
        liquibase.setDefaultSchema(properties.getDefaultSchema());
        liquibase.setDropFirst(properties.isDropFirst());
        liquibase.setShouldRun(properties.isEnabled());
        liquibase.setLabels(properties.getLabels());
        liquibase.setChangeLogParameters(properties.getParameters());
        liquibase.setRollbackFile(properties.getRollbackFile());
        return liquibase;
    }
}
