package com.hmigl.cdc.book;

import static org.junit.jupiter.api.Assertions.*;

import com.hmigl.cdc.author.Author;
import com.hmigl.cdc.category.Category;

import jakarta.persistence.EntityManager;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.LocalDateTime;

class BookTest {
    private BookDTO dto =
            new BookDTO(
                    "title",
                    "overview",
                    "summary",
                    BigDecimal.valueOf(100),
                    100L,
                    "isbn",
                    LocalDateTime.now(),
                    1L,
                    1L);

    @DisplayName("should not create a book if author is null and category is registered")
    @Test
    void test() {
        EntityManager manager = Mockito.mock(EntityManager.class);

        Mockito.when(manager.find(Category.class, 1L)).thenReturn(new Category(""));
        Mockito.when(manager.find(Author.class, 1L)).thenReturn(null);

        Assertions.assertThrows(IllegalArgumentException.class, () -> Book.fromDTO(dto, manager));

        Mockito.verify(manager, Mockito.times(1)).find(Category.class, 1L);
        Mockito.verify(manager, Mockito.times(1)).find(Author.class, 1L);
    }

    @DisplayName("should not create a book if author is registered and category is null")
    @Test
    void test1() {
        EntityManager manager = Mockito.mock(EntityManager.class);

        Mockito.when(manager.find(Category.class, 1L)).thenReturn(null);
        Mockito.when(manager.find(Author.class, 1L))
                .thenReturn(new Author("author", "email@example.com", "description"));

        Assertions.assertThrows(IllegalArgumentException.class, () -> Book.fromDTO(dto, manager));

        Mockito.verify(manager, Mockito.times(1)).find(Category.class, 1L);
        Mockito.verify(manager, Mockito.times(0)).find(Author.class, 1L);
    }
}
