package com.hmigl.cdc.purchase_details;

import com.hmigl.cdc.payment_flow.OrderedItem;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;

public record BoughtItem(String book, Long amount, BigDecimal price) {
    public static BoughtItem fromOrderedItem(OrderedItem item) {
        Assert.notNull(item, "item must not be null");
        return new BoughtItem(item.getBook().getTitle(), item.getAmount(), item.getPriceAtMoment());
    }

    public static Set<BoughtItem> mapOrderedItems(Set<OrderedItem> orderedItems) {
        return orderedItems.stream().map(BoughtItem::fromOrderedItem).collect(Collectors.toSet());
    }
}
