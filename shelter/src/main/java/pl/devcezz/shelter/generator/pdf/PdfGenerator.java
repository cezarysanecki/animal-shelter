package pl.devcezz.shelter.generator.pdf;

import io.vavr.collection.HashMap;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import pl.devcezz.shelter.generator.dto.ShelterListData;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static io.vavr.Predicates.instanceOf;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class PdfGenerator {

    private final HtmlContentGenerator htmlContentGenerator;
    private final PdfCreator pdfCreator;

    public byte[] generatePdf(Object foo) {
        HtmlContext htmlContext = Match(foo).of(
                Case($(instanceOf(ShelterListData.class)), this::handleShelterList),
                Case($(), () -> { throw new IllegalArgumentException("unhandled kind of data for pdf generator"); })
        );

        HtmlContent htmlContent = htmlContentGenerator.process(htmlContext);
        return pdfCreator.process(htmlContent);
    }

    HtmlContext handleShelterList(ShelterListData data) {
        HashMap<String, Object> contextMap = HashMap.of(
                "animals", data.animals(),
                "shelterCapacity", data.shelterCapacity()
        );
        return new HtmlContext("shelter_list", contextMap);
    }
}

