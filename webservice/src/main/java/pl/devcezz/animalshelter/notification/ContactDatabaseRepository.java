package pl.devcezz.animalshelter.notification;

import io.vavr.collection.Set;
import io.vavr.collection.Stream;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.UUID;

class ContactDatabaseRepository implements ContactRepository {

    private final JdbcTemplate jdbcTemplate;

    ContactDatabaseRepository(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Set<Contact> findAll() {
        return Stream.ofAll(
                jdbcTemplate.query(
                        "SELECT c.contact_id, c.email, c.source FROM contact c",
                        new BeanPropertyRowMapper<>(ContactRow.class)
                ))
                .map(ContactRow::toContact)
                .toSet();
    }

    @Override
    public void save(Contact contact) {
        jdbcTemplate.update("" +
                        "INSERT INTO contact " +
                        "(contact_id, email, source) VALUES" +
                        "(?, ?, ?)",
                contact.contactId().value().toString(),
                contact.email(),
                contact.source().name()
        );
    }

    @Override
    public void delete(ContactId contactId, Contact.Source source) {
        jdbcTemplate.update("" +
                        "DELETE FROM contact z " +
                        "WHERE c.contact_id = ? and c.source = ?",
                contactId.value().toString(),
                source.name()
        );
    }
}

class ContactRow {

    UUID contactId;
    String email;
    String source;

    Contact toContact() {
        return new Contact(new ContactId(contactId), email, Contact.Source.of(source));
    }

    public void setContactId(final UUID contactId) {
        this.contactId = contactId;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public void setSource(final String source) {
        this.source = source;
    }
}