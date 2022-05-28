package com.actor.wordcount;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class CustomActorRunner {

    private static CustomActor<String> pingActor;
    private static SentenceSplitterCustomActor<String> pongActor;
    private static final Logger log = LoggerFactory.getLogger(CustomActorRunner.class);

    public static void main(String[] args) throws InterruptedException {
        for (int i = 1; i < 2; i++) {
            // Default ThreadFactory
            ThreadFactory threadFactory
                    = Executors.defaultThreadFactory();

            // Creating new threads with the default
            // ThreadFactory
            Thread thread
                    = threadFactory.newThread(pingActor);
            // print the thread name
            System.out.println(
                    "Name given by threadFactory = "
                            + thread.getName());

            // start the thread
//        thread.start();
            pingActor = ActorSystem.create((actor, message) -> {
                        if ("PING".equals(message)) {
                            pongActor.send("Java is a language.Java is easy and i like Java.This is new line");
                        } else if ("STOP".equals(message)) {
                            System.exit(0);
                        }
                        System.out.println(message);
                    }, (actor, exception) -> System.out.println(exception)
            );

            pongActor = ActorSystem.createPong((actor, message) -> {
                        if ("PONG".equals(message)) {
                            //Problem: Here the input to this shud not be this line but output of :
                            // pongActor.send("Java is a language.Java is easy and i like Java.This is new line");
                            //Output:[Java is a language, Java is easy and i like Java, This is new line] i.e this
                            // shud be the input to the below send function.
                            //But dunno how to pass that coz run() return is void.
                            pingActor.send("Java is a language.Java is easy and i like Java.This is new line");
                        }
                        System.out.println(message);
                    }, (actor, exception) -> System.out.println(exception)
            );

            pingActor.send("PING");
            pongActor.send("PONG");
            Thread.sleep(10);
            pingActor.send("STOP");
            ActorSystem.shutdown();

        }
    }
}
