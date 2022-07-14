package pl.devcezz.shelter.generator;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import pl.devcezz.shelter.generator.dto.ContentType;
import pl.devcezz.shelter.generator.dto.FileType;

import java.util.Set;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class ReportGeneratorFacade {

    private final Set<DataFetcher> dataFetchers;
    private final Set<FileGenerator> fileGenerators;

    GeneratedFile generate(GenerateReportCommand generationType) {
        DataFetcher dataFetcher = findDataFetcherFor(generationType.contentType());
        FileGenerator fileGenerator = findFileGeneratorFor(generationType.fileType());

        Object data = dataFetcher.fetch();
        byte[] content = fileGenerator.generate(data);

        return new GeneratedFile(content, generationType.filename());
    }

    private DataFetcher findDataFetcherFor(ContentType contentType) {
        return dataFetchers.stream()
                .filter(fetcher -> fetcher.isApplicable(contentType))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("not found data fetch for: " + contentType));
    }

    private FileGenerator findFileGeneratorFor(FileType fileType) {
        return fileGenerators.stream()
                .filter(generator -> generator.isApplicable(fileType))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("not found file generator for: " + fileType));
    }
}
