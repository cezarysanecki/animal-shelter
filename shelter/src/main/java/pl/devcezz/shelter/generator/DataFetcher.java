package pl.devcezz.shelter.generator;

@FunctionalInterface
public interface DataFetcher {

    Object fetch(ContentType contentType);
}
