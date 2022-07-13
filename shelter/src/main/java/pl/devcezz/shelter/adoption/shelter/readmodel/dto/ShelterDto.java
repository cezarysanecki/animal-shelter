package pl.devcezz.shelter.adoption.shelter.readmodel.dto;

import java.util.List;

public record ShelterDto(List<ShelterProposalDto> proposals, Long capacity, Long spaceLeft) {
}
