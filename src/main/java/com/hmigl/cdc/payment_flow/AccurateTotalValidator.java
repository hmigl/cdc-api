package com.hmigl.cdc.payment_flow;

import com.hmigl.cdc.book.Book;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.math.BigDecimal;

@Component
public class AccurateTotalValidator implements Validator {

    private @PersistenceContext EntityManager manager;

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
        ShoppingCartRequest shoppingCartRequest = request.shoppingCart();

        BigDecimal sum = BigDecimal.ZERO;
        for (var item : shoppingCartRequest.items()) {
            var book = manager.find(Book.class, item.bookId());
            sum = sum.add(book.getPrice().multiply(BigDecimal.valueOf(item.amount())));
        }

        if (shoppingCartRequest.total().compareTo(sum) != 0) {
            errors.rejectValue("shoppingCart", null, "provided total is not equal to items total");
        }
    }
}
