package com.sellics.assignment.controllers;

import com.sellics.assignment.dto.KeywordDetailDTO;
import com.sellics.assignment.services.KeywordService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.is;


@RunWith(SpringRunner.class)
@WebMvcTest(KeywordController.class)
public class KeywordControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private KeywordService keywordService;

    @Test
    public void when_keywordEndpointIsCalled_then_keywordDetailIsReturned() throws Exception {

        KeywordDetailDTO keywordDetail = KeywordDetailDTO.builder().keyword("linux").score(40).build();

        given(keywordService.getKeywordSearchVolume("linux")).willReturn(keywordDetail);

        mvc.perform(get("/search-volume/linux")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.keyword", is("linux")))
                .andExpect(jsonPath("$.score", is(40)));
    }

}
