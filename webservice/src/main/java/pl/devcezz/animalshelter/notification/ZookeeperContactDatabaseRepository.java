package pl.devcezz.animalshelter.notification;

import io.vavr.collection.Set;
import io.vavr.collection.Stream;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.UUID;

class ZookeeperContactDatabaseRepository implements ZookeeperContactRepository {

    private final JdbcTemplate jdbcTemplate;

    ZookeeperContactDatabaseRepository(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Set<ZookeeperContact> findAll() {
        return Stream.ofAll(
                jdbcTemplate.query(
                        "SELECT z.zookeeper_id, z.email FROM zookeeper_contact z",
                        new BeanPropertyRowMapper<>(ZookeeperContactRow.class)
                ))
                .map(ZookeeperContactRow::toZookeeperContact)
                .toSet();
    }

    public void save(ZookeeperContact zookeeperContact) {
        jdbcTemplate.update("" +
                        "INSERT INTO zookeeper_contact " +
                        "(zookeeper_id, email) VALUES" +
                        "(?, ?)",
                zookeeperContact.zookeeperId().value().toString(),
                zookeeperContact.email()
        );
    }

    public void delete(ZookeeperId zookeeperId) {
        jdbcTemplate.update("" +
                        "DELETE FROM zookeeper_contact " +
                        "WHERE zookeeper_id = ?",
                zookeeperId.value().toString()
        );
    }
}

class ZookeeperContactRow {

    UUID zookeeperId;
    String email;

    ZookeeperContact toZookeeperContact() {
        return new ZookeeperContact(new ZookeeperId(zookeeperId), email);
    }

    public void setZookeeperId(final UUID zookeeperId) {
        this.zookeeperId = zookeeperId;
    }

    public void setEmail(final String email) {
        this.email = email;
    }
}