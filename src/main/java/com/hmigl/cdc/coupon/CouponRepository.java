package com.hmigl.cdc.coupon;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CouponRepository extends CrudRepository<Coupon, Long> {
    Optional<Coupon> findByCode(final String coupon);
}
