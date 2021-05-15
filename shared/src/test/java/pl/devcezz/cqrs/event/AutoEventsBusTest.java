package pl.devcezz.cqrs.event;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import pl.devcezz.tests.TestFiles;

import java.nio.file.Path;
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
        Path path = TestFiles.createTestFileInDir("auto_events_bus_test_%s" + System.currentTimeMillis(), tempDir);

        FirstMailEventHandler firstMailEventHandler = new FirstMailEventHandler(path, "Event handled - first");
        SecondMailEventHandler secondMailEventHandler = new SecondMailEventHandler(path, "Event handled - second");
        AutoEventsBus autoEventsBus = new AutoEventsBus(
                Set.of(firstMailEventHandler, secondMailEventHandler)
        );

        autoEventsBus.publish(new MailEvent());

        assertThat(TestFiles.readFileLines(path)).containsExactlyInAnyOrder("Event handled - first", "Event handled - second");
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
                .ifPresent(path -> TestFiles.writeMessageToFile(path, message));
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
                .ifPresent(path -> TestFiles.writeMessageToFile(path, message));
    }
}