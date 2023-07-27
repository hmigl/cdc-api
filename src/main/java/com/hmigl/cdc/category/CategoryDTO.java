package com.hmigl.cdc.category;

import com.hmigl.cdc.shared.UniqueValue;

import jakarta.validation.constraints.NotBlank;

// 2
public record CategoryDTO(
        @NotBlank @UniqueValue(fieldName = "name", domainClass = Category.class) String name) {}
