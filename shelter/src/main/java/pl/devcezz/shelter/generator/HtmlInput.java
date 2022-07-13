package pl.devcezz.shelter.generator;

import io.vavr.collection.Map;

record HtmlInput(String template, Map<String, Object> contextMap) {}
