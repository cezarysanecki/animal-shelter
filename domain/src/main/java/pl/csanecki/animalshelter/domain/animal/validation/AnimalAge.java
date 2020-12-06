package pl.csanecki.animalshelter.domain.animal.validation;

import lombok.RequiredArgsConstructor;
import pl.csanecki.animalshelter.domain.validation.item.ValidationItem;

@RequiredArgsConstructor
public class AnimalAge extends ValidationItem {

    private final int age;

    @Override
    protected void validateItem() {
        if (age < 0) {
            addError("Cannot use negative integer as number of ages");
        }
    }

    @Override
    protected String field() {
        return "age";
    }
}
