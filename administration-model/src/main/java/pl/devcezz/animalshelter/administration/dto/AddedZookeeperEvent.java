package pl.devcezz.animalshelter.administration.dto;

import pl.devcezz.cqrs.event.Event;

import java.util.UUID;

public record AddedZookeeperEvent(UUID zookeeperId, String name,
                                  String email) implements Event {}
