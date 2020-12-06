package pl.csanecki.animalshelter.domain.animal.validation;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import pl.csanecki.animalshelter.domain.validation.item.ValidationItem;

@RequiredArgsConstructor
public class AnimalName extends ValidationItem {

    @NonNull
    private final String name;

    @Override
    public void validateItem() {
        if (name.length() < 3) {
            addError("Cannot use name shorter than 3 characters");
        }
    }

    @Override
    protected String field() {
        return "name";
    }
}
