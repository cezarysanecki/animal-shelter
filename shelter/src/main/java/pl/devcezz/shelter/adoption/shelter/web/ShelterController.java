package pl.devcezz.shelter.adoption.shelter.web;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.vavr.control.Try;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.devcezz.shelter.adoption.proposal.model.ProposalId;
import pl.devcezz.shelter.adoption.shelter.application.AcceptProposalCommand;
import pl.devcezz.shelter.adoption.shelter.application.AcceptingProposal;
import pl.devcezz.shelter.commons.commands.Result;

import java.util.UUID;

@RestController
@RequestMapping("/shelter/proposals")
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class ShelterController {

    private final AcceptingProposal acceptingProposal;

    @PostMapping("/accept")
    ResponseEntity<Void> acceptProposal(@RequestBody AcceptProposalRequest request) throws Throwable {
        Try<Result> results = acceptingProposal.acceptProposal(
                new AcceptProposalCommand(ProposalId.of(request.getProposalId())));
        throw results.getCause();
    }
}

@Value
@AllArgsConstructor(onConstructor = @__(@JsonCreator))
class AcceptProposalRequest {
    UUID proposalId;
}