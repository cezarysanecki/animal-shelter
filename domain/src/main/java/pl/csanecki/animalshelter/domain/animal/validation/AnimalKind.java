package pl.csanecki.animalshelter.domain.animal.validation;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import pl.csanecki.animalshelter.domain.validation.item.ValidationItem;

import java.util.Optional;
import java.util.stream.Stream;

@RequiredArgsConstructor
public class AnimalKind extends ValidationItem {

    @NonNull
    private final String kind;

    @Override
    public void validateItem() {
        if (AvailableKind.of(kind).isEmpty()) {
            addError("Not valid kind of animal");
        }
    }

    @Override
    protected String field() {
        return "kind";
    }

    enum AvailableKind {
        DOG, CAT;

        public static Optional<AvailableKind> of(String kind) {
            return Stream.of(AvailableKind.values())
                    .filter(availableKind -> availableKind.name().equals(kind))
                    .findFirst();
        }
    }
}