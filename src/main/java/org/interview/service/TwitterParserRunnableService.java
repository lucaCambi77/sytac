/**
 *
 */
package org.interview.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.http.GenericUrl;
import org.interview.constant.Constant;
import org.interview.domain.Tweet;
import org.interview.oauth.twitter.TwitterAuthenticator;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

public class TwitterParserRunnableService extends Constant implements Runnable {

    private final TwitterAuthenticator authenticator;

    private final ObjectMapper objectMapper;

    private Set<Tweet> tweets;

    private String query;

    private static int MAX_NUM_TWEETS = 100;

    public TwitterParserRunnableService(TwitterAuthenticator twitterAuthenticator, ObjectMapper objectMapper) {
        this.authenticator = twitterAuthenticator;
        this.objectMapper = objectMapper;
    }

    @Override
    public void run() {
        tweets = new HashSet<>();

        try {
            InputStream in = authenticator.getAuthorizedHttpRequestFactory()
                    .buildGetRequest(new GenericUrl(DEFAULT_API.concat("?track=").concat(this.query))).execute().getContent();

            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line = reader.readLine();

            int countTweets = 0;

            /**
             * We wait for X seconds or up to Y messages
             */
            while (line != null && !Thread.interrupted()) {

                if (countTweets == MAX_NUM_TWEETS)
                    break;

                tweets.add(objectMapper.readValue(line, Tweet.class));

                line = reader.readLine();
                ++countTweets;
            }

        } catch (Exception e) {
            System.out.println("Twitter parsing error : " + e.getMessage());
        }
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public Set<Tweet> getTweets() {
        return tweets;
    }
}
