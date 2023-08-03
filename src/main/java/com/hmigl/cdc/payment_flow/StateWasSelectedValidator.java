package com.hmigl.cdc.payment_flow;

import com.hmigl.cdc.country_state.Country;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class StateWasSelectedValidator implements Validator {

    private @PersistenceContext EntityManager manager;

    @Override
    public boolean supports(Class<?> clazz) {
        return PaymentAttemptRequest.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (errors.hasErrors()) {
            return;
        }

        PaymentAttemptRequest request = (PaymentAttemptRequest) target;
        Country country = manager.find(Country.class, request.countryId());
        if (country.hasStates() && request.stateId() == null) {
            errors.rejectValue(
                    "countryId",
                    null,
                    "selected country has states, so one of them must be selected");
        }
    }
}
