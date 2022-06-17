package pl.devcezz.shelter.proposal;

import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "proposalEntityManagerFactory",
        transactionManagerRef = "proposalTransactionManager",
        basePackages = {"pl.devcezz.shelter.proposal"}
)
class ProposalDbConfig {

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource-proposal")
    DataSource proposalDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource-proposal.jpa")
    JpaProperties proposalJpaProperties() {
        return new JpaProperties();
    }

    @Bean
    LocalContainerEntityManagerFactoryBean proposalEntityManagerFactory(
            EntityManagerFactoryBuilder entityManagerFactoryBuilder,
            @Qualifier("proposalDataSource") DataSource dataSource,
            @Qualifier("proposalJpaProperties") JpaProperties jpaProperties) {
        return entityManagerFactoryBuilder.dataSource(dataSource)
                .packages("pl.devcezz.shelter.proposal")
                .properties(jpaProperties.getProperties())
                .build();
    }

    @Bean
    PlatformTransactionManager proposalTransactionManager(
            @Qualifier("proposalEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource-proposal.liquibase")
    LiquibaseProperties proposalLiquibaseProperties() {
        return new LiquibaseProperties();
    }

    @Bean
    SpringLiquibase proposalSpringLiquibase(
            @Qualifier("proposalDataSource") DataSource dataSource,
            @Qualifier("proposalLiquibaseProperties") LiquibaseProperties properties) {
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
