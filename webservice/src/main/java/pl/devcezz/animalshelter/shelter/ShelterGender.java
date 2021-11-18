package pl.devcezz.animalshelter.shelter;

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
@Constraint(validatedBy = ShelterGenderValidator.class)
@interface ShelterGender {
    String message() default "{javax.validation.constraints.ShelterGender.message.default}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

class ShelterGenderValidator implements ConstraintValidator<ShelterGender, CharSequence> {

    private String message;

    @Override
    public void initialize(ShelterGender constraintAnnotation) {
        Set<String> acceptedValues = Stream.of(Gender.values())
                .map(Enum::name)
                .toSet();

        message = String.format(
                "{javax.validation.constraints.ShelterGender.message}: %s",
                acceptedValues.mkString(", ")
        );
    }

    @Override
    public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
        return Try.of(() -> Gender.of(value.toString()))
                .map(gender -> true)
                .getOrElseGet(throwable -> {
                    context.disableDefaultConstraintViolation();
                    context.buildConstraintViolationWithTemplate(message).addConstraintViolation();

                    return false;
                });
    }
}
