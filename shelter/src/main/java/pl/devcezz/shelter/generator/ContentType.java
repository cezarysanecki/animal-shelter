package pl.devcezz.shelter.generator;

import java.util.stream.Stream;

public enum ContentType {
    ShelterList;

    static ContentType of(String contentType) {
        return Stream.of(values())
                .filter(contentTypeEnum -> contentTypeEnum.name().equalsIgnoreCase(contentType))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("unknown content type"));
    }
}