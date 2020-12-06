package pl.csanecki.animalshelter.domain.animal.validation.item;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AnimalAge extends ValidationItem {

    private final int age;

    @Override
    protected void validateItem() {
        if (age < 0) {
            resolver.populateError("Cannot use negative integer as number of ages");
        }
    }

    @Override
    protected String field() {
        return "age";
    }
}
