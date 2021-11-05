package pl.devcezz.animalshelter.notification.mail;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.thymeleaf.TemplateEngine;

import java.util.Properties;

@ConfigurationPropertiesScan("pl.devcezz.animalshelter.notification.mail")
@Configuration
class EmailConfig {

    @ConstructorBinding
    @ConfigurationProperties(prefix = "mail")
    record EmailProperties(EmailServer server, EmailCredentials credentials, EmailSettings settings) {

        record EmailServer(String host, Integer port, String encoding) {}

        record EmailCredentials(String from, String password) {}

        record EmailSettings(String transportProtocol, String startTlsEnabled, String sslEnabled,
                             String authEnabled, String debugEnabled) {}
    }

    @Bean
    JavaMailSender mailSender(EmailProperties emailProperties) {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        mailSender.setHost(emailProperties.server().host());
        mailSender.setPort(emailProperties.server().port());
        mailSender.setDefaultEncoding(emailProperties.server().encoding());

        mailSender.setUsername(emailProperties.credentials().from());
        mailSender.setPassword(emailProperties.credentials().password());

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", emailProperties.settings().transportProtocol());
        props.put("mail.smtp.starttls.enable", emailProperties.settings().startTlsEnabled());
        props.put("mail.smtp.ssl.enable", emailProperties.settings().sslEnabled());
        props.put("mail.smtp.auth", emailProperties.settings().authEnabled());
        props.put("mail.debug", emailProperties.settings().debugEnabled());

        return mailSender;
    }

    @Bean
    EmailDatabaseRepository emailRepository(JdbcTemplate jdbcTemplate) {
        return new EmailDatabaseRepository(jdbcTemplate);
    }

    @Bean
    EmailSchemaFactory contentFactory(EmailDatabaseRepository emailRepository) {
        return new EmailSchemaFactory(emailRepository);
    }

    @Bean
    EmailContentFactory emailContentFactory(EmailSchemaFactory emailSchemaFactory,
                                            TemplateEngine templateEngine) {
        return new EmailContentFactory(emailSchemaFactory, templateEngine);
    }

    @Bean
    EmailFactory emailFactory(EmailContentFactory factory, EmailContentProperties properties) {
        return new EmailFactory(factory, properties);
    }
}
