package com.sellics.assignment.clients;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sellics.assignment.exceptions.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class AmazonAutocompleteClient {

    private final RestTemplate restTemplate;

    private final String AMAZON_AUTOCOMPLETE_AJAX_API = "https://completion.amazon.com/search/complete?search-alias=aps&client=amazon-search-ui&mkt=1&q=%s";

    private final ObjectMapper objectMapper;

    /**
     * Search products by keyword
     * @return a list of search suggestions
     */
    public ArrayList<String> searchByKeyword(String keyword) {
        String url = buildUrl(keyword);
        String response = restTemplate.getForObject(url, String.class);

        ArrayList<String> results = parseResultOptions(response);

        return results;
    }

    /**
     * Parse Amazon autocomplete AJAX API response from string to array of strings with search suggestions
     * @param searchResult
     * @return
     */
    private ArrayList<String> parseResultOptions(String searchResult) {
        Object[] resultArray;

        try {
            resultArray = objectMapper.readValue(searchResult, Object[].class);
        } catch (IOException ex) {
            throw new BusinessException("Error parsing Amazon Autocomplete AJAX API response: " + searchResult);
        }

        return (ArrayList<String>) resultArray[1];
    }

    /**
     * Build URL to call Amazon Autocomplete AJAX API with the keyword to search
     * @param keyword
     * @return
     */
    private String buildUrl(String keyword) {
        return String.format(AMAZON_AUTOCOMPLETE_AJAX_API, keyword);
    }

}
