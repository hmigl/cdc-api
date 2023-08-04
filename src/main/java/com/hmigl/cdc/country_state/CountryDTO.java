package com.hmigl.cdc.country_state;

import com.hmigl.cdc.shared.UniqueValue;
import jakarta.validation.constraints.NotBlank;

public record CountryDTO(
        @NotBlank
        @UniqueValue(domainClass = Country.class, fieldName = "name", message = "This country was already registered")
        String name) {}
