package pl.devcezz.shelter.commons.events.handler;

import org.springframework.context.event.EventListener;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@EventListener
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface EventsListener {
}
