package pl.devcezz.animalshelter.mail;

import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import pl.devcezz.animalshelter.commons.mail.EmailSender;

import java.util.Properties;

@ConfigurationPropertiesScan("pl.devcezz.animalshelter.mail")
@Configuration
class MailConfig {

    @Bean
    EmailFactory emailFactory() {
        return new EmailFactory();
    }

    @Bean
    MailSender mailSender(EmailProperties emailProperties) {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
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
    EmailSender emailSender(EmailFactory emailFactory, MailSender mailSender) {
        return new EmailSenderImpl(emailFactory, mailSender);
    }
}
