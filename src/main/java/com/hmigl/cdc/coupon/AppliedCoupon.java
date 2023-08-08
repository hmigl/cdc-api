package com.hmigl.cdc.coupon;

import jakarta.persistence.Embeddable;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;

@Embeddable
public class AppliedCoupon {

    @ManyToOne private Coupon coupon;
    @NotNull @Positive private BigDecimal discountPercentageAtMoment;
    @NotNull @Future private LocalDate expirationDateAtMoment;

    @Deprecated
    protected AppliedCoupon() {}

    public AppliedCoupon(Coupon coupon) {
        this.coupon = coupon;
        this.discountPercentageAtMoment = coupon.getDiscountPercentage();
        this.expirationDateAtMoment = coupon.getExpirationDate();
    }
}
