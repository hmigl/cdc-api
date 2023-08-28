package com.hmigl.cdc.country_state;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@SpringBootTest
@JqwikSpringSupport
@AutoConfigureMockMvc
class StateControllerTest {
    private static final String URI = "/api/v1/states";
    private static final Set<String> STATES = new HashSet<>();

    private @Autowired MockMvc mockMvc;
    private @Autowired Jackson2ObjectMapperBuilder mapperBuilder;

    @Property(tries = 10)
    @Label("should register a state and not let it be registered again")
    void test(@ForAll @AlphaChars @StringLength(min = 1, max = 255) String state) throws Exception {
        Assumptions.assumeTrue(STATES.add(state));

        this.mockMvc.perform(
                post("/api/v1/countries")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapperBuilder.build().writeValueAsString(Map.of("name", "test"))));

        this.mockMvc
                .perform(post(URI).contentType(MediaType.APPLICATION_JSON)
                        .content(
                                mapperBuilder.build().writeValueAsString(Map.of("name", state, "country", 1))))
                .andExpect(status().is2xxSuccessful());

        this.mockMvc
                .perform(post(URI).contentType(MediaType.APPLICATION_JSON)
                        .content(
                                mapperBuilder.build().writeValueAsString(Map.of("name", state, "country", 1))))
                .andExpect(status().is4xxClientError());
    }
}
