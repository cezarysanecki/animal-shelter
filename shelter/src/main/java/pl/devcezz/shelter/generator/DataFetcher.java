package pl.devcezz.shelter.generator;

import pl.devcezz.shelter.generator.dto.ContentType;

public interface DataFetcher {

    Object fetch();

    boolean isApplicable(ContentType contentType);
}
