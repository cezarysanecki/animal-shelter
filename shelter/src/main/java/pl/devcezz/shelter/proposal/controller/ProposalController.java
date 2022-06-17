package pl.devcezz.shelter.proposal.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.devcezz.shelter.proposal.AnimalProposalFacade;

import java.util.UUID;

@RestController
@RequestMapping("/shelter/proposals")
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class ProposalController {

    private final AnimalProposalFacade animalProposalFacade;

    @PostMapping("/accept")
    ResponseEntity<Void> acceptProposal(@RequestBody String animalProposalId) {
        animalProposalFacade.acceptProposal(UUID.fromString(animalProposalId));
        return ResponseEntity.ok().build();
    }

    @PostMapping("/decline")
    ResponseEntity<Void> declineProposal(@RequestBody String animalProposalId) {
        animalProposalFacade.declineProposal(UUID.fromString(animalProposalId));
        return ResponseEntity.ok().build();
    }
}