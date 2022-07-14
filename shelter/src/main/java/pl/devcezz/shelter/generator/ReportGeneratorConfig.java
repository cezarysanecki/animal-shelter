package pl.devcezz.shelter.generator;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import pl.devcezz.shelter.generator.csv.CsvFileGeneratorConfig;
import pl.devcezz.shelter.generator.dto.ContentType;
import pl.devcezz.shelter.generator.dto.FileType;
import pl.devcezz.shelter.generator.external.GeneratorExternalConfig;
import pl.devcezz.shelter.generator.pdf.PdfFileGeneratorConfig;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Configuration
@Import({
        GeneratorExternalConfig.class,
        PdfFileGeneratorConfig.class,
        CsvFileGeneratorConfig.class})
public class ReportGeneratorConfig {

    @Bean
    ReportGeneratorFacade fileGeneratorFacade(
            Set<DataFetcher> dataFetchers,
            Set<FileGenerator> fileGenerators) {
        checkIfMoreFetchersForOneContentType(dataFetchers);
        checkIfMoreGeneratorsForOneFileType(fileGenerators);

        return new ReportGeneratorFacade(dataFetchers, fileGenerators);
    }

    private void checkIfMoreFetchersForOneContentType(Set<DataFetcher> dataFetchers) {
        Map<ContentType, Long> countedContentTypes = Stream.of(ContentType.values())
                .collect(Collectors.toMap(
                        contentType -> contentType,
                        contentType -> dataFetchers.stream()
                                .filter(dataFetcher -> dataFetcher.isApplicable(contentType))
                                .count()));

        if (moreThanOneCount(countedContentTypes.values())) {
            throw new IllegalStateException("too much fetchers for one content type");
        }
    }

    private void checkIfMoreGeneratorsForOneFileType(Set<FileGenerator> fileGenerators) {
        Map<FileType, Long> countedFileTypes = Stream.of(FileType.values())
                .collect(Collectors.toMap(
                        fileType -> fileType,
                        fileType -> fileGenerators.stream()
                                .filter(fileGenerator -> fileGenerator.isApplicable(fileType))
                                .count()));

        if (moreThanOneCount(countedFileTypes.values())) {
            throw new IllegalStateException("too much generators for one file type");
        }
    }

    private boolean moreThanOneCount(Collection<Long> counts) {
        return !counts.stream()
                .filter(count -> count > 1)
                .toList()
                .isEmpty();
    }
}
