package com.hmigl.cdc.payment_flow;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity(name = "`order`")
public class Order {
    private @Id @GeneratedValue Long id;

    @OneToOne private @NotNull @Valid Purchase purchase;

    @ElementCollection
    private @Size(min = 1) Set<OrderedItem> orderedItems = new HashSet<>();

    @Deprecated
    protected Order() {}

    public Order(Purchase purchase, Set<OrderedItem> orderedItems) {
        this.purchase = purchase;
        this.orderedItems.addAll(orderedItems);
    }

    public Long getId() {
        return id;
    }

    public Set<OrderedItem> getOrderedItems() {
        return orderedItems;
    }

    public BigDecimal total() {
        return orderedItems.stream()
                .map(OrderedItem::total)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(orderedItems, order.orderedItems);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderedItems);
    }
}
