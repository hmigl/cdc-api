package com.hmigl.cdc.payment_flow;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.HashSet;
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
}
