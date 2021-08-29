package pl.devcezz.animalshelter.animal.validation;

import io.vavr.collection.Set;
import io.vavr.collection.Stream;
import io.vavr.control.Try;
import pl.devcezz.animalshelter.animal.model.Species;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ShelterSpeciesValidator implements ConstraintValidator<ShelterSpecies, CharSequence> {

    private String message;

    @Override
    public void initialize(ShelterSpecies constraintAnnotation) {
        Set<String> acceptedValues = Stream.of(Species.values())
                .map(Enum::name)
                .toSet();

        message = String.format(
                "{javax.validation.constraints.ShelterSpecies.message}: %s",
                acceptedValues.mkString(", ")
        );
    }

    @Override
    public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
        return Try.of(() -> Species.of(value.toString()))
                .map(species -> true)
                .getOrElseGet(throwable -> {
                    context.disableDefaultConstraintViolation();
                    context.buildConstraintViolationWithTemplate(message).addConstraintViolation();

                    return false;
                });
    }
}
