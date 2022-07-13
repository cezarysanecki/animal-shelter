package pl.devcezz.shelter.adoption.shelter.application;

import io.vavr.control.Either;
import io.vavr.control.Try;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import pl.devcezz.shelter.adoption.shelter.model.Shelter;
import pl.devcezz.shelter.adoption.shelter.model.Shelters;
import pl.devcezz.shelter.commons.commands.Result;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static io.vavr.Patterns.$Left;
import static io.vavr.Patterns.$Right;
import static pl.devcezz.shelter.adoption.shelter.model.ShelterEvent.ProposalCanceled;
import static pl.devcezz.shelter.adoption.shelter.model.ShelterEvent.ProposalCancelingFailed;
import static pl.devcezz.shelter.commons.commands.Result.Rejection;
import static pl.devcezz.shelter.commons.commands.Result.Success;

@RequiredArgsConstructor
@Slf4j
public class CancelingProposal {

    private final Shelters shelterRepository;

    @Transactional
    public Try<Result> cancelProposal(@NonNull CancelProposalCommand command) {
        return Try.of(() -> {
            Shelter shelter = prepare();
            Either<ProposalCancelingFailed, ProposalCanceled> result = shelter.cancel(command.getProposalId());
            return Match(result).of(
                    Case($Left($()), this::publishEvents),
                    Case($Right($()), this::publishEvents)
            );
        }).onFailure(ex -> log.error("failed to cancel proposal", ex));
    }

    private Result publishEvents(ProposalCancelingFailed event) {
        shelterRepository.publish(event);
        return Rejection;
    }

    private Result publishEvents(ProposalCanceled event) {
        shelterRepository.publish(event);
        return Success;
    }

    private Shelter prepare() {
        return shelterRepository.prepareShelter();
    }
}
