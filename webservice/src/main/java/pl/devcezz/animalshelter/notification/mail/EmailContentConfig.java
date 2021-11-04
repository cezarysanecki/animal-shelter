package pl.devcezz.animalshelter.notification.mail;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.thymeleaf.TemplateEngine;
import pl.devcezz.animalshelter.ui.AnimalProjection;

@Configuration
public class EmailContentConfig {

    @Bean
    EmailRepository emailRepository(JdbcTemplate jdbcTemplate) {
        return new EmailDatabaseRepository(jdbcTemplate);
    }

    @Bean
    EmailSchemaFactory contentFactory(AnimalProjection projection, EmailRepository emailRepository) {
        return new EmailSchemaFactory(projection, emailRepository);
    }

    @Bean
    EmailContentFactory emailContentFactory(EmailSchemaFactory emailSchemaFactory,
                                            TemplateEngine templateEngine) {
        return new EmailThymeleafContentFactory(emailSchemaFactory, templateEngine);
    }
}
