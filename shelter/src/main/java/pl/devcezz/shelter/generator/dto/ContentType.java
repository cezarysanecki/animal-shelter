package pl.devcezz.shelter.generator.dto;

import java.util.stream.Stream;

public enum ContentType {
    ShelterList;

    public static ContentType of(String contentType) {
        return Stream.of(values())
                .filter(contentTypeEnum -> contentTypeEnum.name().equalsIgnoreCase(contentType))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("unknown content type"));
    }
}