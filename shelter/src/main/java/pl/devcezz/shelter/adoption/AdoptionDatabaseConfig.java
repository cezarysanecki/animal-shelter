package pl.devcezz.shelter.adoption;

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
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "adoptionEntityManagerFactory",
        transactionManagerRef = "adoptionTransactionManager",
        basePackages = {"pl.devcezz.shelter.adoption"}
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
    @ConfigurationProperties(prefix = "spring.datasource-adoption.jpa")
    JpaProperties adoptionJpaProperties() {
        return new JpaProperties();
    }

    @Bean
    LocalContainerEntityManagerFactoryBean adoptionEntityManagerFactory(
            EntityManagerFactoryBuilder entityManagerFactoryBuilder,
            @Qualifier("adoptionDataSource") DataSource dataSource,
            @Qualifier("adoptionJpaProperties") JpaProperties jpaProperties) {
        return entityManagerFactoryBuilder.dataSource(dataSource)
                .packages("pl.devcezz.shelter.adoption")
                .properties(jpaProperties.getProperties())
                .build();
    }

    @Bean
    PlatformTransactionManager adoptionTransactionManager(
            @Qualifier("adoptionEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
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
