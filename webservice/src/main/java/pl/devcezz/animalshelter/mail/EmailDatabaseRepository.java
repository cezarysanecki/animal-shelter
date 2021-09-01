package pl.devcezz.animalshelter.mail;

import io.vavr.control.Option;
import io.vavr.control.Try;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import pl.devcezz.animalshelter.commons.notification.Notification;
import pl.devcezz.animalshelter.mail.model.EmailTemplate;

import static io.vavr.control.Option.none;
import static io.vavr.control.Option.of;

class EmailDatabaseRepository implements EmailRepository {

    private final JdbcTemplate jdbcTemplate;

    EmailDatabaseRepository(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Option<EmailTemplate> findTemplateBy(Notification.NotificationType type) {
        return Try.ofSupplier(() -> of(
                        jdbcTemplate.queryForObject(
                                "SELECT m.subject, m.template FROM shelter_mail m WHERE m.mail_type = ?",
                                new BeanPropertyRowMapper<>(EmailTemplateRow.class),
                                type.name()))
                        .map(EmailTemplateRow::toEmailTemplate))
                .getOrElse(none());
    }
}

class EmailTemplateRow {

    String subject;
    String template;

    EmailTemplate toEmailTemplate() {
        return new EmailTemplate(subject, template);
    }

    public void setTemplate(final String template) {
        this.template = template;
    }

    public void setSubject(final String subject) {
        this.subject = subject;
    }
}