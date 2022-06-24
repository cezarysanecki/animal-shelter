package pl.devcezz.shelter.commons.aggregates;

public class AggregateRootIsStale extends RuntimeException {

    public AggregateRootIsStale(String msg) {
        super(msg);
    }
}
