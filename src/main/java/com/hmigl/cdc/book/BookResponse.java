package com.hmigl.cdc.book;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import org.springframework.util.Assert;

public record BookResponse(@NotNull @Positive Long id, @NotBlank String title) {
    public static BookResponse fromBook(@NotNull @Valid Book book) {
        Assert.notNull(book, "book must not be null");

        Assert.state(book.getId() != null, "this id does not exist");
        Assert.hasLength(book.getTitle(), "title cannot be blank");

        return new BookResponse(book.getId(), book.getTitle());
    }
}
