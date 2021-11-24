package pl.devcezz.animalshelter.generator;

import io.vavr.collection.HashMap;
import pl.devcezz.animalshelter.shelter.read.AnimalProjection;
import pl.devcezz.animalshelter.shelter.read.dto.DataToReportDto;
import pl.devcezz.animalshelter.shelter.read.query.GetDataToReportQuery;

class ShelterHtmlPreparer {

    private final AnimalProjection animalProjection;

    ShelterHtmlPreparer(final AnimalProjection animalProjection) {
        this.animalProjection = animalProjection;
    }

    HtmlInput process() {
        DataToReportDto data = animalProjection.handle(new GetDataToReportQuery());

        HashMap<String, Object> contextMap = HashMap.of(
                "animals", data.animals(),
                "shelterCapacity", data.shelterCapacity()
        );

        return new HtmlInput("shelter_list", contextMap);
    }
}
