package pl.csanecki.animalshelter.domain.validation;

import pl.csanecki.animalshelter.domain.validation.result.item.FailedItemValidationResult;
import pl.csanecki.animalshelter.domain.validation.result.item.ItemValidationResult;
import pl.csanecki.animalshelter.domain.validation.result.item.SucceededItemValidationResult;

import java.util.ArrayList;
import java.util.List;

public class ValidationResolver {

    private final List<String> errorMessages = new ArrayList<>();

    public void populateError(String errorMessage) {
        errorMessages.add(errorMessage);
    }

    public ItemValidationResult calculateFor(String fieldName) {
        return errorMessages.isEmpty() ? new SucceededItemValidationResult() : new FailedItemValidationResult(fieldName, errorMessages);
    }
}
