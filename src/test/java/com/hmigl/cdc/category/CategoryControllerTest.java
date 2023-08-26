package com.hmigl.cdc.category;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;

import net.jqwik.api.ForAll;
import net.jqwik.api.Label;
import net.jqwik.api.Property;
import net.jqwik.api.constraints.AlphaChars;
import net.jqwik.api.constraints.StringLength;
import net.jqwik.spring.JqwikSpringSupport;

import org.junit.jupiter.api.Assumptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@SpringBootTest
@AutoConfigureMockMvc
@JqwikSpringSupport
class CategoryControllerTest {
    private static final String URI = "/api/v1/categories";

    private static Set<String> names = new HashSet<>();
    private @Autowired MockMvc mockMvc;

    @Property(tries = 10)
    @Label("should register a new category and not let it be registered again")
    void test(@ForAll @AlphaChars @StringLength(min = 1, max = 255) String name) throws Exception {
        Assumptions.assumeTrue(names.add(name));

        String payload = new ObjectMapper().writeValueAsString(Map.of("name", name));

        this.mockMvc
                .perform(post(URI).contentType(MediaType.APPLICATION_JSON).content(payload))
                .andExpect(status().is2xxSuccessful());

        this.mockMvc
                .perform(post(URI).contentType(MediaType.APPLICATION_JSON).content(payload))
                .andExpect(status().is4xxClientError());
    }
}
