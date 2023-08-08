package com.hmigl.cdc.coupon;

import com.hmigl.cdc.shared.UniqueValue;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CouponRequest(
        @NotBlank @UniqueValue(fieldName = "code", domainClass = Coupon.class) String code,
        @NotNull @Positive BigDecimal discountPercentage,
        @NotNull @Future LocalDate expirationDate) {
    public Coupon toModel() {
        return new Coupon(code, discountPercentage, expirationDate);
    }
}
