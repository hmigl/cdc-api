package com.hmigl.cdc.purchase_details;

import com.hmigl.cdc.payment_flow.Purchase;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/purchase-details")
public class PurchaseDetailsController {
    private @PersistenceContext EntityManager manager;

    @GetMapping
    public ResponseEntity<PurchaseDetailsResponse> detail(@RequestParam("id") Long id) {
        Purchase purchase =
                Optional.ofNullable(manager.find(Purchase.class, id))
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return ResponseEntity.ok(PurchaseDetailsResponse.fromPurchase(purchase));
    }
}
