package com.hmigl.cdc.category;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import org.springframework.util.Assert;

@Entity
public class Category {
    private @Id @GeneratedValue Long id;

    private @NotBlank String name;

    @Deprecated
    protected Category() {}

    private Category(String name) {
        this.name = name;
    }

    public static Category fromDTO(@NotNull CategoryDTO categoryDTO) {
        Assert.notNull(categoryDTO, "category must not be null");

        Assert.hasLength(categoryDTO.name(), "name must not be blank");

        return new Category(categoryDTO.name());
    }

    public Long getId() {
        return id;
    }
}
