package pl.devcezz.shelter.commons.model;

import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@RequiredArgsConstructor(staticName = "of")
public class Species {
    String value;
}
