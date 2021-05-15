package pl.devcezz.cqrs.event;

import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AutoEventBusTest {

    @Test
    void shouldProperlySetEventsBus() {
        FirstMailEventHandler firstMailEventHandler = new FirstMailEventHandler();
        SecondMailEventHandler secondMailEventHandler = new SecondMailEventHandler();
        AutoEventBus autoEventBus = new AutoEventBus(
                Set.of(firstMailEventHandler, secondMailEventHandler)
        );

        var handlers = autoEventBus.getHandlers();

        assertThat(handlers.get(MailEvent.class)).containsExactlyInAnyOrder(firstMailEventHandler, secondMailEventHandler);
        assertThat(handlers.get(ChatEvent.class)).isNull();
    }
}

class MailEvent implements Event {}
class ChatEvent implements Event {}

class FirstMailEventHandler implements EventHandler<MailEvent> {
    @Override
    public void handle(final MailEvent event) {

    }
}

class SecondMailEventHandler implements EventHandler<MailEvent> {
    @Override
    public void handle(final MailEvent event) {

    }
}

class TestException extends RuntimeException {}