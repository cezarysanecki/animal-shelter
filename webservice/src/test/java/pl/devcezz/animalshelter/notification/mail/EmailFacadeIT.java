package pl.devcezz.animalshelter.notification.mail;

import io.vavr.collection.HashSet;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import javax.sql.DataSource;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static pl.devcezz.animalshelter.notification.mail.EmailFixture.successfulAdoptionNotification;

@SpringBootTest(classes = { EmailFacadeIT.Config.class })
@ActiveProfiles("container")
@Testcontainers
class EmailFacadeIT {

    private final static JavaMailSender JAVA_MAIL_SENDER = mock(JavaMailSender.class);

    @Container
    private static final MySQLContainer<?> DB_CONTAINER = new MySQLContainer<>(DockerImageName.parse("mysql:8.0.24"))
            .withUsername("test")
            .withPassword("test");

    static {
        DB_CONTAINER.start();
        System.setProperty("DB_PORT", String.valueOf(DB_CONTAINER.getFirstMappedPort()));
    }

    @Test
    @Transactional
    @DisplayName("Should send email about animal adoption")
    void should_send_email_about_animal_adoption(
            @Autowired EmailFacade emailFacade
    ) {
        emailFacade.sendEmail(HashSet.of("zookeeper@mail.com"), successfulAdoptionNotification());

        verify(JAVA_MAIL_SENDER).send(any(MimeMessagePreparator.class));
    }

    @Configuration(proxyBeanMethods = false)
    @EnableAutoConfiguration
    static class Config extends EmailConfig {

        @Bean
        JavaMailSender javaMailSender() {
            return JAVA_MAIL_SENDER;
        }

        @Bean
        DataSource dataSource() {
            return DataSourceBuilder.create()
                    .url(DB_CONTAINER.getJdbcUrl())
                    .username(DB_CONTAINER.getUsername())
                    .password(DB_CONTAINER.getPassword())
                    .build();
        }
    }
}