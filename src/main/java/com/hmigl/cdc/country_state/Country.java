package com.hmigl.cdc.country_state;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import org.springframework.util.Assert;

@Entity
public class Country {
    private @Id @GeneratedValue Long id;

    private @NotBlank String name;

    @Deprecated
    protected Country() {}

    private Country(@NotBlank String name) {
        this.name = name;
    }

    public static Country fromDTO(@Valid @NotNull CountryDTO countryDTO) {
        Assert.notNull(countryDTO, "countryDTO must not be null");
        Assert.hasLength(countryDTO.name(), "name must not be blank");
        return new Country(countryDTO.name());
    }

    public Long getId() {
        return id;
    }
}
