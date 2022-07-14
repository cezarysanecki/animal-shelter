package pl.devcezz.shelter.generator.csv;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import pl.devcezz.shelter.catalogue.AnimalDto;
import pl.devcezz.shelter.generator.FileGenerator;
import pl.devcezz.shelter.generator.dto.FileType;
import pl.devcezz.shelter.generator.dto.ShelterListData;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static io.vavr.Predicates.instanceOf;

class CsvGenerator implements FileGenerator {

    @Override
    public byte[] generate(Object data) {
        ByteArrayOutputStream byteArrayOutputStream = Match(data).of(
                Case($(instanceOf(ShelterListData.class)), this::handleShelterList),
                Case($(), () -> {
                    throw new IllegalArgumentException("unhandled kind of data for pdf generator");
                })
        );
        return byteArrayOutputStream.toByteArray();
    }

    private ByteArrayOutputStream handleShelterList(ShelterListData data) {
        CSVFormat csvFormat = CSVFormat.Builder.create().setHeader(
                "no", "name", "age", "species", "gender"
        ).build();

        try (var byteArrayOutputStream = new ByteArrayOutputStream();
             var csvPrinter = new CSVPrinter(new PrintWriter(byteArrayOutputStream), csvFormat)) {

            List<AnimalDto> animals = data.animals();
            for (int index = 0; index < animals.size(); index++) {
                AnimalDto animal = animals.get(index);
                csvPrinter.printRecord(
                        index + 1,
                        animal.getName(),
                        animal.getAge(),
                        animal.getSpecies(),
                        animal.getGender());
            }
            csvPrinter.flush();

            return byteArrayOutputStream;
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public boolean isApplicable(FileType fileType) {
        return fileType == FileType.Csv;
    }
}