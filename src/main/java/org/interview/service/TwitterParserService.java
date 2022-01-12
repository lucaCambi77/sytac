package org.interview.service;

import lombok.RequiredArgsConstructor;
import org.interview.domain.Tweet;

import java.util.Set;

@RequiredArgsConstructor
public class TwitterParserService {

    private final TwitterParserExecutorService twitterParserExecutionService;

    public Set<Tweet> parseTweetsFrom(String query) {

        try {
            return twitterParserExecutionService.getTweetsFrom(query);
        } catch (Exception e) {
            System.out.println("Error : " + e.getMessage());
        }

        return null;
    }

}
