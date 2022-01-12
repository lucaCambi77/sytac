package org.interview.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.interview.dto.AuthorToTweetDto;
import org.interview.utils.TweetObjectMapper;
import org.interview.utils.TweetResponse;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ModelTest {

    private final ObjectMapper objectMapper = TweetObjectMapper.getObjectMapper();

    @Test
    public void shouldFindAllTweetProperties() throws IOException {

        Tweet tweet = objectMapper
                .readValue(new File("src/test/resources/tweets.json"), Tweet.class);

        assertThat(tweet).hasNoNullFieldsOrProperties();
    }

    @Test
    public void shouldGroupTweetsByUserAndSort() {

        Date messageCreation = new Date();
        Date userCreation = new Date();

        // given 2 Authors
        TweetAuthor newerAuthor = TweetAuthor.builder()
                .userName("Name")
                .userScreenName("ScreenName")
                .id(new BigInteger("1"))
                .creationDate(userCreation)
                .build();

        TweetAuthor olderAuthor = TweetAuthor.builder()
                .userName("Name1")
                .userScreenName("ScreenName1")
                .id(new BigInteger("2"))
                .creationDate(new Date(userCreation.getTime() - 1000))
                .build();

        // when 3 tweets are created and 2 belongs to author1 and 1 to author2

        Tweet oldestTweet = Tweet.builder()
                .creationDate(new Date(messageCreation.getTime() - 2000))
                .id(new BigInteger("3"))
                .messageText("Message3")
                .tweetAuthor(newerAuthor)
                .build();

        Tweet middleTweet = Tweet.builder()
                .creationDate(messageCreation)
                .id(new BigInteger("1"))
                .messageText("Message1")
                .tweetAuthor(newerAuthor)
                .build();

        Tweet newestTweet = Tweet.builder()
                .creationDate(new Date(messageCreation.getTime() + 1000))
                .id(new BigInteger("2"))
                .messageText("Message2")
                .tweetAuthor(olderAuthor)
                .build();

        List<AuthorToTweetDto> authorToTweetDtos = TweetResponse.tweetsToAuthorTweets(Set.of(middleTweet, newestTweet, oldestTweet));

        // assert tweets are grouped by user and sorted by tweet creation date asc
        // assert author 1 has 2 tweets, and they are sorted by creation date asc
        assertEquals(2, authorToTweetDtos.size());
        assertEquals(olderAuthor, authorToTweetDtos.get(0).getTweetAuthor());
        assertEquals(newerAuthor, authorToTweetDtos.get(1).getTweetAuthor());
        assertEquals(oldestTweet.getId(), authorToTweetDtos.get(1).getTweetList().get(0).getId());
    }
}
