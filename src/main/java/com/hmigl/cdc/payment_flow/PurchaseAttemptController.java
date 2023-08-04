package com.hmigl.cdc.payment_flow;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// 5
@RestController
@RequestMapping("/api/v1/payments")
public class PurchaseAttemptController {
    private @PersistenceContext EntityManager manager;

    private final StateBelongsToCountryValidator stateBelongsToCountryValidator;
    private final AccurateTotalValidator accurateTotalValidator;
    private final StateWasSelectedValidator stateWasSelectedValidator;

    public PurchaseAttemptController(
            StateBelongsToCountryValidator stateBelongsToCountryValidator,
            AccurateTotalValidator accurateTotalValidator,
            StateWasSelectedValidator stateWasSelectedValidator) {
        this.stateBelongsToCountryValidator = stateBelongsToCountryValidator;
        this.accurateTotalValidator = accurateTotalValidator;
        this.stateWasSelectedValidator = stateWasSelectedValidator;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(
                stateBelongsToCountryValidator, accurateTotalValidator, stateWasSelectedValidator);
    }

    @PostMapping
    public ResponseEntity<?> process(
            @Valid @RequestBody PurchaseAttemptRequest purchaseAttemptRequest) {
        Purchase newPurchase = purchaseAttemptRequest.toModel(manager);
        return ResponseEntity.ok(newPurchase.toString());
    }
}
