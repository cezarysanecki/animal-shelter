package pl.devcezz.shelter.generator.pdf;

import io.vavr.collection.HashMap;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import pl.devcezz.shelter.generator.FileGenerator;
import pl.devcezz.shelter.generator.dto.FileType;
import pl.devcezz.shelter.generator.dto.ShelterListData;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static io.vavr.Predicates.instanceOf;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class PdfGenerator implements FileGenerator {

    private final HtmlContentGenerator htmlContentGenerator;
    private final PdfCreator pdfCreator;

    @Override
    public byte[] generate(Object data) {
        HtmlContext htmlContext = Match(data).of(
                Case($(instanceOf(ShelterListData.class)), this::handleShelterList),
                Case($(), () -> {
                    throw new IllegalArgumentException("unhandled kind of data for pdf generator");
                })
        );

        HtmlContent htmlContent = htmlContentGenerator.process(htmlContext);
        return pdfCreator.process(htmlContent);
    }

    private HtmlContext handleShelterList(ShelterListData data) {
        HashMap<String, Object> contextMap = HashMap.of(
                "animals", data.animals(),
                "shelterCapacity", data.shelterCapacity()
        );
        return new HtmlContext("shelter_list", contextMap);
    }

    @Override
    public boolean isApplicable(FileType fileType) {
        return fileType == FileType.Pdf;
    }
}

