package pl.csanecki.animalshelter.webservice.repository;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;

@Data
@AllArgsConstructor
public class AnimalEntity {

    long id;
    String name;
    String kind;
    int age;
    Instant admittedAt;
    Instant adoptedAt;
}
