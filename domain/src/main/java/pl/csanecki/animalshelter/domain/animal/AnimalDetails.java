package pl.csanecki.animalshelter.domain.animal;

import lombok.Value;
import pl.csanecki.animalshelter.domain.model.AnimalKind;

import java.time.Instant;

@Value
public class AnimalDetails {

    long id;
    String name;
    AnimalKind kind;
    int age;
    Instant admittedAt;
    Instant adoptedAt;

    public String getKind() {
        return kind.name();
    }
}
