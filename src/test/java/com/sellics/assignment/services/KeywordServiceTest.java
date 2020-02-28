package com.sellics.assignment.services;

import com.sellics.assignment.clients.AmazonAutocompleteClient;
import com.sellics.assignment.dto.KeywordDetailDTO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
public class KeywordServiceTest {

    private KeywordService keywordService;

    @MockBean
    private AmazonAutocompleteClient amazonAutocompleteClient;

    @Before
    public void setUp() {
        this.keywordService = new KeywordService(amazonAutocompleteClient);
    }

    @Test
    public void when_keywordIsGiven_then_keywordScoreIsReturned() {

        given(amazonAutocompleteClient.searchByKeyword("l")).willReturn(getSuggestionsMock());

        KeywordDetailDTO actualKeywordDetail = keywordService.getKeywordSearchVolume("linux");

        assertThat(actualKeywordDetail).isNotNull();
        assertThat(actualKeywordDetail.getKeyword()).isEqualTo("linux");
        assertThat(actualKeywordDetail.getScore()).isEqualTo(100);
    }

    private ArrayList<String> getSuggestionsMock() {
        return new ArrayList<String>() {{
            add("linux");
            add("linux pocket guide");
            add("linux bible");
            add("linux command line");
            add("linux command line and shell scripting bible");
            add("linux sticker");
            add("linux for dummies");
            add("linux shirt");
            add("linux wifi adapter");
            add("linux essentials");
        }};
    }

}
