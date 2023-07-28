package com.hmigl.cdc.bookdetail;

import com.hmigl.cdc.author.Author;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.util.Assert;

public record AuthorDetailsResponse(@NotBlank String name, @NotBlank String description) {
    public static AuthorDetailsResponse fromAuthor(@NotNull @Valid Author author) {
        Assert.notNull(author, "author must not be null");

        return new AuthorDetailsResponse(author.getName(), author.getDescription());
    }
}
