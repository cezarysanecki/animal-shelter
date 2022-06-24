package pl.devcezz.shelter.adoption;

import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJdbcRepositories(
        transactionManagerRef = "adoptionTransactionManager",
        basePackages = {"pl.devcezz.shelter.adoption"},
        jdbcOperationsRef = "adoptionNamedParameterJdbcTemplate"
)
class AdoptionDatabaseConfig {

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource-adoption")
    DataSource adoptionDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    JdbcTemplate adoptionJdbcTemplate(@Qualifier("adoptionDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    NamedParameterJdbcOperations adoptionNamedParameterJdbcTemplate(
            @Qualifier("adoptionDataSource") DataSource dataSource) {
        return new NamedParameterJdbcTemplate(dataSource);
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource-adoption.jpa")
    JpaProperties adoptionJpaProperties() {
        return new JpaProperties();
    }

    @Bean
    PlatformTransactionManager adoptionTransactionManager(
            @Qualifier("adoptionDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource-adoption.liquibase")
    LiquibaseProperties adoptionLiquibaseProperties() {
        return new LiquibaseProperties();
    }

    @Bean
    SpringLiquibase adoptionSpringLiquibase(
            @Qualifier("adoptionDataSource") DataSource dataSource,
            @Qualifier("adoptionLiquibaseProperties") LiquibaseProperties properties) {
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
