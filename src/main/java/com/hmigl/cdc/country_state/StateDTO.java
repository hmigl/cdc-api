package com.hmigl.cdc.country_state;

import com.hmigl.cdc.shared.IdExists;
import com.hmigl.cdc.shared.UniqueValue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record StateDTO(
        @NotBlank @UniqueValue(domainClass = State.class, fieldName = "name") String name,
        @NotNull @IdExists(domainClass = Country.class, fieldName = "id") Long country) {}
