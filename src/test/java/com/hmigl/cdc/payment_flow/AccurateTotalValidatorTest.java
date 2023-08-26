package com.hmigl.cdc.payment_flow;

import com.hmigl.cdc.author.Author;
import com.hmigl.cdc.book.Book;
import com.hmigl.cdc.category.Category;

import jakarta.persistence.EntityManager;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.validation.Errors;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

class AccurateTotalValidatorTest {
    private EntityManager manager = Mockito.mock(EntityManager.class);
    private ShoppingCartRequest shoppingCart =
            new ShoppingCartRequest(
                    BigDecimal.valueOf(20),
                    Set.of(
                            new ShoppingCartRequest.ItemRequest(1L, 1L),
                            new ShoppingCartRequest.ItemRequest(2L, 1L)));
    private PurchaseAttemptRequest purchaseAttempt =
            new PurchaseAttemptRequest(
                    "Fiodor",
                    "Dostoevsky",
                    "dostoevsky@example.com",
                    "579175151",
                    "012.574.273-85",
                    "fjaljflaj",
                    "4",
                    "Moscow",
                    "561651856156",
                    1L,
                    1L,
                    shoppingCart,
                    null);
    private final Book book1 =
            new Book(
                    "b1",
                    "ljfaljf",
                    "alfjlaj",
                    BigDecimal.TEN,
                    123L,
                    "5196591569151",
                    LocalDateTime.now(),
                    Mockito.mock(Category.class),
                    Mockito.mock(Author.class));
    private final Book book2 =
            new Book(
                    "b2",
                    "falfjj",
                    "faflafj",
                    BigDecimal.TEN,
                    123L,
                    "571896751516591",
                    LocalDateTime.now(),
                    Mockito.mock(Category.class),
                    Mockito.mock(Author.class));

    @Test
    @DisplayName("should sum books prices and obtain an accurate total")
    void test() {
        Mockito.when(this.manager.find(Book.class, 1L)).thenReturn(book1);
        Mockito.when(this.manager.find(Book.class, 2L)).thenReturn(book2);

        AccurateTotalValidator validator = new AccurateTotalValidator(manager);
        Errors errors = Mockito.mock(Errors.class);
        validator.validate(purchaseAttempt, errors);

        Mockito.verify(errors, Mockito.never())
                .rejectValue(Mockito.anyString(), Mockito.any(), Mockito.anyString());
    }
}
