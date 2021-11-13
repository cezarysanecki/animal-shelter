package pl.devcezz.animalshelter.shelter;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;
import pl.devcezz.cqrs.event.EventsBus;

import javax.sql.DataSource;

import static org.mockito.Mockito.mock;

@SpringBootTest(classes = { ShelterConfig.class, ShelterBaseIntegrationTest.Config.class })
@ActiveProfiles("container")
abstract class ShelterBaseIntegrationTest {

    @Container
    private static final MySQLContainer<?> DB_CONTAINER = new MySQLContainer<>(DockerImageName.parse("mysql:8.0.24"))
            .withUsername("test")
            .withPassword("test");

    static {
        DB_CONTAINER.start();
    }

    private final static EventsBus EVENTS_BUS = mock(EventsBus.class);

    @BeforeEach
    void resetMock() {
        Mockito.reset(EVENTS_BUS);
    }

    @Configuration(proxyBeanMethods = false)
    @EnableAutoConfiguration
    static class Config extends ShelterDatabaseConfig {

        @Bean
        EventsBus eventsBus() {
            return EVENTS_BUS;
        }

        @Bean
        DataSource dataSource() {
            return DataSourceBuilder.create()
                    .driverClassName(DB_CONTAINER.getDriverClassName())
                    .url(DB_CONTAINER.getJdbcUrl())
                    .username(DB_CONTAINER.getUsername())
                    .password(DB_CONTAINER.getPassword())
                    .build();
        }
    }
}
