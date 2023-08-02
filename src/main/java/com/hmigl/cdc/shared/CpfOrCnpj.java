package com.hmigl.cdc.shared;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import org.hibernate.validator.constraints.CompositionType;
import org.hibernate.validator.constraints.ConstraintComposition;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@CPF
@CNPJ
@Target(ElementType.FIELD)
@Constraint(validatedBy = {})
@Retention(RetentionPolicy.RUNTIME)
@ConstraintComposition(CompositionType.OR)
public @interface CpfOrCnpj {
    String message() default "invalid document, should be valid for either @CPF or @CNPJ";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
