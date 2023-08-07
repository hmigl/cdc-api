package com.hmigl.cdc.payment_flow;

import com.hmigl.cdc.coupon.Coupon;
import com.hmigl.cdc.coupon.CouponRepository;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

// 6
@Component
public class ValidCouponValidator implements Validator {
    private final CouponRepository repository;

    public ValidCouponValidator(CouponRepository repository) {
        this.repository = repository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return PurchaseAttemptRequest.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        PurchaseAttemptRequest request = (PurchaseAttemptRequest) target;
        if (errors.hasErrors() || request.coupon() == null) {
            return;
        }

        Optional<Coupon> optionalCoupon = repository.findByCode(request.coupon());
        if (optionalCoupon.isEmpty()) {
            errors.rejectValue("coupon", null, "there is no such coupon");
            return;
        }

        if (!optionalCoupon.get().stillValid()) {
            errors.rejectValue("coupon", null, "coupon is no longer valid");
        }
    }
}
