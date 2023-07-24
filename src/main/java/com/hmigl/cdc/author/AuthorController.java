package com.hmigl.cdc.author;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/authors")
public class AuthorController {

    @PostMapping
    public String register(@Valid @RequestBody AuthorDTO authorDTO) {
        return authorDTO.toString();
    }
}
