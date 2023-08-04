package com.hmigl.cdc.category;

import com.hmigl.cdc.book.Book;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import org.springframework.util.Assert;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Category {
    private @Id @GeneratedValue Long id;

    private @NotBlank String name;

    @OneToMany(mappedBy = "category")
    private Set<Book> books = new HashSet<>();

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
