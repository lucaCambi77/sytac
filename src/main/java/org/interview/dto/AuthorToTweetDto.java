package org.interview.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.interview.domain.TweetAuthor;

import java.util.List;

@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class AuthorToTweetDto {

    private TweetAuthor tweetAuthor;
    private List<TweetDto> tweetList;
}
