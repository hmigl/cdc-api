package com.hmigl.cdc.author;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/authors")
public class AuthorController {

    private final @PersistenceContext EntityManager entityManager;

    public AuthorController(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @PostMapping
    @Transactional
    public String register(@Valid @RequestBody AuthorDTO authorDTO) {
        var author = Author.fromDTO(authorDTO);
        entityManager.persist(author);
        return author.toString();
    }
}
