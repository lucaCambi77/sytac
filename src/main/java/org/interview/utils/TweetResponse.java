/**
 *
 */
package org.interview.utils;

import org.interview.domain.Tweet;
import org.interview.dto.AuthorToTweetDto;
import org.interview.dto.TweetDto;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Map.Entry.comparingByKey;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toCollection;

/**
 * @author luca
 *
 */
public class TweetResponse {

    /**
     * Group input tweets list by author and sort by author creation date first and then by tweet creation date
     * @param tweets
     * @return
     */
    public static List<AuthorToTweetDto> tweetsToAuthorTweets(Set<Tweet> tweets) {

        return tweets.stream()
                .collect(groupingBy(Tweet::getTweetAuthor, LinkedHashMap::new, Collectors.toList())).entrySet()
                .stream()
                .sorted(comparingByKey()).map(e -> new AuthorToTweetDto(e.getKey(),
                        e.getValue().stream().map(tweet -> new TweetDto(tweet.getId(), tweet.getCreationDate(), tweet.getMessageText()))
                                .collect(Collectors.toList()).stream().sorted().collect(Collectors.toList())
                )).collect(toCollection(LinkedList::new));
    }

}
