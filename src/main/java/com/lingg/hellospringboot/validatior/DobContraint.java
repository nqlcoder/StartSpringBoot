package com.lingg.hellospringboot.validatior;

import static java.lang.annotation.ElementType.FIELD;

import java.lang.annotation.*;

import jakarta.validation.Constraint;

import com.nimbusds.jose.Payload;

@Target({FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {DobValidator.class})
public @interface DobContraint {
    String message() default "Invalid date of birth";

    int min();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
