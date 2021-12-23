package pl.devcezz.animalshelter.generator.pdf;

import io.vavr.collection.Map;

record HtmlInput(String template, Map<String, Object> contextMap) {}
