package com.actor.wordcount;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SentenceSplitterCustomActor<T> implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(SentenceSplitterCustomActor.class);

    private final ConcurrentLinkedQueue<T> mailbox;
    private final BiConsumer<SentenceSplitterCustomActor<T>, T> actionHandler;
    private final BiConsumer<SentenceSplitterCustomActor<T>, Throwable> errorHandler;

    private SentenceSplitterCustomActor(BiConsumer<SentenceSplitterCustomActor<T>, T> behaviourHandler,
                        BiConsumer<SentenceSplitterCustomActor<T>, Throwable> errorHandler) {
        this.mailbox = new ConcurrentLinkedQueue<>();
        this.actionHandler = behaviourHandler;
        this.errorHandler = errorHandler;
    }

    static <T> SentenceSplitterCustomActor<T> create(BiConsumer<SentenceSplitterCustomActor<T>, T> behaviourHandler,
                                     BiConsumer<SentenceSplitterCustomActor<T>, Throwable> errorHandler) {
        return new SentenceSplitterCustomActor<>(behaviourHandler, errorHandler);
    }

    public void send(T message) {
        mailbox.offer(message);
    }

    @Override
    public void run() {
        try {
            while(true) {
                T message = mailbox.poll();
                if(message != null) {
                    actionHandler.accept(this, message);
                    log.info("Received Message: " + message);
                    log.info("Running in thread: " + Thread.currentThread().getName());

                    List<String> list = Stream.of(message.toString()).map(k -> k.split("\\.")).flatMap(Arrays::stream)
                            .collect(Collectors.toList());

                    log.info("Output:" + list);
                    // replies back to sender
                }
            }
        } catch(Exception e) {
            errorHandler.accept(this, e);
        }
    }
}
