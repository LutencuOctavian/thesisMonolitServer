package dom.com.thesismonolitserver.validators;

import dom.com.thesismonolitserver.validators.implementation.NotEmptyListValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ FIELD })
@Retention(RUNTIME)
@Constraint(validatedBy = NotEmptyListValidator.class)
@Documented
public @interface NotEmptyList {
    String message() default "{emptyList.invalid}";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}
