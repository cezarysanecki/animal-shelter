package pl.csanecki.animalshelter.domain.validation.result;

import lombok.Value;

import java.util.List;

@Value
public class FailedItemValidationResult implements ItemValidationResult {

    String fieldName;
    List<String> errorMessages;
}
