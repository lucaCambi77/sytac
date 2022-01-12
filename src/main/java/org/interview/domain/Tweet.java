package org.interview.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigInteger;
import java.util.Date;
import java.util.Optional;

@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Tweet {

    @JsonProperty("id")
    private BigInteger id;
    @JsonProperty("created_at")
    private Date creationDate;
    @JsonProperty("text")
    private String messageText;
    @JsonProperty("user")
    private TweetAuthor tweetAuthor;

    public TweetAuthor getTweetAuthor() {
        return Optional.ofNullable(tweetAuthor)
                .orElse(TweetAuthor.builder().id(new BigInteger("0")).userName("N.A").userScreenName("N.A").build());
    }
}
