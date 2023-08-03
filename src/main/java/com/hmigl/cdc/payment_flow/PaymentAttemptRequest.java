package com.hmigl.cdc.payment_flow;

import com.hmigl.cdc.country_state.Country;
import com.hmigl.cdc.country_state.State;
import com.hmigl.cdc.shared.CpfOrCnpj;
import com.hmigl.cdc.shared.IdExists;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PaymentAttemptRequest(
        @NotBlank String name,
        @NotBlank String lastName,
        @NotBlank @Email String email,
        @NotBlank String cellphone,
        @NotBlank @CpfOrCnpj String document,
        @NotBlank String address,
        @NotBlank String complement,
        @NotNull @IdExists(fieldName = "id", domainClass = Country.class) Long countryId,
        @IdExists(fieldName = "id", domainClass = State.class) Long stateId,
        @NotNull @Valid ShoppingCartRequest shoppingCart) {
    public boolean containsState() {
        return this.stateId != null;
    }
}
