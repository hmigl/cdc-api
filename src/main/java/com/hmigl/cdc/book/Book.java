package com.hmigl.cdc.book;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Book {

    private @Id @GeneratedValue Long id;

    public static Book fromDTO(BookDTO bookDTO) {
        return new Book();
    }

    public Long getId() {
        return id;
    }
}
