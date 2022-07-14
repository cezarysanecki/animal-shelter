package pl.devcezz.shelter.generator;

import pl.devcezz.shelter.generator.dto.FileType;

public interface FileGenerator {

    byte[] generate(Object data);

    boolean isApplicable(FileType fileType);
}
