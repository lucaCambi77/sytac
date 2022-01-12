package org.interview.service;

import org.interview.domain.Tweet;
import org.interview.domain.TweetAuthor;
import org.interview.service.TwitterParserExecutorService;
import org.interview.service.TwitterParserRunnableService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigInteger;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TwitterParserExecutorTest {

    private TwitterParserExecutorService twitterParserExecutionService;

    @Mock
    private TwitterParserRunnableService twitterParserRunnableService;

    private static final Tweet tweet =
            Tweet.builder()
                    .creationDate(new Date())
                    .id(new BigInteger("1"))
                    .messageText("Message1")
                    .tweetAuthor(TweetAuthor.builder()
                            .userName("Name")
                            .userScreenName("ScreenName")
                            .id(new BigInteger("1")).build())
                    .build();

    @BeforeEach
    public void setUp() {
        twitterParserExecutionService = new TwitterParserExecutorService(twitterParserRunnableService);
    }

    @Test
    public void shouldParseTweets() throws Exception {

        when(twitterParserRunnableService.getTweets()).thenReturn(Set.of(tweet));

        twitterParserExecutionService.getTweetsFrom("?query");

        assertEquals(1, twitterParserRunnableService.getTweets().size());
        assertEquals(tweet, twitterParserRunnableService.getTweets().iterator().next());

        reset();

        when(twitterParserRunnableService.getTweets()).thenReturn(new HashSet<>());

        twitterParserExecutionService.getTweetsFrom("?query");

        assertEquals(0, twitterParserRunnableService.getTweets().size());
    }
}
