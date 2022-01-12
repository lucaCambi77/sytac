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

@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TweetAuthor implements Comparable<TweetAuthor> {

    @JsonProperty("id")
    private BigInteger id;
    @JsonProperty("created_at")
    private Date creationDate;
    @JsonProperty("name")
    private String userName;
    @JsonProperty("screen_name")
    private String userScreenName;


    @Override
    public int compareTo(TweetAuthor tweetAuthor) {
        return Long.compare(creationDate.getTime(), tweetAuthor.getCreationDate().getTime());
    }
}
