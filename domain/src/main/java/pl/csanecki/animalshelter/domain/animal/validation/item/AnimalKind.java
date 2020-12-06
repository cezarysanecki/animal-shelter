package pl.csanecki.animalshelter.domain.animal.validation.item;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.stream.Stream;

@RequiredArgsConstructor
public class AnimalKind extends ValidationItem {

    @NonNull
    private final String kind;

    @Override
    public void validateItem() {
        if (AvailableKind.of(kind).isEmpty()) {
            resolver.populateError("Not valid kind of animal");
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