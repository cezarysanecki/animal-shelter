package pl.devcezz.shelter.catalogue;

import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
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
        transactionManagerRef = "catalogueTransactionManager",
        basePackages = {"pl.devcezz.shelter.catalogue"},
        jdbcOperationsRef = "catalogueNamedParameterJdbcTemplate")
class CatalogueDatabaseConfig {

    @Primary
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource-catalogue")
    DataSource catalogueDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Primary
    @Bean
    JdbcTemplate catalogueJdbcTemplate(@Qualifier("catalogueDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Primary
    @Bean
    NamedParameterJdbcOperations catalogueNamedParameterJdbcTemplate(
            @Qualifier("catalogueDataSource") DataSource dataSource) {
        return new NamedParameterJdbcTemplate(dataSource);
    }

    @Primary
    @Bean
    PlatformTransactionManager catalogueTransactionManager(
            @Qualifier("catalogueDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Primary
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource-catalogue.liquibase")
    LiquibaseProperties catalogueLiquibaseProperties() {
        return new LiquibaseProperties();
    }

    @Primary
    @Bean
    SpringLiquibase catalogueSpringLiquibase(
            @Qualifier("catalogueDataSource") DataSource dataSource,
            @Qualifier("catalogueLiquibaseProperties") LiquibaseProperties properties) {
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