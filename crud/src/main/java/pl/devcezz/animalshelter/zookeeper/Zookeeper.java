package pl.devcezz.animalshelter.zookeeper;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

@Entity
class Zookeeper {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "zookeeper_id", updatable = false, nullable = false)
    private String zookeeperId;

    private String name;
    private String email;

    protected Zookeeper() {}

    public Zookeeper(final String name, final String email) {
        this.name = name;
        this.email = email;
    }

    public UUID toZookeeperId() {
        return UUID.fromString(zookeeperId);
    }

    public String getZookeeperId() {
        return zookeeperId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
