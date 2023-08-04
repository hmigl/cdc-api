package com.hmigl.cdc.payment_flow;

import com.hmigl.cdc.country_state.Country;
import com.hmigl.cdc.country_state.State;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

// 6
@Component
public class StateBelongsToCountryValidator implements Validator {

    private @PersistenceContext EntityManager manager;

    @Override
    public boolean supports(Class<?> clazz) {
        return PurchaseAttemptRequest.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (errors.hasErrors()) {
            return;
        }

        PurchaseAttemptRequest request = (PurchaseAttemptRequest) target;

        if (request.containsState()) {
            Country country = manager.find(Country.class, request.countryId());
            State state = manager.find(State.class, request.stateId());

            if (!state.belongsTo(country)) {
                errors.rejectValue("stateId", null, "state does not belong to selected country");
            }
        }
    }
}
