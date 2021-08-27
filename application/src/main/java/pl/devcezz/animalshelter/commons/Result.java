package pl.devcezz.animalshelter.commons;

public abstract class Result {

    public final static class Success extends Result {}

    public final static class Rejection extends Result {

        private final String reason;

        public Rejection(final String reason) {
            this.reason = reason;
        }

        public String getReason() {
            return reason;
        }
    }
}