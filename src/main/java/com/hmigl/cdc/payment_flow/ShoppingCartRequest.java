package com.hmigl.cdc.payment_flow;

import com.hmigl.cdc.book.Book;
import com.hmigl.cdc.shared.IdExists;

import jakarta.persistence.EntityManager;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public record ShoppingCartRequest(
        @NotNull @Positive BigDecimal total, @Valid @Size(min = 1) Set<ItemRequest> items) {
    public Function<Purchase, Order> toModel(EntityManager manager) {
        Set<OrderedItem> orderedItems =
                items().stream().map(item -> item.toModel(manager)).collect(Collectors.toSet());
        return (purchase) -> new Order(purchase, orderedItems);
    }

    public record ItemRequest(
            @NotNull @IdExists(fieldName = "id", domainClass = Book.class) Long bookId,
            @NotNull @Positive Long amount) {
        public OrderedItem toModel(EntityManager manager) {
            return new OrderedItem(manager.find(Book.class, bookId), amount);
        }
    }
}
