package com.hmigl.cdc.author;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

@Component
public class UniqueAuthorEmailValidator implements Validator {
    private final AuthorRepository authorRepository;

    public UniqueAuthorEmailValidator(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return AuthorDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (errors.hasErrors()) {
            return;
        }

        var authorDTO = (AuthorDTO) target;
        Optional<Author> optionalAuthor = authorRepository.findByEmail(authorDTO.email());

        if (optionalAuthor.isPresent()) {
            errors.rejectValue("email", null, "This email is taken. Try another");
        }
    }
}
