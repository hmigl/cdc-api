package com.hmigl.cdc.category;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UniqueCategoryNameValidator
        implements ConstraintValidator<UniqueCategoryName, Object> {

    private final CategoryRepository repository;

    public UniqueCategoryNameValidator(CategoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public void initialize(UniqueCategoryName constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        return repository.findByName(o.toString()).isEmpty();
    }
}
