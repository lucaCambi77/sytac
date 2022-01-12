package org.interview.service;

import org.interview.domain.Tweet;

import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class TwitterParserExecutorService {

    private final TwitterParserRunnableService runnable;
    private static final long maxPollTime = 30;

    public TwitterParserExecutorService(TwitterParserRunnableService twitterParserRunnableService) {
        this.runnable = twitterParserRunnableService;
    }

    public Set<Tweet> getTweetsFrom(String query) throws ExecutionException, InterruptedException {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        runnable.setQuery(query);
        Future<?> future = executor.submit(runnable);

        try {
            future.get(maxPollTime, TimeUnit.SECONDS);
        } catch (TimeoutException e) {
            future.cancel(true);
        }
        executor.shutdownNow();

        return runnable.getTweets();
    }

}
