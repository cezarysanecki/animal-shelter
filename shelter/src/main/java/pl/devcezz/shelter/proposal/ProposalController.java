package pl.devcezz.shelter.proposal;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/shelter/proposals")
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class ProposalController {

    private final AnimalProposalService animalProposalService;

    @PostMapping("/accept")
    ResponseEntity<Void> acceptProposal(@RequestBody String animalProposalId) {
        animalProposalService.acceptProposal(AnimalProposalId.of(UUID.fromString(animalProposalId)));
        return ResponseEntity.ok().build();
    }

    @PostMapping("/decline")
    ResponseEntity<Void> declineProposal(@RequestBody String animalProposalId) {
        animalProposalService.declineProposal(AnimalProposalId.of(UUID.fromString(animalProposalId)));
        return ResponseEntity.ok().build();
    }
}