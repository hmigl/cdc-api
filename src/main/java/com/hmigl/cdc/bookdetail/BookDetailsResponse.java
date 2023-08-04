package com.hmigl.cdc.bookdetail;

import com.hmigl.cdc.book.Book;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import org.hibernate.validator.constraints.ISBN;
import org.springframework.util.Assert;

import java.math.BigDecimal;

public record BookDetailsResponse(
        @NotBlank String title,
        @NotNull AuthorDetailsResponse author,
        @NotNull @Min(20) BigDecimal price,
        @NotBlank String overview,
        @NotBlank String summary,
        @NotNull Long pages,
        @ISBN String isbn,
        @NotBlank String publicationDate) {

    public static BookDetailsResponse fromBook(@NotNull @Valid Book book) {
        Assert.notNull(book, "book must not be null");

        return new BookDetailsResponse(
                book.getTitle(),
                AuthorDetailsResponse.fromAuthor(book.getAuthor()),
                book.getPrice(),
                book.getOverview(),
                book.getSummary(),
                book.getPages(),
                book.getIsbn(),
                book.formatPublicationDate("MM/yyyy"));
    }
}
