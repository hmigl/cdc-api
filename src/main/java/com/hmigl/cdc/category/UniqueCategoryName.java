package com.hmigl.cdc.category;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = {UniqueCategoryNameValidator.class})
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface UniqueCategoryName {
    String message() default "There's already a category with this name";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
