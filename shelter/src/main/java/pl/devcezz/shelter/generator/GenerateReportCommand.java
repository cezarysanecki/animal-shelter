package pl.devcezz.shelter.generator;

record GenerateReportCommand(
        FileType fileType,
        ContentType contentType) {

    String filename() {
        return String.format("%s.%s", contentType.name().toLowerCase(), fileType.name().toLowerCase());
    }
}
