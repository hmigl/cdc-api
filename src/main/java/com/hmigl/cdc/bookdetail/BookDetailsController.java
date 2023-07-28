package com.hmigl.cdc.bookdetail;

import com.hmigl.cdc.book.Book;

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
@RequestMapping("/api/v1/products")
public class BookDetailsController {

    private @PersistenceContext EntityManager manager;

    @GetMapping
    public ResponseEntity<BookDetailsResponse> detail(@RequestParam Long id) {
        var book =
                Optional.ofNullable(manager.find(Book.class, id))
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return ResponseEntity.ok(BookDetailsResponse.fromBook(book));
    }
}
