package com.hmigl.cdc.book;

import com.hmigl.cdc.author.AuthorDTO;
import com.hmigl.cdc.category.CategoryDTO;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import org.hibernate.validator.constraints.ISBN;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record BookDTO(
        @NotBlank String title, // TODO: should also be unique
        @NotBlank @Size(max = 500) String overview,
        @NotBlank String summary,
        @NotNull @Min(20) BigDecimal price,
        @NotNull @Min(100) Integer pages,
        @NotBlank @ISBN(type = ISBN.Type.ANY) String isbn, // TODO: should also be unique
        @NotNull @Future LocalDateTime publicationDate,
        @NotNull CategoryDTO category,
        @NotNull AuthorDTO author) {}
