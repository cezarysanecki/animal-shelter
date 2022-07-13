package pl.devcezz.shelter.adoption.shelter.readmodel.dto;

import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Value
@Builder
public class ShelterProposalDto {
    UUID proposalId;
    String status;
}
