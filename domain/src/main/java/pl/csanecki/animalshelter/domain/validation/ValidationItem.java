package pl.csanecki.animalshelter.domain.validation;

import pl.csanecki.animalshelter.domain.validation.result.item.ItemValidationResult;

public abstract class ValidationItem {

    protected ValidationResolver resolver = new ValidationResolver();

    public final ItemValidationResult validate() {
        validateItem();
        return resolver.calculateFor(field());
    }

    protected abstract String field();

    protected abstract void validateItem();
}
