package pl.devcezz.cqrs.command;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import pl.devcezz.cqrs.exception.NoHandlerForCommandException;
import pl.devcezz.cqrs.exception.NotImplementedCommandInterfaceException;
import pl.devcezz.tests.FailTestException;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AutoCommandsBusTest {

    @TempDir
    Path tempDir;

    @Test
    void shouldProperlySetCommandsBus() {
        ProperCommandHandler properCommandHandler = new ProperCommandHandler();
        AutoCommandsBus commandsBus = new AutoCommandsBus(
                Set.of(properCommandHandler)
        );

        var handlers = commandsBus.getHandlers();

        assertThat(handlers.get(HandledCommand.class)).isEqualTo(properCommandHandler);
        assertThat(handlers.get(NotHandledCommand.class)).isNull();
    }

    @Test
    void shouldHandleCommand() {
        Path path = createEmptyTestFile();
        AutoCommandsBus commandsBus = new AutoCommandsBus(
                Set.of(new ProperCommandHandler(path, "Command handled"))
        );

        commandsBus.send(new HandledCommand());

        assertThat(readFileLines(path)).containsExactlyInAnyOrder("Command handled");
    }

    @Test
    void shouldThrowExceptionIfNoHandlerForCommand() {
        AutoCommandsBus commandsBus = new AutoCommandsBus(
                Set.of(new ProperCommandHandler())
        );

        assertThatThrownBy(() -> commandsBus.send(new NotHandledCommand()))
                .isInstanceOf(NoHandlerForCommandException.class);
    }

    @Test
    void shouldThrowExceptionWhenHandlerNotUsingImplementationOfCommand() {
        assertThatThrownBy(() -> new AutoCommandsBus(Set.of(new IncorrectCommandHandler())))
                .isInstanceOf(NotImplementedCommandInterfaceException.class);
    }

    @Test
    void shouldThrowExceptionWhenTwoHandlersForOneImplementationOfCommand() {
        assertThatThrownBy(() -> new AutoCommandsBus(Set.of(new ProperCommandHandler(), new RedundantCommandHandler())))
                .isInstanceOf(IllegalStateException.class);
    }

    private Path createEmptyTestFile() {
        try {
            return tempDir.resolve(String.format("auto_commands_bus_test_%s", System.currentTimeMillis()));
        } catch (InvalidPathException ex) {
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

class HandledCommand implements Command {}
class NotHandledCommand implements Command {}

class ProperCommandHandler implements CommandHandler<HandledCommand>, Serializable {

    private Path path;
    private String message;

    ProperCommandHandler() {
    }

    ProperCommandHandler(final Path path, final String message) {
        this.path = path;
        this.message = message;
    }

    @Override
    public void handle(final HandledCommand command) {
        Optional.ofNullable(path)
                .ifPresent(path -> this.writeMessage());
    }

    private void writeMessage() {
        try {
            Files.write(path, List.of(message));
        } catch (IOException e) {
            throw new FailTestException("Cannot write message to file: "+ path.getFileName());
        }
    }
}
class RedundantCommandHandler implements CommandHandler<HandledCommand> {
    @Override
    public void handle(final HandledCommand command) {

    }
}
class IncorrectCommandHandler implements CommandHandler<Command> {
    @Override
    public void handle(final Command command) {

    }
}