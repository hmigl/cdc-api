package com.hmigl.cdc.book;

import com.hmigl.cdc.author.Author;
import com.hmigl.cdc.category.Category;
import com.hmigl.cdc.shared.IdExists;
import com.hmigl.cdc.shared.UniqueValue;

import jakarta.validation.constraints.*;

import org.hibernate.validator.constraints.ISBN;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record BookDTO(
        @NotBlank @UniqueValue(domainClass = Book.class, fieldName = "title") String title,
        @NotBlank @Size(max = 500) String overview,
        @NotBlank String summary,
        @NotNull @Min(20) BigDecimal price,
        @NotNull @Min(100) Long pages,
        @NotBlank @ISBN(type = ISBN.Type.ANY) @UniqueValue(domainClass = Book.class, fieldName = "isbn") String isbn,
        @NotNull @Future LocalDateTime publicationDate,
        @NotNull @IdExists(domainClass = Category.class, fieldName = "id") Long categoryId,
        @NotNull @IdExists(domainClass = Author.class, fieldName = "id") Long authorId) {}
