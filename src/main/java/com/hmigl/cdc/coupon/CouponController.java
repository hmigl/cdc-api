package com.hmigl.cdc.coupon;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/api/v1/coupons")
public class CouponController {

    private @PersistenceContext EntityManager manager;

    @PostMapping
    @Transactional
    public ResponseEntity<?> register(@Valid @RequestBody CouponRequest request) {
        var coupon = request.toModel();
        manager.persist(coupon);
        var uri =
                UriComponentsBuilder.fromPath("/coupons/{id}")
                        .buildAndExpand(coupon.getId())
                        .toUri();
        return ResponseEntity.created(uri).body(request);
    }
}
