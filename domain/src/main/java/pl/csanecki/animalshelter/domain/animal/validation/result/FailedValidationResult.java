package pl.csanecki.animalshelter.domain.animal.validation.result;

import lombok.Value;
import pl.csanecki.animalshelter.domain.animal.validation.item.result.FailedItemValidationResult;

import java.util.Set;

@Value
public class FailedValidationResult implements ValidationResult {

    Set<FailedItemValidationResult> errors;

    public FailedValidationResult(Set<FailedItemValidationResult> errors) {
        this.errors = errors;
    }
}
