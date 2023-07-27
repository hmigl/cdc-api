package com.hmigl.cdc.author;

import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;

import net.jqwik.api.ForAll;
import net.jqwik.api.Label;
import net.jqwik.api.Property;
import net.jqwik.api.constraints.AlphaChars;
import net.jqwik.api.constraints.StringLength;
import net.jqwik.spring.JqwikSpringSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@JqwikSpringSupport
@SpringBootTest
@AutoConfigureMockMvc
class AuthorControllerTest {
    private static final String URI = "/api/v1/authors";
    private static Set<String> emails = new HashSet<>();
    private @Autowired MockMvc mockMvc;

    @Property(tries = 10)
    @Label("new author registration")
    void test1(
            @ForAll @AlphaChars @StringLength(min = 1, max = 255) String name,
            @ForAll @AlphaChars @StringLength(min = 1, max = 30) String email,
            @ForAll @AlphaChars @StringLength(min = 1, max = 400) String description)
            throws Exception {

        assumeTrue(emails.add(email));

        String payload = new ObjectMapper().writeValueAsString(
                                Map.of(
                                        "name", name,
                                        "email", email + "@example.com",
                                        "description", description)
        );

        this.mockMvc
                .perform(post(URI).contentType(MediaType.APPLICATION_JSON).content(payload))
                .andExpect(status().is2xxSuccessful());

        this.mockMvc
                .perform(post(URI).contentType(MediaType.APPLICATION_JSON).content(payload))
                .andExpect(status().is4xxClientError());
    }
}
