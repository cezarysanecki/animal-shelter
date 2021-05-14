package pl.devcezz.cqrs.command;

public interface CommandHandler<T extends Command> {
    void handle(T command);
}
