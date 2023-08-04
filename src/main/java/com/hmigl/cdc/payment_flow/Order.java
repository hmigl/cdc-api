package com.hmigl.cdc.payment_flow;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Order {
    private @Id @GeneratedValue Long id;

    @OneToOne(mappedBy = "order")
    private @NotNull @Valid Purchase purchase;

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
}
