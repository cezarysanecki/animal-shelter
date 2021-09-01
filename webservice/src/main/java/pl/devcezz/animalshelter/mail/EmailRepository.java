package pl.devcezz.animalshelter.mail;

import io.vavr.control.Option;
import io.vavr.control.Try;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import pl.devcezz.animalshelter.commons.notification.Notification;

import static io.vavr.control.Option.none;
import static io.vavr.control.Option.of;

class EmailRepository {

    private final JdbcTemplate jdbcTemplate;

    EmailRepository(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    Option<EmailContentTemplate> findTemplateBy(Notification.NotificationType type) {
        return Try.ofSupplier(() -> of(
                        jdbcTemplate.queryForObject(
                                "SELECT m.subject, m.template FROM shelter_mail m WHERE m.mail_type = ?",
                                new BeanPropertyRowMapper<>(EmailContentTemplateRow.class),
                                type.name()))
                        .map(EmailContentTemplateRow::toEmailContentTemplate))
                .getOrElse(none());
    }
}

class EmailContentTemplateRow {

    String template;
    String subject;

    EmailContentTemplate toEmailContentTemplate() {
        return new EmailContentTemplate(template, subject);
    }

    public void setTemplate(final String template) {
        this.template = template;
    }

    public void setSubject(final String subject) {
        this.subject = subject;
    }
}