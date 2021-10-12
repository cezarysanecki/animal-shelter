package pl.devcezz.animalshelter.commons;

public abstract class Result {

    public static Success success() {
        return new Success();
    }

    public static Rejection rejection(String reason) {
        return new Rejection(reason);
    }

    public final static class Success extends Result {
        private Success() {}
    }

    public final static class Rejection extends Result {

        private final String reason;

        private Rejection(final String reason) {
            this.reason = reason;
        }

        public String getReason() {
            return reason;
        }
    }
}