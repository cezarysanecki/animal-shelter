package pl.devcezz.animalshelter.mail.content;

import io.vavr.collection.HashMap;
import io.vavr.collection.Map;

class EmailContext {

    private Map<String, Object> contextMap = HashMap.empty();

    private EmailContext() {}

    static EmailContext create() {
        return new EmailContext();
    }

    EmailContext append(String key, Object value) {
        this.contextMap = contextMap.put(key, value);
        return this;
    }

    Map<String, Object> collect() {
        return contextMap;
    }
}