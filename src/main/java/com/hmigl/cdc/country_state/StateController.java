package com.hmigl.cdc.country_state;

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
@RequestMapping("/api/v1/states")
public class StateController {
    private @PersistenceContext EntityManager manager;

    @PostMapping
    @Transactional
    public ResponseEntity<StateDTO> register(@Valid @RequestBody StateDTO stateDTO) {
        var state = State.fromDTO(stateDTO, manager);
        manager.persist(state);
        var uri =
                UriComponentsBuilder.fromPath("/states/{id}").buildAndExpand(state.getId()).toUri();

        return ResponseEntity.created(uri).body(stateDTO);
    }
}
