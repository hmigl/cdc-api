package com.hmigl.cdc.author;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;

import org.springframework.util.Assert;

import java.time.LocalDateTime;

@Entity
public class Author {
    private @Id @GeneratedValue Long id;

    private @NotBlank String name;
    private @NotBlank @Email String email;
    private @NotBlank @Size(max = 400) String description;
    private final @PastOrPresent LocalDateTime instant = LocalDateTime.now();

    protected Author() {}

    private Author(String name, String email, String description) {
        this.name = name;
        this.email = email;
        this.description = description;
    }

    public static Author fromDTO(@NotNull AuthorDTO authorDTO) {
        Assert.notNull(authorDTO, "authorDTO must not be null");
        return new Author(authorDTO.name(), authorDTO.email(), authorDTO.description());
    }

    @Override
    public String toString() {
        return "Author{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", description='" + description + '\'' +
                ", instant=" + instant +
                '}';
    }
}
