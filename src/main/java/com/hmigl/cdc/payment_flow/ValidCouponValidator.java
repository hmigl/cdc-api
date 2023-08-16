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
        if (errors.hasErrors()) {
            return;
        }

        PurchaseAttemptRequest request = (PurchaseAttemptRequest) target;
        Optional<String> possibleCouponCode = request.getCouponCode();
        if (possibleCouponCode.isPresent()) {
            Coupon coupon = repository.getByCode(possibleCouponCode.get());
            if (!coupon.isValid()) {
                errors.rejectValue("couponCode", null, "this coupon is no longer valid");
            }
        }
    }
}
