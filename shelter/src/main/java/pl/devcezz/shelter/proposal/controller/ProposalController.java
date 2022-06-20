package pl.devcezz.shelter.proposal.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.devcezz.shelter.proposal.ProposalFacade;

import java.util.UUID;

@RestController
@RequestMapping("/shelter/proposals")
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class ProposalController {

    private final ProposalFacade proposalFacade;

    @PostMapping("/accept")
    ResponseEntity<Void> acceptProposal(@RequestBody String subjectId) {
        proposalFacade.acceptProposal(UUID.fromString(subjectId));
        return ResponseEntity.ok().build();
    }

    @PostMapping("/decline")
    ResponseEntity<Void> declineProposal(@RequestBody String subjectId) {
        proposalFacade.declineProposal(UUID.fromString(subjectId));
        return ResponseEntity.ok().build();
    }
}