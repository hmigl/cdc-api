package com.hmigl.cdc.author;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = {UniqueAuthorEmailValidator.class})
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueAuthorEmail {
    String message() default "This email is taken. Try another";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
