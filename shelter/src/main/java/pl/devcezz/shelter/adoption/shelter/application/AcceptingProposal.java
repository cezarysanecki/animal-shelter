package pl.devcezz.shelter.adoption.shelter.application;

import io.vavr.control.Either;
import io.vavr.control.Try;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pl.devcezz.shelter.adoption.shelter.model.Shelter;
import pl.devcezz.shelter.adoption.shelter.model.ShelterEvent.ProposalAcceptedEvents;
import pl.devcezz.shelter.adoption.shelter.model.ShelterEvent.ProposalAcceptingFailed;
import pl.devcezz.shelter.adoption.shelter.model.Shelters;
import pl.devcezz.shelter.commons.commands.Result;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static io.vavr.Patterns.$Left;
import static io.vavr.Patterns.$Right;
import static pl.devcezz.shelter.commons.commands.Result.Rejection;
import static pl.devcezz.shelter.commons.commands.Result.Success;

@RequiredArgsConstructor
@Slf4j
public class AcceptingProposal {

    private final Shelters shelterRepository;

    public Try<Result> acceptProposal(@NonNull AcceptProposalCommand command) {
        return Try.of(() -> {
            Shelter shelter = prepare();
            Either<ProposalAcceptingFailed, ProposalAcceptedEvents> result = shelter.accept(command.getProposalId());
            return Match(result).of(
                    Case($Left($()), this::publishEvents),
                    Case($Right($()), this::publishEvents)
            );
        }).onFailure(ex -> log.error("failed to accept proposal", ex));
    }

    private Result publishEvents(ProposalAcceptingFailed proposalAcceptingFailed) {
        log.error("failed to accept proposal: {}", proposalAcceptingFailed.getReason());
        shelterRepository.publish(proposalAcceptingFailed);
        return Rejection;
    }

    private Result publishEvents(ProposalAcceptedEvents proposalAcceptedEvents) {
        shelterRepository.publish(proposalAcceptedEvents);
        return Success;
    }

    private Shelter prepare() {
        return shelterRepository.prepareShelter();
    }
}
