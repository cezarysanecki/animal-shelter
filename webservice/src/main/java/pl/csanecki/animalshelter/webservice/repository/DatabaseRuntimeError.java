package pl.csanecki.animalshelter.webservice.repository;

public class DatabaseRuntimeError extends RuntimeException {
    public DatabaseRuntimeError(String message) {
        super(message);
    }
}
