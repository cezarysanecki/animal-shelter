package pl.devcezz.shelter.generator;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class PdfGenerator {

    private final HtmlPreparer htmlPreparer;
    private final HtmlContentGenerator htmlContentGenerator;
    private final PdfCreator pdfCreator;

    byte[] generate() {
        HtmlInput htmlInput = htmlPreparer.process();
        HtmlContent htmlContent = htmlContentGenerator.process(htmlInput);
        return pdfCreator.process(htmlContent);
    }
}
