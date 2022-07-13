package pl.devcezz.shelter.generator;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class FileGeneratorFacade {

    private final PdfGenerator pdfGenerator;

    public GeneratedFileDto generateShelterListPdf() {
        byte[] content = pdfGenerator.generate();
        return new GeneratedFileDto(content, "shelter_list.pdf");
    }
}
