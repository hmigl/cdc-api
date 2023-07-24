package com.hmigl.cdc.author;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

import java.time.Instant;

public record AuthorDTO(
        @NotBlank String name,
        @NotBlank @Email String email,
        @NotBlank @Size(max = 400) String description,
        @PastOrPresent Instant instant) {

    public AuthorDTO(
            @NotBlank String name,
            @NotBlank @Email String email,
            @NotBlank @Size(max = 400) String description,
            @PastOrPresent Instant instant) {
        this.name = name;
        this.email = email;
        this.description = description;
        this.instant = Instant.now();
    }
}
