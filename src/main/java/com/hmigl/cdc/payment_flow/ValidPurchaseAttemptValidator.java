package com.hmigl.cdc.payment_flow;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ValidPurchaseAttemptValidator implements Validator {

    private final StateBelongsToCountryValidator stateBelongsToCountryValidator;
    private final AccurateTotalValidator accurateTotalValidator;
    private final StateWasSelectedValidator stateWasSelectedValidator;
    private final ValidCouponValidator validCouponValidator;

    public ValidPurchaseAttemptValidator(
            StateBelongsToCountryValidator stateBelongsToCountryValidator,
            AccurateTotalValidator accurateTotalValidator,
            StateWasSelectedValidator stateWasSelectedValidator,
            ValidCouponValidator validCouponValidator) {
        this.stateBelongsToCountryValidator = stateBelongsToCountryValidator;
        this.accurateTotalValidator = accurateTotalValidator;
        this.stateWasSelectedValidator = stateWasSelectedValidator;
        this.validCouponValidator = validCouponValidator;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return PurchaseAttemptRequest.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (errors.hasErrors()) {
            return;
        }

        stateBelongsToCountryValidator.validate(target, errors);
        accurateTotalValidator.validate(target, errors);
        stateWasSelectedValidator.validate(target, errors);
        validCouponValidator.validate(target, errors);
    }
}
