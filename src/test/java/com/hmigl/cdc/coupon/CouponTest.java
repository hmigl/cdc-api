package com.hmigl.cdc.coupon;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

class CouponTest {
    @DisplayName("past date coupon should be invalid")
    @Test
    void test() {
        Coupon coupon = new Coupon("", BigDecimal.TEN, LocalDate.now().minusDays(1L));
        Assertions.assertFalse(coupon.isValid());
    }

    @DisplayName("future date coupon should be valid")
    @Test
    void test1() {
        Coupon coupon = new Coupon("", BigDecimal.TEN, LocalDate.now().plusDays(1L));
        Assertions.assertTrue(coupon.isValid());
    }
}
