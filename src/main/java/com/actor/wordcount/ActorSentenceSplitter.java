package com.actor.wordcount;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ActorSentenceSplitter extends UntypedActor {

    private final LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    @Override
    public void onReceive(Object message) throws Throwable {
        log.info("Received Message: " + message);
        log.info("Running in thread: " + Thread.currentThread().getName());

        List<String> list = Stream.of(message.toString()).map(k -> k.split("\\.")).flatMap(Arrays::stream)
                .collect(Collectors.toList());

        log.info("Count:" + list);
        // replies back to sender
        getSender().tell(list, getSelf());
    }
}
