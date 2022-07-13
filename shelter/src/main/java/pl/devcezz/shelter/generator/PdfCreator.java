package pl.devcezz.shelter.generator;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.html2pdf.resolver.font.DefaultFontProvider;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

class PdfCreator {

    byte[] process(HtmlContent htmlContent) {
        String pdfTempFilename = tempFilename();

        try (OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(pdfTempFilename))) {
            ConverterProperties converterProperties = new ConverterProperties();
            converterProperties.setFontProvider(new DefaultFontProvider(true, true, true));
            HtmlConverter.convertToPdf(htmlContent.value(), outputStream, converterProperties);

            return Files.readAllBytes(Paths.get(pdfTempFilename));
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private String tempFilename() {
        return System.currentTimeMillis() + ".pdf";
    }
}
