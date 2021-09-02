package pl.devcezz.animalshelter.mail;

import io.vavr.control.Option;
import io.vavr.control.Try;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import pl.devcezz.animalshelter.commons.notification.Notification;
import pl.devcezz.animalshelter.mail.model.EmailData;

import static io.vavr.control.Option.none;
import static io.vavr.control.Option.of;

class EmailDatabaseRepository implements EmailRepository {

    private final JdbcTemplate jdbcTemplate;

    EmailDatabaseRepository(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Option<EmailData> findEmailDataBy(Notification.NotificationType type) {
        return Try.ofSupplier(() -> of(
                        jdbcTemplate.queryForObject(
                                "SELECT m.subject, m.template_file FROM shelter_mail m WHERE m.mail_type = ?",
                                new BeanPropertyRowMapper<>(EmailDataRow.class),
                                type.name()))
                        .map(EmailDataRow::toEmailData))
                .getOrElse(none());
    }
}

class EmailDataRow {

    String subject;
    String templateFile;

    EmailData toEmailData() {
        return new EmailData(subject, templateFile);
    }

    public void setSubject(final String subject) {
        this.subject = subject;
    }

    public void setTemplateFile(final String templateFile) {
        this.templateFile = templateFile;
    }
}