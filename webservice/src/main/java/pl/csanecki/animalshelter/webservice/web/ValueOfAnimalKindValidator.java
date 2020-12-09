package pl.csanecki.animalshelter.webservice.web;

import io.vavr.collection.List;
import io.vavr.collection.Stream;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValueOfAnimalKindValidator implements ConstraintValidator<ValueOfAnimalKind, CharSequence> {

    private List<String> acceptedValues;

    @Override
    public void initialize(ValueOfAnimalKind constraintAnnotation) {
        acceptedValues = Stream.of(constraintAnnotation.enumClass().getEnumConstants())
                .map(Enum::name)
                .toList();
    }

    @Override
    public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
        return value != null && acceptedValues.contains(value.toString());
    }
}
