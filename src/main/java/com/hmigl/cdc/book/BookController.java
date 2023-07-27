package com.hmigl.cdc.book;

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
@RequestMapping("/api/v1/books")
public class BookController {
    private @PersistenceContext EntityManager manager;

    @PostMapping
    @Transactional
    public ResponseEntity<BookDTO> register(@Valid @RequestBody BookDTO bookDTO) {
        var book = Book.fromDTO(bookDTO);
        manager.persist(book);
        var uri = UriComponentsBuilder.fromPath("/books/{id}").buildAndExpand(book.getId()).toUri();
        return ResponseEntity.created(uri).body(bookDTO);
    }
}
