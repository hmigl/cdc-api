package com.hmigl.cdc.payment_flow;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/payments")
public class PaymentAttemptController {

    private final StateBelongsToCountryValidator stateBelongsToCountryValidator;

    public PaymentAttemptController(StateBelongsToCountryValidator stateBelongsToCountryValidator) {
        this.stateBelongsToCountryValidator = stateBelongsToCountryValidator;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(stateBelongsToCountryValidator);
    }

    @PostMapping
    public ResponseEntity<?> register(
            @Valid @RequestBody PaymentAttemptRequest paymentAttemptRequest) {
        return ResponseEntity.ok(paymentAttemptRequest.toString());
    }
}
