package com.hmigl.cdc.payment_flow;

import com.hmigl.cdc.coupon.CouponRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

// 4
@RestController
@RequestMapping("/api/v1/purchases")
public class PurchaseAttemptController {
    private @PersistenceContext EntityManager manager;
    private final CouponRepository repository;

    private final ValidPurchaseAttemptValidator validPurchaseAttemptValidator;

    public PurchaseAttemptController(
            CouponRepository repository,
            ValidPurchaseAttemptValidator validPurchaseAttemptValidator) {
        this.repository = repository;
        this.validPurchaseAttemptValidator = validPurchaseAttemptValidator;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(validPurchaseAttemptValidator);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<PurchaseAttemptRequest> process(
            @Valid @RequestBody PurchaseAttemptRequest purchaseAttemptRequest) {
        var purchase = purchaseAttemptRequest.toModel(manager, repository);
        manager.persist(purchase);
        var uri =
                UriComponentsBuilder.fromPath("/purchases/{id}")
                        .buildAndExpand(purchase.getId())
                        .toUri();
        return ResponseEntity.created(uri).body(purchaseAttemptRequest);
    }
}
