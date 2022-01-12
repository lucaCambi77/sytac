package org.interview.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.LowLevelHttpRequest;
import com.google.api.client.http.LowLevelHttpResponse;
import com.google.api.client.json.Json;
import com.google.api.client.testing.http.MockHttpTransport;
import com.google.api.client.testing.http.MockLowLevelHttpRequest;
import com.google.api.client.testing.http.MockLowLevelHttpResponse;
import org.interview.domain.Tweet;
import org.interview.utils.TweetObjectMapper;

import java.util.Set;

public class FakeRequest {

    public static HttpRequestFactory getFakeHttpRequestFactory(Set<Tweet> tweets) {

        ObjectMapper objectMapper = TweetObjectMapper.getObjectMapper();

        return new MockHttpTransport() {
            @Override
            public LowLevelHttpRequest buildRequest(String method, String url) {
                return new MockLowLevelHttpRequest() {

                    @Override
                    public LowLevelHttpResponse execute() throws JsonProcessingException {

                        StringBuilder sb = new StringBuilder();

                        for (Tweet tweet : tweets) {
                            sb.append(objectMapper.writeValueAsString(tweet)).append("\n");
                        }

                        MockLowLevelHttpResponse response = new MockLowLevelHttpResponse();
                        response.setStatusCode(200);
                        response.setContentType(Json.MEDIA_TYPE);
                        response.setContent(sb.toString());
                        return response;

                    }
                };
            }
        }.createRequestFactory();
    }
}
