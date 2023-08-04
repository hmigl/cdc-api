package com.hmigl.cdc.coupon;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

@Entity
public class Coupon {
    private @Id @GeneratedValue Long id;

    private @NotBlank String code;
    private @NotNull @Positive Integer discountPercentage;
    private @NotNull @Future LocalDate expirationDate;

    @Deprecated
    protected Coupon() {}

    public Coupon(
            @NotBlank String code,
            @NotNull @Positive Integer discountPercentage,
            @NotNull @Future LocalDate expirationDate) {
        this.code = code;
        this.discountPercentage = discountPercentage;
        this.expirationDate = expirationDate;
    }

    public Long getId() {
        return id;
    }
}
