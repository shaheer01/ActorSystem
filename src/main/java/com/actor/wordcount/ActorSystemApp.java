package com.actor.wordcount;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

public class ActorSystemApp {

    public static void main(String[] args) {
        // Entry point
        ActorSystem actorSystem
                = ActorSystem.create();

        // Creates a SimpleActor and returns reference to it
        ActorRef simpleActor
                = actorSystem.actorOf(Props.create(SimpleActor.class), "simple-actor");

        // Sends a message to the actor
        simpleActor.tell("Hello", simpleActor);

    }
}

