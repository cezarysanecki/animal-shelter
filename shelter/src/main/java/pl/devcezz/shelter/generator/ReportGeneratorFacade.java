package pl.devcezz.shelter.generator;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import pl.devcezz.shelter.generator.pdf.PdfGenerator;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class ReportGeneratorFacade {

    private final DataFetcher dataFetcher;
    private final PdfGenerator pdfGenerator;

    GeneratedFile generate(GenerateReportCommand generationType) {
        Object data = dataFetcher.fetch(generationType.contentType());

        byte[] content = switch (generationType.fileType()) {
            case Pdf -> pdfGenerator.generatePdf(data);
        };
        return new GeneratedFile(content, generationType.filename());
    }
}
