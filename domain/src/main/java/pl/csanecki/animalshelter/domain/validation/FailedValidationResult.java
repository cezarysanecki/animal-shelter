package pl.csanecki.animalshelter.domain.validation;

import pl.csanecki.animalshelter.domain.validation.result.FailedItemValidationResult;

import java.util.Set;

public class FailedValidationResult implements ValidationResult {

    private final Set<FailedItemValidationResult> errors;

    FailedValidationResult(Set<FailedItemValidationResult> errors) {
        this.errors = errors;
    }
}
