package com.hmigl.cdc.book;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/books")
// 3
public class BookController {
    private @PersistenceContext EntityManager manager;

    @PostMapping
    @Transactional
    public ResponseEntity<BookDTO> register(@Valid @RequestBody BookDTO bookDTO) {
        var book = Book.fromDTO(bookDTO, manager);
        manager.persist(book);
        var uri = UriComponentsBuilder.fromPath("/books/{id}").buildAndExpand(book.getId()).toUri();
        return ResponseEntity.created(uri).body(bookDTO);
    }

    @GetMapping
    public ResponseEntity<List<BookResponse>> display() {
        return ResponseEntity.ok(
                manager.createQuery("SELECT b FROM Book b", Book.class).getResultList().stream()
                        .map(BookResponse::fromBook)
                        .collect(Collectors.toList()));
    }
}
