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
import pl.devcezz.shelter.adoption.shelter.application.CancelProposalCommand;
import pl.devcezz.shelter.adoption.shelter.application.CancelingProposal;
import pl.devcezz.shelter.commons.commands.Result;

import java.util.UUID;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestController
@RequestMapping("/shelter/proposals")
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class ShelterController {

    private final AcceptingProposal acceptingProposal;
    private final CancelingProposal cancelingProposal;

    @PostMapping("/accept")
    ResponseEntity acceptProposal(@RequestBody AcceptProposalRequest request) {
        Try<Result> result = acceptingProposal.acceptProposal(
                new AcceptProposalCommand(ProposalId.of(request.getProposalId())));
        return result
                .map(success -> ResponseEntity.ok().build())
                .getOrElse(ResponseEntity.status(INTERNAL_SERVER_ERROR).build());
    }

    @PostMapping("/cancel")
    ResponseEntity cancelProposal(@RequestBody CancelProposalRequest request) {
        Try<Result> result = cancelingProposal.cancelProposal(
                new CancelProposalCommand(ProposalId.of(request.getProposalId())));
        return result
                .map(success -> ResponseEntity.ok().build())
                .getOrElse(ResponseEntity.status(INTERNAL_SERVER_ERROR).build());
    }
}

@Value
@AllArgsConstructor(onConstructor = @__(@JsonCreator))
class AcceptProposalRequest {
    UUID proposalId;
}

@Value
@AllArgsConstructor(onConstructor = @__(@JsonCreator))
class CancelProposalRequest {
    UUID proposalId;
}