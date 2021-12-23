package pl.devcezz.animalshelter.generator;

import pl.devcezz.animalshelter.generator.dto.GeneratedFileDto;

public class FileGeneratorFacade {

    private final ShelterPdfGenerator shelterPdfGenerator;

    FileGeneratorFacade(final ShelterPdfGenerator shelterPdfGenerator) {
        this.shelterPdfGenerator = shelterPdfGenerator;
    }

    public GeneratedFileDto generateShelterListPdf() {
        byte[] content = shelterPdfGenerator.generate();
        return new GeneratedFileDto(content, "shelter_list.pdf");
    }
}
