package com.hmigl.cdc.payment_flow;

import com.hmigl.cdc.book.Book;
import com.hmigl.cdc.shared.IdExists;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.Set;

public record ShoppingCartRequest(
        @NotNull @Positive BigDecimal total, @Valid @Size(min = 1) Set<ItemRequest> items) {
    public record ItemRequest(
            @NotNull @IdExists(fieldName = "id", domainClass = Book.class) Long bookId,
            @NotNull @Positive Long amount) {}
}
