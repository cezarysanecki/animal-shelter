package pl.devcezz.shelter.generator;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;

@RestController
@RequestMapping("/shelter/pdf")
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class PdfGeneratorController {

    private final FileGeneratorFacade fileGeneratorFacade;

    @PostMapping
    ResponseEntity<Resource> generatePdf() {
        GeneratedFileDto generatedFile = fileGeneratorFacade.generateShelterListPdf();

        InputStreamResource resource = new InputStreamResource(new ByteArrayInputStream(generatedFile.content()));
        MediaType mediaType = MediaTypeFactory
                .getMediaType(resource)
                .orElse(MediaType.APPLICATION_OCTET_STREAM);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(mediaType);
        headers.setContentDisposition(
                ContentDisposition
                        .inline()
                        .filename(generatedFile.filename())
                        .build());
        
        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
    }
}
