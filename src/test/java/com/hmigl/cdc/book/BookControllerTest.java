package com.hmigl.cdc.book;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import net.jqwik.api.Arbitrary;
import net.jqwik.api.ForAll;
import net.jqwik.api.Label;
import net.jqwik.api.Property;
import net.jqwik.api.Provide;
import net.jqwik.api.constraints.AlphaChars;
import net.jqwik.api.constraints.BigRange;
import net.jqwik.api.constraints.LongRange;
import net.jqwik.api.constraints.StringLength;
import net.jqwik.spring.JqwikSpringSupport;
import net.jqwik.time.api.DateTimes;

import org.junit.jupiter.api.Assumptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@SpringBootTest
@AutoConfigureMockMvc
@JqwikSpringSupport
class BookControllerTest {
    private static final String URI = "/api/v1/books";
    private static final Set<String> ISBNS_AND_TITLES = new HashSet<>();

    private @Autowired MockMvc mockMvc;
    private @Autowired Jackson2ObjectMapperBuilder mapperBuilder;

    @Property(tries = 10)
    @Label("should register a book and not let it be registered again")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    void test(
            @ForAll @AlphaChars @StringLength(min = 1, max = 255) String title,
            @ForAll @AlphaChars @StringLength(min = 1, max = 500) String overview,
            @ForAll @AlphaChars @StringLength(min = 1, max = 255) String summary,
            @ForAll @BigRange(min = "20", max = "500") BigDecimal price,
            @ForAll @LongRange(min = 100) Long pages,
//            @ForAll @NumericChars @StringLength(min = 10, max = 10) String isbn,
            @ForAll("futureDates") LocalDateTime publicationDate)
            throws Exception {

        Assumptions.assumeTrue(ISBNS_AND_TITLES.add(title));
//        Assumptions.assumeTrue(ISBNS_AND_TITLES.add(isbn));

        this.mockMvc
                .perform(post("/api/v1/authors").contentType(MediaType.APPLICATION_JSON)
                        .content(mapperBuilder.build().writeValueAsString(
                                        Map.of("name", "Dostoevsky",
                                                "email", "dostoevsky@example.com",
                                                "description", "beauty will save the world")
                                )
                        )
                );
        this.mockMvc
                .perform(post("/api/v1/categories").contentType(MediaType.APPLICATION_JSON)
                        .content(mapperBuilder.build().writeValueAsString(Map.of("name", "russian literature"))));

        String payload =
                mapperBuilder.build().registerModule(new JavaTimeModule())
                        .writeValueAsString(
                                Map.of("title", title,
                                        "overview", overview,
                                        "summary", summary,
                                        "price", price,
                                        "pages", pages,
                                        "isbn", "978-8588808836",
//                                        "isbn", isbn,
                                        "publicationDate", publicationDate,
                                        "categoryId", "1",
                                        "authorId", "1")
                        );

        this.mockMvc
                .perform(post(URI).contentType(MediaType.APPLICATION_JSON).content(payload))
                .andExpect(status().is2xxSuccessful());

        this.mockMvc
                .perform(post(URI).contentType(MediaType.APPLICATION_JSON).content(payload))
                .andExpect(status().is4xxClientError());
    }

    @Provide
    Arbitrary<LocalDateTime> futureDates() {
        return DateTimes.dateTimes().atTheEarliest(LocalDateTime.now().plusDays(1));
    }
}
