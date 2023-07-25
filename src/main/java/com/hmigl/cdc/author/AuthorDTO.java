package com.hmigl.cdc.author;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AuthorDTO(
        @NotBlank String name,
        @NotBlank @Email String email,
        @NotBlank @Size(max = 400) String description) {}
