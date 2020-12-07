package pl.csanecki.animalshelter.webservice.repository;

import lombok.Data;

import java.time.Instant;

@Data
public class AnimalEntity {

    int id;
    String name;
    String kind;
    int age;
    Instant admittedAt;
    Instant adoptedAt;
}
