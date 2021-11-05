package pl.devcezz.cqrs.command;

public interface CommandsBus {
    void send(Command command);
}
