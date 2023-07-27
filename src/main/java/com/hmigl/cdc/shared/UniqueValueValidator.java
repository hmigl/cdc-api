package com.hmigl.cdc.shared;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.util.Assert;

import java.util.List;

public class UniqueValueValidator implements ConstraintValidator<UniqueValue, Object> {
    private @PersistenceContext EntityManager manager;
    private String fieldName;
    private Class<?> domainClass;

    @Override
    public void initialize(UniqueValue constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        this.fieldName = constraintAnnotation.fieldName();
        this.domainClass = constraintAnnotation.domainClass();
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        Query query =
                manager.createQuery(
                        "SELECT 1 FROM " + domainClass.getName() + " WHERE " + fieldName + " =:o");
        query.setParameter("o", o);
        List<?> res = query.getResultList();

        Assert.state(res.size() <= 1, "More than 1 " + domainClass + " was found");

        return res.isEmpty();
    }
}
