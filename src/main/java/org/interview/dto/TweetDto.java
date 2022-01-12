package org.interview.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigInteger;
import java.util.Date;

@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class TweetDto implements Comparable<TweetDto> {

    private BigInteger id;
    private Date creationDate;
    private String messageText;

    @Override
    public int compareTo(TweetDto tweet) {
        return Long.compare(creationDate.getTime(), tweet.getCreationDate().getTime());
    }
}
