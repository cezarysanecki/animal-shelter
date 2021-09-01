package pl.devcezz.animalshelter.mail;

import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.thymeleaf.TemplateEngine;
import pl.devcezz.animalshelter.commons.notification.Notifier;
import pl.devcezz.animalshelter.read.AnimalProjection;

import java.util.Properties;

@ConfigurationPropertiesScan("pl.devcezz.animalshelter.mail")
@Configuration
public class EmailConfig {

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
    EmailRepository emailRepository(JdbcTemplate jdbcTemplate) {
        return new EmailDatabaseRepository(jdbcTemplate);
    }

    @Bean
    ContentFactory contentFactory(AnimalProjection projection, EmailRepository emailRepository) {
        return new ContentFactory(projection, emailRepository);
    }

    @Bean
    EmailContentFactory emailContentFactory(ContentFactory contentFactory,
                                            TemplateEngine templateEngine) {
        return new EmailThymeleafContentFactory(contentFactory, templateEngine);
    }

    @Bean
    EmailFactory emailFactory(EmailContentFactory factory, EmailContentProperties properties) {
        return new EmailFactory(factory, properties);
    }

    @Bean
    Notifier emailSender(EmailFactory factory, JavaMailSender mailSender) {
        return new EmailNotifier(factory, mailSender);
    }
}
