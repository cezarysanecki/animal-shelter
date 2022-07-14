package pl.devcezz.shelter.generator;

import java.util.stream.Stream;

public enum FileType {
    Pdf;

    static FileType of(String fileType) {
        return Stream.of(values())
                .filter(fileTypeEnum -> fileTypeEnum.name().equalsIgnoreCase(fileType))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("unknown file type"));
    }
}
