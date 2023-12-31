package com.hmigl.cdc.purchase_details;

import com.hmigl.cdc.payment_flow.Purchase;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.Set;

public record PurchaseDetailsResponse(
        Long id,
        String sentTo,
        String address,
        String cep,
        String city,
        String complement,
        String country,
        String state,
        BigDecimal total,
        Set<BoughtItem> boughtItems,
        boolean coupon,
        BigDecimal totalWithDiscount) {
    public static PurchaseDetailsResponse fromPurchase(Purchase purchase) {
        Assert.notNull(purchase, "purchase must not be null");
        return new PurchaseDetailsResponse(
                purchase.getId(),
                purchase.getName() + " " + purchase.getLastName(),
                purchase.getAddress(),
                purchase.getCep(),
                purchase.getCity(),
                purchase.getComplement(),
                purchase.getCountry().getName(),
                purchase.getState().getName(),
                purchase.total(),
                BoughtItem.mapOrderedItems(purchase.getOrder().getOrderedItems()),
                purchase.coupon(),
                purchase.totalWithDiscount());
    }
}
