package pl.devcezz.shelter.generator.pdf;

import io.vavr.collection.Map;

record HtmlContext(String template, Map<String, Object> contextMap) {}
