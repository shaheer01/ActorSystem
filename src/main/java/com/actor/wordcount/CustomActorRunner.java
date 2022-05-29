package com.actor.wordcount;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CustomActorRunner {

    private static CustomActor<String> layer1Actor;
    private static CustomActor<String> layer2Actor;
    private static CustomActor<List<String>> layer3Actor;
    private static final Logger log = LoggerFactory.getLogger(CustomActorRunner.class);

    public static void main(String[] args) throws InterruptedException {
        for (int i = 1; i < 2; i++) {
            // Default ThreadFactory
            ThreadFactory threadFactory
                    = Executors.defaultThreadFactory();

            // Creating new threads with the default
            // ThreadFactory
            Thread thread
                    = threadFactory.newThread(layer1Actor);
            // print the thread name
            log.info(
                    "Name given by threadFactory = "
                            + thread.getName());

            // start the thread
        thread.start();
            layer1Actor = ActorSystem.create((actor, message) -> {
                        List<String> list = Stream.of(message).map(k -> k.split("\\.")).flatMap(Arrays::stream).collect(Collectors.toList());
                        log.info("Output layer1Actor:" + list);
                        //Spawning multiple actors for layer2Actor, layer1Actor.
                        list.forEach(k -> layer2Actor.send(k));
                    }, (actor, exception) -> log.error(String.valueOf(exception))
            );


            layer2Actor = ActorSystem.create((actor, message) -> {
                List<String> list =
                        Stream.of(message).map(k -> k.split("\\W+")).flatMap(Arrays::stream).collect(Collectors.toList());
                log.info("Output layer2Actor:" + list);
                layer3Actor.send(list);
                    }, (actor, exception) -> log.error(String.valueOf(exception))
            );

            layer3Actor = ActorSystem.create((actor, message) -> {
                  Map<String, Integer> countMap = message.stream().collect(Collectors.toMap(k -> k.toLowerCase(), k -> 1,
                          Integer::sum));
                  log.info("Output layer1Actor:" +countMap);
                countMap.forEach((key, value) -> log.info(key + " " + value));
                    },  (actor, exception) -> log.error(String.valueOf(exception))
            );

            layer1Actor.send("Java is a language.Java is easy and i like Java.This is new line");

            ActorSystem.shutdown();

        }
    }
}