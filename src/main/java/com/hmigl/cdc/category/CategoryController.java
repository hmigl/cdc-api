package com.hmigl.cdc.category;

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
@RequestMapping("/api/v1/categories")
public class CategoryController {

    private @PersistenceContext EntityManager manager;

    @PostMapping
    @Transactional
    public ResponseEntity<CategoryDTO> register(@Valid @RequestBody CategoryDTO categoryDTO) {
        var category = Category.fromDTO(categoryDTO);
        manager.persist(category);
        var uri =
                UriComponentsBuilder.fromPath("/categories/{id}")
                        .buildAndExpand(category.getId())
                        .toUri();

        return ResponseEntity.created(uri).body(categoryDTO);
    }
}
