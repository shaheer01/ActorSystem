package com.actor.wordcount;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class DefaultThreadFactory {

    private static CustomActor<String> pingActor;

    public static void main(String[] args)
    {

        for (int i = 1; i < 10; i++) {

            // Default ThreadFactory
            ThreadFactory threadFactory
                    = Executors.defaultThreadFactory();

            // Creating new threads with the default
            // ThreadFactory
            Thread thread
                    = threadFactory.newThread(pingActor);
            pingActor = ActorSystem.create((actor, message) -> {
                        if("PING".equals(message)) {
                            pingActor.send("Java is a language.Java is easy and i like Java.This is new line");
                        }
                        else if("STOP".equals(message)) {
                            System.exit(0);
                        }
                        System.out.println(message);
                    }, (actor, exception) -> System.out.println(exception)
            );
            // print the thread name
            System.out.println(
                    "Name given by threadFactory = "
                            + thread.getName());

            // start the thread
            thread.start();
        }
    }
}
