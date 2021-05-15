package pl.devcezz.cqrs.command;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import pl.devcezz.cqrs.exception.NoHandlerForCommandException;
import pl.devcezz.cqrs.exception.NotImplementedCommandInterfaceException;

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
        // given
        FirstCommandHandler firstCommandHandler = new FirstCommandHandler();
        AutoCommandsBus commandsBus = new AutoCommandsBus(
                Set.of(firstCommandHandler)
        );

        // when
        var handlers = commandsBus.getHandlers();

        // then
        assertThat(handlers.get(FirstCommand.class)).isEqualTo(firstCommandHandler);
        assertThat(handlers.get(SecondCommand.class)).isNull();
    }

    @Test
    void shouldHandleCommand() throws IOException {
        // given
        String messageForHandler = "First command handled";
        Path path = createTestFile();

        FirstCommandHandler firstCommandHandler = new FirstCommandHandler(path, messageForHandler);
        AutoCommandsBus commandsBus = new AutoCommandsBus(
                Set.of(firstCommandHandler)
        );

        // when
        commandsBus.send(new FirstCommand());

        // then
        assertThat(Files.readAllLines(path)).containsExactlyInAnyOrder(messageForHandler);
    }

    @Test
    void shouldThrowExceptionIfNoHandlerForCommand() {
        // given
        FirstCommandHandler firstCommandHandler = new FirstCommandHandler();
        AutoCommandsBus commandsBus = new AutoCommandsBus(
                Set.of(firstCommandHandler)
        );
        SecondCommand command = new SecondCommand();

        // when/then
        assertThatThrownBy(() -> commandsBus.send(command))
                .isInstanceOf(NoHandlerForCommandException.class);
    }

    @Test
    void shouldThrowExceptionWhenHandlerNotUsingImplementationOfCommand() {
        // given
        WrongCommandHandler wrongCommandHandler = new WrongCommandHandler();

        // when/then
        assertThatThrownBy(() -> new AutoCommandsBus(Set.of(wrongCommandHandler)))
                .isInstanceOf(NotImplementedCommandInterfaceException.class);
    }

    private Path createTestFile() {
        try {
            return tempDir.resolve(String.format("command_handler_%s", System.currentTimeMillis()));
        } catch (InvalidPathException ex) {
            throw new EndTestException();
        }
    }
}

class FirstCommand implements Command {}
class SecondCommand implements Command {}

class FirstCommandHandler implements CommandHandler<FirstCommand>, Serializable {

    private Path path;
    private String message;

    FirstCommandHandler() {
    }

    FirstCommandHandler(final Path path, final String message) {
        this.path = path;
        this.message = message;
    }

    @Override
    public void handle(final FirstCommand command) {
        Optional.ofNullable(path)
                .ifPresent(path -> this.writeMessage());
    }

    private void writeMessage() {
        try {
            Files.write(path, List.of(message));
        } catch (IOException e) {
            throw new EndTestException();
        }
    }
}
class WrongCommandHandler implements CommandHandler<Command> {
    @Override
    public void handle(final Command command) {

    }
}

class EndTestException extends RuntimeException {}