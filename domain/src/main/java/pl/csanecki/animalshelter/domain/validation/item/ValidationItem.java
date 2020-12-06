package pl.csanecki.animalshelter.domain.validation.item;

import pl.csanecki.animalshelter.domain.validation.result.ItemValidationResult;

public abstract class ValidationItem {

    protected ValidationResolver resolver = new ValidationResolver();

    public final ItemValidationResult validate() {
        validateItem();
        return resolver.calculateFor(field());
    }

    protected void addError(String error) {
        resolver.populateError(error);
    }

    protected abstract String field();

    protected abstract void validateItem();
}
