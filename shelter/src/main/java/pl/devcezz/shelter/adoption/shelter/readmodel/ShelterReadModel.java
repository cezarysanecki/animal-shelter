package pl.devcezz.shelter.adoption.shelter.readmodel;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import pl.devcezz.shelter.adoption.shelter.model.Shelter;
import pl.devcezz.shelter.adoption.shelter.readmodel.dto.ShelterDto;
import pl.devcezz.shelter.adoption.shelter.readmodel.dto.ShelterProposalDto;

import java.util.List;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class ShelterReadModel {

    private final ShelterReadModelRepository shelterReadModelRepository;

    public ShelterDto fetchShelter() {
        List<ShelterProposalDto> proposals = shelterReadModelRepository.findAcceptedProposals();

        return new ShelterDto(proposals, Shelter.CAPACITY, countSpaceLeft(Shelter.CAPACITY, proposals.size()));
    }

    private Long countSpaceLeft(long capacity, long currentProposals) {
        return capacity - currentProposals;
    }
}
