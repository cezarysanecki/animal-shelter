package pl.csanecki.animalshelter.webservice.web;

import io.vavr.collection.List;
import io.vavr.collection.Stream;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValueOfAnimalKindValidator implements ConstraintValidator<ValueOfAnimalKind, CharSequence> {

    private List<String> acceptedValues;
    private String message;

    @Override
    public void initialize(ValueOfAnimalKind constraintAnnotation) {
        acceptedValues = Stream.of(constraintAnnotation.enumClass().getEnumConstants())
                .map(Enum::name)
                .toList();

        message = String.format("{javax.validation.constraints.ValueOfAnimalKind.message}: %s", acceptedValues.mkString(", "));
    }

    @Override
    public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
        if (value != null && acceptedValues.contains(value.toString())) {
            return true;
        }

        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(message).addConstraintViolation();

        return false;
    }
}
