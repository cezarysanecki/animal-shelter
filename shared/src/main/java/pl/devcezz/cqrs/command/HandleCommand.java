package pl.devcezz.cqrs.command;

public interface HandleCommand<T extends Command> {
    void handle(T command);
}
