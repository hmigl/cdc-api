package com.hmigl.cdc.country_state;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import org.springframework.util.Assert;

@Entity
public class State {

    private @Id @GeneratedValue Long id;

    private @NotBlank String name;

    @ManyToOne
    @JoinColumn(name = "country_id_fk")
    private @NotNull Country country;

    @Deprecated
    protected State() {}

    private State(String name, Country country) {
        this.name = name;
        this.country = country;
    }

    public static State fromDTO(@Valid @NotNull StateDTO stateDTO, EntityManager manager) {
        Assert.notNull(stateDTO, "stateDTO must not be null");

        var country = manager.find(Country.class, stateDTO.country());
        Assert.state(country != null, "country must not be null");

        return new State(stateDTO.name(), country);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
