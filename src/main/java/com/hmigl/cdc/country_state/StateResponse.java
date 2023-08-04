package com.hmigl.cdc.country_state;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.util.Assert;

public record StateResponse(@NotBlank String name, @NotNull Long id) {
    public static StateResponse fromState(@NotNull @Valid State state) {
        Assert.notNull(state, "state must not be null");

        return new StateResponse(state.getName(), state.getId());
    }
}
