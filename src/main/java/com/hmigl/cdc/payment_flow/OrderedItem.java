package com.hmigl.cdc.payment_flow;

import com.hmigl.cdc.book.Book;

import jakarta.persistence.Embeddable;
import jakarta.persistence.ManyToOne;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.Objects;

@Embeddable
public class OrderedItem {

    @ManyToOne private @NotNull @Valid Book book;

    private Long amount;
    private BigDecimal priceAtMoment;

    @Deprecated
    protected OrderedItem() {}

    public OrderedItem(@NotNull @Valid Book book, @NotNull @Positive Long amount) {
        this.book = book;
        this.amount = amount;
        this.priceAtMoment = book.getPrice();
    }

    public BigDecimal total() {
        return priceAtMoment.multiply(BigDecimal.valueOf(amount));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderedItem that = (OrderedItem) o;
        return Objects.equals(book, that.book);
    }

    @Override
    public int hashCode() {
        return Objects.hash(book);
    }
}
