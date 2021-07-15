package pl.devcezz.animalshelter.___;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ PARAMETER })
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = ShelterSpeciesValidator.class)
public @interface ShelterSpecies {
    String message() default "{javax.validation.constraints.ValueOfAnimalKind.message}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
