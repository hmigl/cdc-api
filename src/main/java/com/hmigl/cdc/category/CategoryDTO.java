package com.hmigl.cdc.category;

import jakarta.validation.constraints.NotBlank;

public record CategoryDTO(@NotBlank @UniqueCategoryName String name) {}
