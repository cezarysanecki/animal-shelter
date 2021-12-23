package pl.devcezz.animalshelter.generator;

class ShelterPdfGenerator {

    private final ShelterHtmlPreparer shelterHtmlPreparer;
    private final HtmlContentGenerator htmlContentGenerator;
    private final PdfCreator pdfCreator;

    ShelterPdfGenerator(final ShelterHtmlPreparer shelterHtmlPreparer, final HtmlContentGenerator htmlContentGenerator, final PdfCreator pdfCreator) {
        this.shelterHtmlPreparer = shelterHtmlPreparer;
        this.htmlContentGenerator = htmlContentGenerator;
        this.pdfCreator = pdfCreator;
    }

    byte[] generate() {
        HtmlInput htmlInput = shelterHtmlPreparer.process();
        HtmlContent htmlContent = htmlContentGenerator.process(htmlInput);
        return pdfCreator.process(htmlContent);
    }
}
