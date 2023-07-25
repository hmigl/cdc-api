package com.hmigl.cdc.author;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

// 2
public class UniqueAuthorEmailValidator implements ConstraintValidator<UniqueAuthorEmail, Object> {
    private final AuthorRepository repository;

    public UniqueAuthorEmailValidator(AuthorRepository repository) {
        this.repository = repository;
    }

    @Override
    public void initialize(UniqueAuthorEmail constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        return repository.findByEmail(o.toString()).isEmpty();
    }
}
