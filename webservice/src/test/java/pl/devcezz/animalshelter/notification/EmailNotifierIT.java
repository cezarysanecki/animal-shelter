package pl.devcezz.animalshelter.notification;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
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
import static pl.devcezz.animalshelter.notification.NotificationFixture.failedAcceptanceNotification;
import static pl.devcezz.animalshelter.notification.NotificationFixture.successfulAcceptanceNotification;
import static pl.devcezz.animalshelter.notification.NotificationFixture.successfulAdoptionNotification;
import static pl.devcezz.animalshelter.notification.NotificationFixture.warnedAcceptanceNotification;
import static pl.devcezz.animalshelter.notification.NotificationFixture.zookeeperContactsDetails;

@SpringBootTest(classes = { EmailNotifierIT.Config.class, NotificationConfig.class })
@ActiveProfiles("container")
@Testcontainers
class EmailNotifierIT {

    private final static JavaMailSender JAVA_MAIL_SENDER = mock(JavaMailSender.class);

    @Container
    private static final MySQLContainer<?> DB_CONTAINER = new MySQLContainer<>(DockerImageName.parse("mysql:8.0.24"))
            .withUsername("test")
            .withPassword("test");

    static {
        DB_CONTAINER.start();
        System.setProperty("DB_PORT", String.valueOf(DB_CONTAINER.getFirstMappedPort()));
    }

    @BeforeEach
    void resetMock() {
        Mockito.reset(JAVA_MAIL_SENDER);
    }

    @Test
    @Transactional
    @DisplayName("Should send email about animal adoption")
    void should_send_email_about_animal_adoption(
            @Autowired EmailNotifier emailNotifier
    ) {
        emailNotifier.notify(zookeeperContactsDetails(), successfulAdoptionNotification());

        verify(JAVA_MAIL_SENDER).send(any(MimeMessagePreparator.class));
    }

    @Test
    @Transactional
    @DisplayName("Should send email about animal successful acceptance")
    void should_send_email_about_animal_successful_acceptance(
            @Autowired EmailNotifier emailNotifier
    ) {
        emailNotifier.notify(zookeeperContactsDetails(), successfulAcceptanceNotification());

        verify(JAVA_MAIL_SENDER).send(any(MimeMessagePreparator.class));
    }

    @Test
    @Transactional
    @DisplayName("Should send email about animal warned acceptance")
    void should_send_email_about_animal_warned_acceptance(
            @Autowired EmailNotifier emailNotifier
    ) {
        emailNotifier.notify(zookeeperContactsDetails(), warnedAcceptanceNotification());

        verify(JAVA_MAIL_SENDER).send(any(MimeMessagePreparator.class));
    }

    @Test
    @Transactional
    @DisplayName("Should send email about animal failed acceptance")
    void should_send_email_about_animal_failed_acceptance(
            @Autowired EmailNotifier emailNotifier
    ) {
        emailNotifier.notify(zookeeperContactsDetails(), failedAcceptanceNotification());

        verify(JAVA_MAIL_SENDER).send(any(MimeMessagePreparator.class));
    }

    @Configuration(proxyBeanMethods = false)
    @EnableAutoConfiguration
    @ComponentScan("pl.devcezz.animalshelter.notification.mail")
    static class Config {

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