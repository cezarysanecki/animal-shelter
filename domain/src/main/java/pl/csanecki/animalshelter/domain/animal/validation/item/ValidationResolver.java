package pl.csanecki.animalshelter.domain.animal.validation.item;

import pl.csanecki.animalshelter.domain.animal.validation.item.result.FailedItemValidationResult;
import pl.csanecki.animalshelter.domain.animal.validation.item.result.ItemValidationResult;
import pl.csanecki.animalshelter.domain.animal.validation.item.result.SucceededItemValidationResult;

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
