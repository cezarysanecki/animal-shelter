package pl.devcezz.animalshelter.infrastructure;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.*;

@ControllerAdvice
class ErrorHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException exception,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request
    ) {
        Map<String, List<String>> errors = exception.getBindingResult()
                .getAllErrors()
                .stream()
                .collect(groupingBy(error -> ((FieldError) error).getField(), mapping(DefaultMessageSourceResolvable::getDefaultMessage, toList())));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }
}
