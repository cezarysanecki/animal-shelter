package pl.devcezz.cqrs.command;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import pl.devcezz.cqrs.exception.command.NoHandlerForCommandException;
import pl.devcezz.cqrs.exception.command.NotImplementedCommandHandlerInterfaceException;
import pl.devcezz.cqrs.exception.command.NotImplementedCommandInterfaceException;
import pl.devcezz.tests.TestFiles;

import java.io.Serializable;
import java.nio.file.Path;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AutoCommandsBusTest {

    @TempDir
    Path tempDir;

    @Test
    void should_command_handler_handle_command() {
        Path path = TestFiles.createTestFileInDir("auto_commands_bus_test_%s" + System.currentTimeMillis(), tempDir);
        AutoCommandsBus commandsBus = new AutoCommandsBus(
                Set.of(new ProperCommandHandler(path))
        );

        commandsBus.send(new HandledCommand("Message"));

        assertThat(TestFiles.readFileLines(path)).containsExactlyInAnyOrder("Message");
    }

    @Test
    void should_fail_when_no_command_handler_for_command() {
        AutoCommandsBus commandsBus = new AutoCommandsBus(
                Set.of(new ProperCommandHandler())
        );

        assertThatThrownBy(() -> commandsBus.send(new NotHandledCommand()))
                .isInstanceOf(NoHandlerForCommandException.class);
    }

    @Test
    void should_fail_when_set_command_handler_without_generic() {
        assertThatThrownBy(() -> new AutoCommandsBus(Set.of(new CommandHandlerWithoutGeneric())))
                .isInstanceOf(NotImplementedCommandHandlerInterfaceException.class);
    }

    @Test
    void should_fail_when_set_command_handler_without_generic_command_implementation() {
        assertThatThrownBy(() -> new AutoCommandsBus(Set.of(new CommandHandlerForCommandInterface())))
                .isInstanceOf(NotImplementedCommandInterfaceException.class);
    }

    @Test
    void should_fail_when_two_command_handlers_for_command() {
        assertThatThrownBy(() -> new AutoCommandsBus(Set.of(new ProperCommandHandler(), new RedundantCommandHandler())))
                .isInstanceOf(IllegalStateException.class);
    }
}

class HandledCommand implements Command {
    final String message;

    HandledCommand(final String message) {
        this.message = message;
    }
}
class NotHandledCommand implements Command {}

class ProperCommandHandler implements CommandHandler<HandledCommand>, Serializable {

    private Path path;

    ProperCommandHandler() {
    }

    ProperCommandHandler(final Path path) {
        this.path = path;
    }

    @Override
    public void handle(final HandledCommand command) {
        Optional.ofNullable(path)
                .ifPresent(path -> TestFiles.writeMessageToFile(path, command.message));
    }
}
class RedundantCommandHandler implements CommandHandler<HandledCommand> {
    @Override
    public void handle(final HandledCommand command) {

    }
}
class CommandHandlerWithoutGeneric implements CommandHandler {
    @Override
    public void handle(final Command command) {

    }
}
class CommandHandlerForCommandInterface implements CommandHandler<Command> {
    @Override
    public void handle(final Command command) {

    }
}