package pl.devcezz.shelter.generator;

import pl.devcezz.shelter.generator.dto.ContentType;
import pl.devcezz.shelter.generator.dto.FileType;

record GenerateReportCommand(
        FileType fileType,
        ContentType contentType) {

    String filename() {
        return String.format("%s.%s", contentType.name().toLowerCase(), fileType.name().toLowerCase());
    }
}
