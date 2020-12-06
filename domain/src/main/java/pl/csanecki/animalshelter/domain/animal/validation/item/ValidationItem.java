package pl.csanecki.animalshelter.domain.animal.validation.item;

import pl.csanecki.animalshelter.domain.animal.validation.item.result.ItemValidationResult;

public abstract class ValidationItem {

    protected ValidationResolver resolver = new ValidationResolver();

    public final ItemValidationResult validate() {
        validateItem();
        return resolver.calculateFor(field());
    }

    protected abstract String field();

    protected abstract void validateItem();
}
