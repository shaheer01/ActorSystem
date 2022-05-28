package com.actor.wordcount;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.BiConsumer;

public class ActorSystem {

    private static final int noOfThreads = 30;

    private final static ExecutorService executorService = Executors.newFixedThreadPool(noOfThreads);

    public static <T> CustomActor<T> create(BiConsumer<CustomActor<T>, T> behaviourHandler,
                                           BiConsumer<CustomActor<T>, Throwable> errorHandler) {

        CustomActor<T> actor = CustomActor.create(behaviourHandler, errorHandler);
        executorService.submit(actor);
        return actor;
    }

    public static <T> SentenceSplitterCustomActor<T> createPong(BiConsumer<SentenceSplitterCustomActor<T>, T> behaviourHandler,
                                            BiConsumer<SentenceSplitterCustomActor<T>, Throwable> errorHandler) {

        SentenceSplitterCustomActor<T> actor = SentenceSplitterCustomActor.create(behaviourHandler, errorHandler);
        executorService.submit(actor);
        return actor;
    }

    public static void shutdown() {
        executorService.shutdown();
    }
}
