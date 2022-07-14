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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.devcezz.shelter.generator.dto.ContentType;
import pl.devcezz.shelter.generator.dto.FileType;

import javax.validation.constraints.NotBlank;
import java.io.ByteArrayInputStream;

@RestController
@RequestMapping("/shelter/report")
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class ReportGeneratorController {

    private final ReportGeneratorFacade reportGeneratorFacade;

    @PostMapping
    ResponseEntity<Resource> generatePdf(@RequestBody GenerateReportRequest request) {
        GeneratedFile generatedPdfFile = reportGeneratorFacade.generate(
                new GenerateReportCommand(
                        FileType.of(request.fileType()),
                        ContentType.of(request.contentType())));

        InputStreamResource resource = prepareByteResource(generatedPdfFile);
        MediaType mediaType = resolveMediaType(resource);
        HttpHeaders httpHeaders = prepareHttpHeaders(
                generatedPdfFile.filename(), mediaType);

        return new ResponseEntity<>(resource, httpHeaders, HttpStatus.OK);
    }

    private InputStreamResource prepareByteResource(GeneratedFile generatedPdfFile) {
        return new InputStreamResource(new ByteArrayInputStream(generatedPdfFile.content()));
    }

    private MediaType resolveMediaType(InputStreamResource resource) {
        return MediaTypeFactory
                .getMediaType(resource)
                .orElse(MediaType.APPLICATION_OCTET_STREAM);
    }

    private HttpHeaders prepareHttpHeaders(String filename, MediaType mediaType) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(mediaType);
        headers.setContentDisposition(
                ContentDisposition.inline()
                        .filename(filename)
                        .build());
        return headers;
    }
}

record GenerateReportRequest(
        @NotBlank String contentType,
        @NotBlank String fileType) {
}