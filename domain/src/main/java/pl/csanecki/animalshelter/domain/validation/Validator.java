package pl.csanecki.animalshelter.domain.validation;

import pl.csanecki.animalshelter.domain.validation.item.ValidationItem;
import pl.csanecki.animalshelter.domain.validation.result.FailedItemValidationResult;

import java.util.Set;
import java.util.stream.Collectors;

public class Validator {

    public ValidationResult validate(Set<ValidationItem> items) {
        Set<FailedItemValidationResult> errors = items.stream()
                .map(ValidationItem::validate)
                .filter(result -> result instanceof FailedItemValidationResult)
                .map(failure -> (FailedItemValidationResult) failure)
                .collect(Collectors.toSet());

        return errors.isEmpty() ? new SucceededValidationResult() : new FailedValidationResult(errors);
    }
}
