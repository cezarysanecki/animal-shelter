package pl.devcezz.cqrs.command;

import org.junit.jupiter.api.Test;
import pl.devcezz.cqrs.exception.NoHandlerForCommandException;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AutoCommandsBusTest {

    @Test
    void shouldProperlySetCommandsBus() {
        // given
        FirstCommandHandler firstCommandHandler = new FirstCommandHandler();
        AutoCommandsBus commandsBus = new AutoCommandsBus(
                Set.of(firstCommandHandler)
        );

        // when
        Map<Type, CommandHandler> handlers = commandsBus.getHandlers();

        // then
        assertThat(handlers.get(FirstCommand.class)).isEqualTo(firstCommandHandler);
        assertThat(handlers.get(SecondCommand.class)).isNull();
    }

    @Test
    void shouldHandleCommand() {
        // given
        FirstCommandHandler firstCommandHandler = new FirstCommandHandler();
        AutoCommandsBus commandsBus = new AutoCommandsBus(
                Set.of(firstCommandHandler)
        );
        FirstCommand command = new FirstCommand();

        // when/then
        assertThatThrownBy(() -> commandsBus.send(command))
            .isInstanceOf(TestException.class);
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
}

class FirstCommand implements Command {}
class SecondCommand implements Command {}

class FirstCommandHandler implements CommandHandler<FirstCommand> {
    @Override
    public void handle(final FirstCommand command) {
        throw new TestException();
    }
}

class TestException extends RuntimeException {}