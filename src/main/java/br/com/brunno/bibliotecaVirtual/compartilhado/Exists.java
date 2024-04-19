package br.com.brunno.bibliotecaVirtual.compartilhado;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = ExistsValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface Exists {

    String message() default "Nao encontrado";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    Class<?> domain();

    String domainField();
}
