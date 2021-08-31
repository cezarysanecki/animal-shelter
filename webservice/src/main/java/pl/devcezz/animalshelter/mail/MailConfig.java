package pl.devcezz.animalshelter.mail;

import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.thymeleaf.TemplateEngine;
import pl.devcezz.animalshelter.commons.mail.EmailSender;

import java.util.Properties;

@ConfigurationPropertiesScan("pl.devcezz.animalshelter.mail")
@Configuration
class MailConfig {

    @Bean
    JavaMailSender mailSender(EmailProperties emailProperties) {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setDefaultEncoding("UTF-8");

        mailSender.setHost(emailProperties.server().host());
        mailSender.setPort(emailProperties.server().port());

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
    EmailContentFactory emailFactory(TemplateEngine templateEngine) {
        return new EmailContentFactory(templateEngine);
    }

    @Bean
    EmailSender emailSender(EmailContentFactory factory, EmailContentProperties properties, JavaMailSender mailSender) {
        return new EmailSenderImpl(factory, properties, mailSender);
    }
}
