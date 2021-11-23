package pl.devcezz.animalshelter.generator;

import com.lowagie.text.DocumentException;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

class PdfCreator {

    private final ITextRenderer renderer;

    PdfCreator(final ITextRenderer renderer) {
        this.renderer = renderer;
    }

    byte[] process(HtmlContent htmlContent) {
        String pdfTempFilename = tempFilename();

        try (OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(pdfTempFilename))) {
            renderer.setDocumentFromString(htmlContent.value());
            renderer.layout();
            renderer.createPDF(outputStream);

            return Files.readAllBytes(Paths.get(pdfTempFilename));
        } catch (IOException | DocumentException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private String tempFilename() {
        return System.currentTimeMillis() + ".pdf";
    }
}
