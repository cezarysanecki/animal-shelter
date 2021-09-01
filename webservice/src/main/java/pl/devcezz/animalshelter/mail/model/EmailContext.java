package pl.devcezz.animalshelter.mail.model;

import io.vavr.collection.HashMap;
import io.vavr.collection.Map;

public class EmailContext {

    private Map<String, Object> contextMap = HashMap.empty();

    private EmailContext() {}

    public static EmailContext create() {
        return new EmailContext();
    }

    public EmailContext append(String key, Object value) {
        this.contextMap = contextMap.put(key, value);
        return this;
    }

    public Map<String, Object> collect() {
        return contextMap;
    }
}