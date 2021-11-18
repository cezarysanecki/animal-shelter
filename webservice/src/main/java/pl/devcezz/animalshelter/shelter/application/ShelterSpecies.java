package pl.devcezz.animalshelter.shelter.application;

import io.vavr.collection.Set;
import io.vavr.collection.Stream;
import io.vavr.control.Try;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = ShelterSpeciesValidator.class)
@interface ShelterSpecies {
    String message() default "{javax.validation.constraints.ShelterSpecies.message.default}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

class ShelterSpeciesValidator implements ConstraintValidator<ShelterSpecies, CharSequence> {

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
