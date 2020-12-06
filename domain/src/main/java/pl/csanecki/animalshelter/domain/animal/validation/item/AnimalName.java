package pl.csanecki.animalshelter.domain.animal.validation.item;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AnimalName extends ValidationItem {

    @NonNull
    private final String name;

    @Override
    public void validateItem() {
        if (name.length() < 3) {
            resolver.populateError("Cannot use name shorter than 3 characters");
        }
    }

    @Override
    protected String field() {
        return "name";
    }
}
