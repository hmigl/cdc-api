package com.hmigl.cdc.author;

import com.hmigl.cdc.book.Book;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Author {
    private @Id @GeneratedValue Long id;

    private @NotBlank String name;
    private @NotBlank @Email String email;
    private @NotBlank @Size(max = 400) String description;
    private final @PastOrPresent LocalDateTime instant = LocalDateTime.now();

    @OneToMany(mappedBy = "author")
    private Set<Book> books = new HashSet<>();

    protected Author() {}

    public Author(String name, String email, String description) {
        this.name = name;
        this.email = email;
        this.description = description;
    }

    public static Author fromDTO(@NotNull AuthorDTO authorDTO) {
        Assert.notNull(authorDTO, "authorDTO must not be null");

        Assert.hasLength(authorDTO.name(), "name must not be blank");

        Assert.hasLength(authorDTO.email(), "email must not be blank");
        Assert.isTrue(
                EmailValidator.getInstance().isValid(authorDTO.email()), "email must be valid");

        Assert.hasLength(authorDTO.description(), "description must not be blank");
        Assert.isTrue(
                authorDTO.description().length() <= 400,
                "description cannot exceed 400 characters");

        return new Author(authorDTO.name(), authorDTO.email(), authorDTO.description());
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
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
