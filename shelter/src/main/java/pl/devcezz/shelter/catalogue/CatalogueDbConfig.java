package pl.devcezz.shelter.catalogue;

import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
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
        entityManagerFactoryRef = "catalogueEntityManagerFactory",
        transactionManagerRef = "catalogueTransactionManager",
        basePackages = {"pl.devcezz.shelter.catalogue"}
)
class CatalogueDbConfig {

    @Primary
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource-catalogue")
    DataSource catalogueDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Primary
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource-catalogue.jpa")
    JpaProperties catalogueJpaProperties() {
        return new JpaProperties();
    }

    @Primary
    @Bean
    LocalContainerEntityManagerFactoryBean catalogueEntityManagerFactory(
            EntityManagerFactoryBuilder entityManagerFactoryBuilder,
            @Qualifier("catalogueDataSource") DataSource dataSource,
            @Qualifier("catalogueJpaProperties") JpaProperties jpaProperties) {
        return entityManagerFactoryBuilder.dataSource(dataSource)
                .packages("pl.devcezz.shelter.catalogue")
                .properties(jpaProperties.getProperties())
                .build();
    }

    @Primary
    @Bean
    PlatformTransactionManager catalogueTransactionManager(
            @Qualifier("catalogueEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
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