package com.hmigl.cdc.coupon;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

public record CouponRequest(
        @NotBlank String code,
        @NotNull @Positive Integer discountPercentage,
        @NotNull @Future LocalDate expirationDate) {}
