package pl.devcezz.shelter.catalogue;

import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.jdbc.JdbcRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.jdbc.core.convert.BasicJdbcConverter;
import org.springframework.data.jdbc.core.convert.DataAccessStrategy;
import org.springframework.data.jdbc.core.convert.DefaultDataAccessStrategy;
import org.springframework.data.jdbc.core.convert.DefaultJdbcTypeFactory;
import org.springframework.data.jdbc.core.convert.JdbcConverter;
import org.springframework.data.jdbc.core.convert.JdbcCustomConversions;
import org.springframework.data.jdbc.core.convert.RelationResolver;
import org.springframework.data.jdbc.core.convert.SqlGeneratorSource;
import org.springframework.data.jdbc.core.mapping.JdbcMappingContext;
import org.springframework.data.jdbc.repository.config.DialectResolver;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
import org.springframework.data.relational.core.dialect.Dialect;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@EnableAutoConfiguration(
        exclude = {
                DataSourceAutoConfiguration.class,
                JdbcRepositoriesAutoConfiguration.class})
@EnableJdbcRepositories(
        transactionManagerRef = "catalogueTransactionManager",
        basePackages = {"pl.devcezz.shelter.catalogue"},
        dataAccessStrategyRef = "catalogueDataAccessStrategy",
        jdbcOperationsRef = "catalogueNamedParameterJdbcTemplate")
class CatalogueDatabaseConfig {

    @Bean
    @Qualifier("catalogue")
    @ConfigurationProperties(prefix = "spring.datasource-catalogue")
    DataSource catalogueDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    @Qualifier("catalogue")
    JdbcTemplate catalogueJdbcTemplate(@Qualifier("catalogue") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    @Qualifier("catalogue")
    NamedParameterJdbcOperations catalogueNamedParameterJdbcTemplate(
            @Qualifier("catalogue") DataSource dataSource) {
        return new NamedParameterJdbcTemplate(dataSource);
    }

    @Bean
    @Qualifier("catalogue")
    PlatformTransactionManager catalogueTransactionManager(
            @Qualifier("catalogue") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    @Qualifier("catalogue")
    public DataAccessStrategy catalogueDataAccessStrategy(
            @Qualifier("catalogue") NamedParameterJdbcOperations operations,
            @Qualifier("catalogue") JdbcConverter jdbcConverter,
            JdbcMappingContext context) {
        return new DefaultDataAccessStrategy(
                new SqlGeneratorSource(context, jdbcConverter,
                        DialectResolver.getDialect(operations.getJdbcOperations())),
                context, jdbcConverter, operations);
    }

    @Bean
    @Qualifier("catalogue")
    public JdbcConverter catalogueJdbcConverter(
            JdbcMappingContext mappingContext,
            @Qualifier("catalogue") NamedParameterJdbcOperations operations,
            @Lazy @Qualifier("catalogue") RelationResolver relationResolver,
            JdbcCustomConversions conversions) {
        DefaultJdbcTypeFactory jdbcTypeFactory = new DefaultJdbcTypeFactory(
                operations.getJdbcOperations());
        Dialect dialect = DialectResolver.getDialect(operations.getJdbcOperations());
        return new BasicJdbcConverter(mappingContext, relationResolver, conversions, jdbcTypeFactory,
                dialect.getIdentifierProcessing());
    }

    @Bean
    @Qualifier("catalogue")
    @ConfigurationProperties(prefix = "spring.datasource-catalogue.liquibase")
    LiquibaseProperties catalogueLiquibaseProperties() {
        return new LiquibaseProperties();
    }

    @Bean
    @Qualifier("adoption")
    SpringLiquibase catalogueSpringLiquibase(
            @Qualifier("catalogue") DataSource dataSource,
            @Qualifier("catalogue") LiquibaseProperties properties) {
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