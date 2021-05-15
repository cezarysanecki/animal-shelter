package pl.devcezz.cqrs.event;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class AutoEventsBusTest {

    @TempDir
    Path tempDir;

    @Test
    void shouldProperlySetEventsBus() {
        FirstMailEventHandler firstMailEventHandler = new FirstMailEventHandler();
        SecondMailEventHandler secondMailEventHandler = new SecondMailEventHandler();
        AutoEventsBus autoEventsBus = new AutoEventsBus(
                Set.of(firstMailEventHandler, secondMailEventHandler)
        );

        var handlers = autoEventsBus.getHandlers();

        assertThat(handlers.get(MailEvent.class)).containsExactlyInAnyOrder(firstMailEventHandler, secondMailEventHandler);
        assertThat(handlers.get(ChatEvent.class)).isNull();
    }

    @Test
    void shouldHandleEvent() {
        Path path = createEmptyTestFile();

        FirstMailEventHandler firstMailEventHandler = new FirstMailEventHandler(path, "Event handled - first");
        SecondMailEventHandler secondMailEventHandler = new SecondMailEventHandler(path, "Event handled - second");
        AutoEventsBus autoEventsBus = new AutoEventsBus(
                Set.of(firstMailEventHandler, secondMailEventHandler)
        );

        autoEventsBus.publish(new MailEvent());

        assertThat(readFileLines(path)).containsExactlyInAnyOrder("Event handled - first", "Event handled - second");
    }

    private Path createEmptyTestFile() {
        try {
            Path tempDirPath = tempDir.resolve(String.format("auto_commands_bus_test_%s", System.currentTimeMillis()));
            return Files.createFile(tempDirPath);
        } catch (InvalidPathException | IOException ex) {
            throw new FailTestException("Cannot create empty file for test");
        }
    }

    private List<String> readFileLines(final Path path) {
        try {
            return Files.readAllLines(path);
        } catch (IOException ex) {
            throw new FailTestException("Cannot read content of test file: " + path.getFileName());
        }
    }
}

class MailEvent implements Event {}
class ChatEvent implements Event {}

class FirstMailEventHandler implements EventHandler<MailEvent> {

    private Path path;
    private String message;

    FirstMailEventHandler() {
    }

    FirstMailEventHandler(final Path path, final String message) {
        this.path = path;
        this.message = message;
    }

    @Override
    public void handle(final MailEvent event) {
        Optional.ofNullable(path)
                .ifPresent(path -> this.writeMessage());
    }

    private void writeMessage() {
        try {
            Files.write(path, List.of(message), StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new FailTestException("Cannot write message to file: "+ path.getFileName());
        }
    }
}

class SecondMailEventHandler implements EventHandler<MailEvent> {

    private Path path;
    private String message;

    SecondMailEventHandler() {
    }

    SecondMailEventHandler(final Path path, final String message) {
        this.path = path;
        this.message = message;
    }

    @Override
    public void handle(final MailEvent event) {
        Optional.ofNullable(path)
                .ifPresent(path -> this.writeMessage());
    }

    private void writeMessage() {
        try {
            Files.write(path, List.of(message), StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new FailTestException("Cannot write message to file: "+ path.getFileName());
        }
    }
}

class FailTestException extends RuntimeException {
    FailTestException(final String message) {
        super(message);
    }
}