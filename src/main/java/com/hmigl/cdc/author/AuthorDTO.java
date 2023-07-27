package com.hmigl.cdc.author;

import com.hmigl.cdc.shared.UniqueValue;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

// 2
public record AuthorDTO(
        @NotBlank String name,
        @NotBlank @Email @UniqueValue(fieldName = "email", domainClass = Author.class) String email,
        @NotBlank @Size(max = 400) String description) {}
