package com.actor.wordcount;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.BiConsumer;

public class ActorMailbox<T> implements Runnable {

    private final Queue<T> mailbox;
    private final BiConsumer<ActorMailbox<T>, T> actionHandler;
    private final BiConsumer<ActorMailbox<T>, Throwable> errorHandler;

    private ActorMailbox(BiConsumer<ActorMailbox<T>, T> behaviorHandler,
                         BiConsumer<ActorMailbox<T>, Throwable> errorHandler){
        this.mailbox = new ConcurrentLinkedQueue<>();
        this.actionHandler = behaviorHandler;
        this.errorHandler = errorHandler;
    }

    static <T> ActorMailbox<T> create(BiConsumer<ActorMailbox<T>, T> behaviorHandler,
                                      BiConsumer<ActorMailbox<T>, Throwable> errorHandler){
        return new ActorMailbox<>(behaviorHandler, errorHandler);
    }

    public void send(T message){
        mailbox.offer(message);
    }

    @Override
    public void run() {
        do {
            T message = mailbox.poll();
            if (message != null) {
                try {
                    //(actor itself, message)
                    actionHandler.accept(this, message);
                } catch (Exception e) {
                    errorHandler.accept(this, e);
                }
            }
        } while (true);
    }
}
