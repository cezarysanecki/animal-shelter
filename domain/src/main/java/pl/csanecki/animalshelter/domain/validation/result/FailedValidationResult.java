package pl.csanecki.animalshelter.domain.validation.result;

import lombok.Value;
import pl.csanecki.animalshelter.domain.validation.result.item.FailedItemValidationResult;

import java.util.Set;

@Value
public class FailedValidationResult implements ValidationResult {

    Set<FailedItemValidationResult> errors;

    public FailedValidationResult(Set<FailedItemValidationResult> errors) {
        this.errors = errors;
    }
}
