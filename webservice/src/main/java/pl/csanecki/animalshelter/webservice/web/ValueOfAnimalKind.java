package pl.csanecki.animalshelter.webservice.web;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ FIELD })
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = ValueOfAnimalKindValidator.class)
public @interface ValueOfAnimalKind {
    Class<? extends Enum<?>> enumClass();
    String message() default "{javax.validation.constraints.ValueOfAnimalKind.message}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
