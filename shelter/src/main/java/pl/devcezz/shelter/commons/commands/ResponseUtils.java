package pl.devcezz.shelter.commons.commands;

import org.springframework.http.ResponseEntity;

public class ResponseUtils {

    public static <T> ResponseEntity<T> resolveResult(Result result, T body) {
        return switch (result) {
            case Success -> ResponseEntity.ok(body);
            case Rejection -> ResponseEntity.badRequest().build();
        };
    }

    public static ResponseEntity<Void> resolveResult(Result result) {
        return switch (result) {
            case Success -> ResponseEntity.ok().build();
            case Rejection -> ResponseEntity.badRequest().build();
        };
    }
}
