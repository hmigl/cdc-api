package com.hmigl.cdc.country_state;

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
@RequestMapping("/api/v1/countries")
public class CountryController {

    private @PersistenceContext EntityManager manager;

    @PostMapping
    @Transactional
    public ResponseEntity<CountryDTO> register(@Valid @RequestBody CountryDTO countryDTO) {
        var country = Country.fromDTO(countryDTO);
        manager.persist(country);
        var uri =
                UriComponentsBuilder.fromPath("/countries/{id}")
                        .buildAndExpand(country.getId())
                        .toUri();
        return ResponseEntity.created(uri).body(countryDTO);
    }

    @GetMapping
    public ResponseEntity<List<CountryResponse>> display() {
        return ResponseEntity.ok(
                manager.createQuery("SELECT c from Country c", Country.class)
                        .getResultList()
                        .stream()
                        .map(CountryResponse::fromCountry)
                        .collect(Collectors.toList()));
    }
}
