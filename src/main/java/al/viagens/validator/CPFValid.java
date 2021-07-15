package al.viagens.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.validation.Payload;
@java.lang.annotation.Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CPFValid {
	String message() default "{al.viagens.validator.CPFvalidator.message}";
	Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
