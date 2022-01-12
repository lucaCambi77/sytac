package org.interview.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.interview.constant.Constant;
import org.interview.domain.Tweet;
import org.interview.oauth.twitter.TwitterAuthenticationException;
import org.interview.oauth.twitter.TwitterAuthenticator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.interview.utils.FakeRequest;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TwitterParserRunnableTest extends Constant {

    @InjectMocks
    private TwitterParserRunnableService twitterParserRunnableService;

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private TwitterAuthenticator twitterAuthenticator;

    @BeforeEach
    public void setUp() throws TwitterAuthenticationException {
        this.twitterParserRunnableService = new TwitterParserRunnableService(twitterAuthenticator, objectMapper);

        ReflectionTestUtils.setField(twitterParserRunnableService, "query", "?query");
        ReflectionTestUtils.setField(twitterParserRunnableService, "MAX_NUM_TWEETS", 10000);
        when(twitterAuthenticator.getAuthorizedHttpRequestFactory())
                .thenReturn(FakeRequest.getFakeHttpRequestFactory(Set.of(Tweet.builder().id(new BigInteger("1")).build())));
    }

    @Test
    public void shouldGetTweets() throws IOException {

        when(objectMapper.readValue(anyString(), eq(Tweet.class))).thenReturn(Tweet.builder().id(new BigInteger("1")).build());

        twitterParserRunnableService.run();

        assertEquals(1, twitterParserRunnableService.getTweets().size());
    }

    @Test
    public void shouldExitWhenMaxNumReached() throws TwitterAuthenticationException {

        when(twitterAuthenticator.getAuthorizedHttpRequestFactory())
                .thenReturn(FakeRequest.getFakeHttpRequestFactory(Set.of(Tweet.builder().id(new BigInteger("1")).build()
                        , Tweet.builder().id(new BigInteger("2")).build())));

        ReflectionTestUtils.setField(twitterParserRunnableService, "MAX_NUM_TWEETS", 1);

        twitterParserRunnableService.run();

        assertEquals(1, twitterParserRunnableService.getTweets().size());
    }

    @Test
    public void shouldThrowsWhenAuthorizationFails() throws TwitterAuthenticationException, IOException {

        when(twitterAuthenticator.getAuthorizedHttpRequestFactory())
                .thenThrow(new TwitterAuthenticationException("error"));

        twitterParserRunnableService.run();

        assertEquals(0, twitterParserRunnableService.getTweets().size());
    }

    @Test
    public void shouldThrowsWhenParseFails() throws IOException {

        when(objectMapper.readValue(anyString(), eq(Tweet.class))).thenThrow(new RuntimeException("error"));

        twitterParserRunnableService.run();

        assertEquals(0, twitterParserRunnableService.getTweets().size());
    }
}
