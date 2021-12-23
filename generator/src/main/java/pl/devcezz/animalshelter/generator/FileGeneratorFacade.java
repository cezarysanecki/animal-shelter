package pl.devcezz.animalshelter.generator;

import pl.devcezz.animalshelter.generator.csv.ShelterCsvGenerator;
import pl.devcezz.animalshelter.generator.dto.GeneratedFileDto;
import pl.devcezz.animalshelter.generator.pdf.ShelterPdfGenerator;

public class FileGeneratorFacade {

    private final ShelterPdfGenerator shelterPdfGenerator;
    private final ShelterCsvGenerator shelterCsvGenerator;

    FileGeneratorFacade(final ShelterPdfGenerator shelterPdfGenerator, ShelterCsvGenerator shelterCsvGenerator) {
        this.shelterPdfGenerator = shelterPdfGenerator;
        this.shelterCsvGenerator = shelterCsvGenerator;
    }

    public GeneratedFileDto generateShelterListPdf() {
        byte[] content = shelterPdfGenerator.generate();
        return new GeneratedFileDto(content, "shelter_list.pdf");
    }

    public GeneratedFileDto generateShelterListCsv() {
        byte[] content = shelterCsvGenerator.generate();
        return new GeneratedFileDto(content, "shelter_list.csv");
    }
}
