package com.hmigl.cdc.book;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import org.hibernate.validator.constraints.ISBN;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class Book {
    private @Id @GeneratedValue Long id;

    private @NotBlank String title;
    private @NotBlank @Size(max = 500) String overview;
    private @NotBlank String summary;
    private @NotNull @Min(20) BigDecimal price;
    private @NotNull @Min(100) Long pages;
    private @NotBlank @ISBN(type = ISBN.Type.ANY) String isbn;
    private @NotNull @Future LocalDateTime publicationDate;
    private @NotNull Long categoryId;
    private @NotNull Long authorId;

    @Deprecated
    protected Book() {}

    private Book(
            @NotBlank String title,
            @NotBlank @Size(max = 500) String overview,
            @NotBlank String summary,
            @NotNull @Min(20) BigDecimal price,
            @NotNull @Min(100) Long pages,
            @NotBlank @ISBN(type = ISBN.Type.ANY) String isbn,
            @NotNull @Future LocalDateTime publicationDate,
            @NotNull Long categoryId,
            @NotNull Long authorId) {
        this.title = title;
        this.overview = overview;
        this.summary = summary;
        this.price = price;
        this.pages = pages;
        this.isbn = isbn;
        this.publicationDate = publicationDate;
        this.categoryId = categoryId;
        this.authorId = authorId;
    }

    public static Book fromDTO(@NotNull BookDTO bookDTO) {
        Assert.notNull(bookDTO, "bookDTO must not be null");

        Assert.hasLength(bookDTO.title(), "title must not be blank");

        Assert.hasLength(bookDTO.overview(), "overview must not be blank");
        Assert.isTrue(
                bookDTO.overview().length() <= 500, "overview should not exceed 500 characters");

        Assert.hasLength(bookDTO.summary(), "summary must not be blank");

        Assert.notNull(bookDTO.price(), "price must not be provided");
        Assert.isTrue(
                bookDTO.price().compareTo(new BigDecimal(20)) != -1, "price should be at least 20");

        Assert.notNull(bookDTO.pages(), "number of pages must be provided");
        Assert.isTrue(bookDTO.pages() >= 100, "book must have at least 100 pages");

        Assert.hasLength(bookDTO.isbn(), "isbn must not be null"); // TODO: isbn validation?

        Assert.notNull(
                bookDTO.publicationDate(),
                "a publication date must be provided"); // TODO: @Future validation?

        Assert.notNull(bookDTO.categoryId(), "a category must be provided");

        Assert.notNull(bookDTO.authorId(), "an author must be specified");

        return new Book(
                bookDTO.title(),
                bookDTO.overview(),
                bookDTO.summary(),
                bookDTO.price(),
                bookDTO.pages(),
                bookDTO.isbn(),
                bookDTO.publicationDate(),
                bookDTO.categoryId(),
                bookDTO.authorId());
    }

    public Long getId() {
        return id;
    }
}
