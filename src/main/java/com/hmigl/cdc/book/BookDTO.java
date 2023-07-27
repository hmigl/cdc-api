package com.hmigl.cdc.book;

import com.hmigl.cdc.shared.UniqueValue;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import org.hibernate.validator.constraints.ISBN;

import java.math.BigDecimal;
import java.time.LocalDateTime;

// TODO: add category and author
public record BookDTO(
        @NotBlank @UniqueValue(domainClass = Book.class, fieldName = "title") String title,
        @NotBlank @Size(max = 500) String overview,
        @NotBlank String summary,
        @NotNull @Min(20) BigDecimal price,
        @NotNull @Min(100) Integer pages,
        @NotBlank @ISBN(type = ISBN.Type.ANY) @UniqueValue(domainClass = Book.class, fieldName = "isbn") String isbn,
        @NotNull @Future LocalDateTime publicationDate) {}
