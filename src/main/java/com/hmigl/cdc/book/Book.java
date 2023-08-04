package com.hmigl.cdc.book;

import com.hmigl.cdc.author.Author;
import com.hmigl.cdc.category.Category;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import org.hibernate.validator.constraints.ISBN;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Entity
public class Book {
    private @Id @GeneratedValue Long id;

    private @NotBlank String title;
    private @NotBlank @Size(max = 500) String overview;
    private @NotBlank String summary;
    private @NotNull @Min(20) BigDecimal price;
    private @NotNull @Min(100) Long pages;
    private @NotBlank @ISBN(type = ISBN.Type.ANY) String isbn;
    private @NotNull @Future LocalDateTime publicationDate;

    @ManyToOne
    @JoinColumn(name = "category_id_fk")
    private @NotNull @Valid Category category;

    @ManyToOne
    @JoinColumn(name = "author_id_fk")
    private @NotNull @Valid Author author;

    @Deprecated
    protected Book() {}

    private Book(
            @NotBlank String title,
            @NotBlank @Size(max = 500) String overview,
            @NotBlank String summary,
            @NotNull @Min(20) BigDecimal price,
            @NotNull @Min(100) Long pages,
            @NotBlank @ISBN(type = ISBN.Type.ANY) String isbn,
            @NotNull @Future LocalDateTime publicationDate,
            @NotNull @Valid Category category,
            @NotNull @Valid Author author) {
        this.title = title;
        this.overview = overview;
        this.summary = summary;
        this.price = price;
        this.pages = pages;
        this.isbn = isbn;
        this.publicationDate = publicationDate;
        this.category = category;
        this.author = author;
    }

    public static Book fromDTO(@NotNull @Valid BookDTO bookDTO, EntityManager manager) {
        Book.validate(bookDTO);

        var category = manager.find(Category.class, bookDTO.categoryId());
        Assert.notNull(category, "provided category does not exist");

        var author = manager.find(Author.class, bookDTO.authorId());
        Assert.notNull(author, "provided author is not available");

        return new Book(
                bookDTO.title(),
                bookDTO.overview(),
                bookDTO.summary(),
                bookDTO.price(),
                bookDTO.pages(),
                bookDTO.isbn(),
                bookDTO.publicationDate(),
                category,
                author);
    }

    private static void validate(@NotNull @Valid BookDTO bookDTO) {
        Assert.notNull(bookDTO, "bookDTO must not be null");

        Assert.hasLength(bookDTO.title(), "title must not be blank");

        Assert.hasLength(bookDTO.overview(), "overview must not be blank");
        Assert.isTrue(
                bookDTO.overview().length() <= 500, "overview should not exceed 500 characters");

        Assert.hasLength(bookDTO.summary(), "summary must not be blank");

        Assert.notNull(bookDTO.price(), "price must not be provided");
        Assert.isTrue(
                bookDTO.price().compareTo(new BigDecimal(20)) != -1, "price should be at least 20");

        Assert.notNull(bookDTO.pages(), "number of pages must be provided");
        Assert.isTrue(bookDTO.pages() >= 100, "book must have at least 100 pages");

        Assert.hasLength(bookDTO.isbn(), "isbn must not be null"); // TODO: isbn validation?

        Assert.notNull(
                bookDTO.publicationDate(),
                "a publication date must be provided"); // TODO: @Future validation?

        Assert.notNull(bookDTO.categoryId(), "a category must be provided");

        Assert.notNull(bookDTO.authorId(), "an author must be specified");
    }

    public String formatPublicationDate(final String pattern) {
        Assert.hasLength(pattern, "pattern cannot be blank and must be valid");

        return this.publicationDate.format(DateTimeFormatter.ofPattern(pattern));
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public String getSummary() {
        return summary;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Long getPages() {
        return pages;
    }

    public String getIsbn() {
        return isbn;
    }

    public LocalDateTime getPublicationDate() {
        return publicationDate;
    }

    public Category getCategory() {
        return category;
    }

    public Author getAuthor() {
        return author;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(isbn, book.isbn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isbn);
    }
}
