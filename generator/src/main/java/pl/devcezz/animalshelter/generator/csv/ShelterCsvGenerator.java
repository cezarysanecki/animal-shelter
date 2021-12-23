package pl.devcezz.animalshelter.generator.csv;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import pl.devcezz.animalshelter.shelter.read.AnimalProjection;
import pl.devcezz.animalshelter.shelter.read.dto.DataToReportDto;
import pl.devcezz.animalshelter.shelter.read.query.GetDataToReportQuery;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

public class ShelterCsvGenerator {

    private final AnimalProjection animalProjection;

    ShelterCsvGenerator(AnimalProjection animalProjection) {
        this.animalProjection = animalProjection;
    }

    public byte[] generate() {
        DataToReportDto data = animalProjection.handle(new GetDataToReportQuery());

        try (var byteArrayOutputStream = new ByteArrayOutputStream();
            var csvPrinter = new CSVPrinter(new PrintWriter(byteArrayOutputStream), createCsvFormat())) {

            for (var animal : data.animals()) {
                csvPrinter.printRecord(animal.getAnimalId(), animal.getName(), animal.getAge(), animal.getSpecies(), animal.getGender());
            }
            csvPrinter.flush();

            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private CSVFormat createCsvFormat() {
        return CSVFormat.Builder.create().setHeader(
                "animalId", "name", "age", "species", "gender"
        ).build();
    }
}