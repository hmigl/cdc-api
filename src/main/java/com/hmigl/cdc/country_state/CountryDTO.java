package com.hmigl.cdc.country_state;

import jakarta.validation.constraints.NotBlank;

public record CountryDTO(@NotBlank String name) {}
