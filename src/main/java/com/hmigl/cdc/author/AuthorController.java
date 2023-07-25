package com.hmigl.cdc.author;

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
@RequestMapping("/api/v1/authors")
public class AuthorController {

    private final @PersistenceContext EntityManager entityManager;

    public AuthorController(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<AuthorDTO> register(@Valid @RequestBody AuthorDTO authorDTO) {
        var author = Author.fromDTO(authorDTO);

        entityManager.persist(author);
        var uri = UriComponentsBuilder.fromPath("/authors/{id}")
                        .buildAndExpand(author.getId())
                        .toUri();

        return ResponseEntity.created(uri).body(authorDTO);
    }
}
