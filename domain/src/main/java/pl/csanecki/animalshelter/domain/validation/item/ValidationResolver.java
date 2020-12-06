package pl.csanecki.animalshelter.domain.validation.item;

import pl.csanecki.animalshelter.domain.validation.result.FailedItemValidationResult;
import pl.csanecki.animalshelter.domain.validation.result.ItemValidationResult;
import pl.csanecki.animalshelter.domain.validation.result.SucceededItemValidationResult;

import java.util.ArrayList;
import java.util.List;

class ValidationResolver {

    private final List<String> errorMessages = new ArrayList<>();

    ValidationResolver() {}

    public void populateError(String errorMessage) {
        errorMessages.add(errorMessage);
    }

    public ItemValidationResult calculateFor(String fieldName) {
        return errorMessages.isEmpty() ? new SucceededItemValidationResult() : new FailedItemValidationResult(fieldName, errorMessages);
    }
}
