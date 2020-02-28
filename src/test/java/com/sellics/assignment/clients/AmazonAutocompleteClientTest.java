package com.sellics.assignment.clients;

import com.sellics.assignment.exceptions.BusinessException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RunWith(SpringRunner.class)
@RestClientTest(AmazonAutocompleteClient.class)
public class AmazonAutocompleteClientTest {

    @Autowired
    private AmazonAutocompleteClient client;

    @Autowired
    private RestTemplate restTemplate;

    private MockRestServiceServer server;

    @Before
    public void setUp() {
        server= MockRestServiceServer.createServer(restTemplate);
    }

    @Test
    public void when_callingSearchByKeyword_then_ClientMakesCorrectCall() {

        String detailsString = "[\"linux\",[\"linux\",\"linux pocket guide\",\"linux bible\",\"linux command line\",\"linux command line and shell scripting bible\",\"linux sticker\",\"linux for dummies\",\"linux shirt\",\"linux wifi adapter\",\"linux essentials\"],[{\"nodes\":[{\"name\":\"Books\",\"alias\":\"stripbooks\"}]},{},{},{},{},{},{},{},{},{}],[],\"1QGF4O8JQR6ZQ\"]";

        this.server.expect(requestTo("https://completion.amazon.com/search/complete?search-alias=aps&client=amazon-search-ui&mkt=1&q=linux"))
                .andRespond(withSuccess(detailsString, MediaType.APPLICATION_JSON));

        ArrayList<String> suggestions = this.client.searchByKeyword("linux");

        assertThat(suggestions).isNotNull();
        assertThat(suggestions).hasSize(10);
        assertThat(suggestions.get(0)).isEqualTo("linux");
    }

    @Test(expected = BusinessException.class)
    public void when_clientReturnIncorrectResponse_then_exceptionIsThrown() {

        this.server.expect(requestTo("https://completion.amazon.com/search/complete?search-alias=aps&client=amazon-search-ui&mkt=1&q=linux"))
                .andRespond(withSuccess("response with bad format{}", MediaType.APPLICATION_JSON));

        this.client.searchByKeyword("linux");

    }
}
