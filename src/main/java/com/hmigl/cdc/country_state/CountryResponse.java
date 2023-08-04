package com.hmigl.cdc.country_state;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.util.Assert;

import java.util.Set;
import java.util.stream.Collectors;

public record CountryResponse(@NotBlank String name, Set<StateResponse> states) {
    public static CountryResponse fromCountry(@NotNull @Valid Country country) {
        Assert.notNull(country, "country must not be null");

        return new CountryResponse(
                country.getName(),
                country.getStates().stream()
                        .map(StateResponse::fromState)
                        .collect(Collectors.toSet()));
    }
}
