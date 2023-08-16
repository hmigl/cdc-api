package com.hmigl.cdc.coupon;

import org.springframework.stereotype.Repository;

@Repository
public interface CouponRepository
        extends org.springframework.data.repository.Repository<Coupon, Long> {
    Coupon getByCode(final String code);
}
