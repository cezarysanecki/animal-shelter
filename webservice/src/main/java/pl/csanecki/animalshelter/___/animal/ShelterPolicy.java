package pl.csanecki.animalshelter.___.animal;

import io.vavr.Function2;
import io.vavr.collection.List;
import io.vavr.control.Either;

import static io.vavr.control.Either.left;
import static io.vavr.control.Either.right;
import static pl.csanecki.animalshelter.___.animal.Rejection.Reason.NoSpaceInShelter;
import static pl.csanecki.animalshelter.___.animal.Rejection.Reason.NotAcceptableSpecies;
import static pl.csanecki.animalshelter.___.animal.Warning.Message.SafeThresholdExceeded;

interface ShelterPolicy extends Function2<Shelter, Animal, Either<Rejection, Allowance>> {

    ShelterPolicy acceptableSpeciesInShelterPolicy = (shelter, animal) -> {
        if (shelter.isSpeciesAcceptable(animal.getSpecies())) {
            return left(Rejection.withReason(NotAcceptableSpecies));
        }
        return right(new Success());
    };

    ShelterPolicy limitedCapacityInShelterPolicy = (shelter, animal) -> {
        if (shelter.capacityReachedAfterAccepting(animal)) {
            return left(Rejection.withReason(NoSpaceInShelter));
        } else if (shelter.safeThresholdExceededAfterAccepting(animal)) {
            return right(Warning.withMessage(SafeThresholdExceeded));
        }
        return right(new Success());
    };

    static List<ShelterPolicy> allCurrentPolicies() {
        return List.of(
                acceptableSpeciesInShelterPolicy,
                limitedCapacityInShelterPolicy);
    }
}

abstract class Allowance {}

final class Success extends Allowance {}

final class Warning extends Allowance {

    private Warning(final Message message) {
        this.message = message;
    }

    enum Message {
        SafeThresholdExceeded
    }

    private final Message message;

    static Warning withMessage(Message message) {
        return new Warning(message);
    }
}

final class Rejection {

    private Rejection(final Reason reason) {
        this.reason = reason;
    }

    enum Reason {
        NotAcceptableSpecies, NoSpaceInShelter
    }

    private final Reason reason;

    static Rejection withReason(Reason reason) {
        return new Rejection(reason);
    }
}
