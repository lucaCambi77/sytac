import com.fasterxml.jackson.databind.ObjectMapper;
import org.interview.domain.Tweet;
import org.interview.oauth.twitter.TwitterAuthenticator;
import org.interview.service.TwitterParserExecutorService;
import org.interview.service.TwitterParserRunnableService;
import org.interview.utils.TweetObjectMapper;
import org.interview.utils.TweetResponse;

import java.io.FileWriter;
import java.io.PrintStream;
import java.util.Date;
import java.util.Set;

import static org.interview.constant.Constant.CONSUMER_KEY;
import static org.interview.constant.Constant.CONSUMER_SECRET;

public class TweetApplication {

    private static String output = "/tmp/output.txt";
    private static String query = "bieber";
    private static String statistics = "/tmp/statistics.txt";

    public static void main(String[] args) {

        ObjectMapper objectMapper = TweetObjectMapper.getObjectMapper();

        TwitterParserRunnableService twitterParserRunnableService = new TwitterParserRunnableService(
                new TwitterAuthenticator(new PrintStream(System.out), CONSUMER_KEY, CONSUMER_SECRET), objectMapper);

        TwitterParserExecutorService twitterParserExecutorService = new TwitterParserExecutorService(twitterParserRunnableService);

        try {
            long startTime = System.currentTimeMillis();

            Set<Tweet> tweetSet = twitterParserExecutorService.getTweetsFrom(query);
            FileWriter outputWriter = new FileWriter(output, true);

            if (!tweetSet.isEmpty()) {
                outputWriter.write(objectMapper.writerWithDefaultPrettyPrinter()
                        .writeValueAsString(TweetResponse.tweetsToAuthorTweets(tweetSet)));
            }

            outputWriter.close();

            long estimatedTime = System.currentTimeMillis() - startTime;

            FileWriter statisticsWriter = new FileWriter(statistics, true);

            statisticsWriter.write("Execution of " + new Date(startTime) + " found tweets " + tweetSet.size() + " in " + (estimatedTime / 1000) + " seconds \n");

            statisticsWriter.close();

        } catch (Exception e) {
            System.out.println("Tweets parse exception: " + e.getMessage());
        }

    }

}
