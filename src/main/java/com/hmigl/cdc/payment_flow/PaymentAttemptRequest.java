package com.hmigl.cdc.payment_flow;

import com.hmigl.cdc.country_state.Country;
import com.hmigl.cdc.country_state.State;
import com.hmigl.cdc.shared.CpfOrCnpj;
import com.hmigl.cdc.shared.IdExists;

import jakarta.persistence.EntityManager;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

// 4
public record PaymentAttemptRequest(
        @NotBlank String name,
        @NotBlank String lastName,
        @NotBlank @Email String email,
        @NotBlank String cellphone,
        @NotBlank @CpfOrCnpj String document,
        @NotBlank String address,
        @NotBlank String complement,
        @NotBlank String city,
        @NotBlank String cep,
        @NotNull @IdExists(fieldName = "id", domainClass = Country.class) Long countryId,
        @IdExists(fieldName = "id", domainClass = State.class) Long stateId,
        @NotNull @Valid ShoppingCartRequest shoppingCart) {
    public boolean containsState() {
        return this.stateId != null;
    }

    public Purchase toModel(EntityManager manager) {
        var builder =
                new Purchase.Builder()
                        .name(name)
                        .lastName(lastName)
                        .email(email)
                        .cellphone(cellphone)
                        .document(document)
                        .address(address)
                        .complement(complement)
                        .city(city)
                        .cep(cep)
                        .country(manager.find(Country.class, countryId));

        if (stateId != null) {
            builder.state(manager.find(State.class, stateId));
        }

        return builder.build();
    }
}
