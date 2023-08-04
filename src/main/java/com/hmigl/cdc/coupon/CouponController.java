package com.hmigl.cdc.coupon;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/coupons")
public class CouponController {
    @PostMapping
    public ResponseEntity<?> register(@Valid @RequestBody CouponRequest request) {
        return ResponseEntity.ok(request.toString());
    }
}
