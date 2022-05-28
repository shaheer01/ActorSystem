//package com.actor.wordcount;
//
//
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.Map;
//import java.util.concurrent.ConcurrentLinkedQueue;
//import java.util.function.BiConsumer;
//import java.util.stream.Collectors;
//import java.util.stream.Stream;
//
//public class ActorWordCount<T> implements Runnable {
//
//    private static Logger log = LoggerFactory.getLogger(ActorWordCount.class);
//
//    private final ConcurrentLinkedQueue<T> mailbox;
//    private final BiConsumer<ActorWordCount<T>, T> actionHandler;
//    private final BiConsumer<ActorWordCount<T>, Throwable> errorHandler;
//
//    private CustomActor(BiConsumer<ActorWordCount<T>, T> behaviourHandler,
//                        BiConsumer<ActorWordCount<T>, Throwable> errorHandler) {
//        this.mailbox = new ConcurrentLinkedQueue<>();
//        this.actionHandler = behaviourHandler;
//        this.errorHandler = errorHandler;
//    }
//
//    public ActorWordCount() {
//    }
//
//    static <T> ActorWordCount<T> create(BiConsumer<ActorWordCount<T>, T> behaviourHandler,
//                                     BiConsumer<ActorWordCount<T>, Throwable> errorHandler) {
//        return new ActorWordCount<>(behaviourHandler, errorHandler);
//    }
//
//    public void send(T message) {
//        mailbox.offer(message);
//    }
//
//    @Override
//    public void run() {
//        try {
//            while(true) {
//                T message = mailbox.poll();
//                if(message != null) {
//                    actionHandler.accept(this, message);
//                    List<String> list = Stream.of(message.toString()).map(k -> k.split("\\W+")).flatMap(Arrays::stream)
//                            .collect(Collectors.toList());
//
//                    Map<String, Integer> countMap = list.stream()
//                            .collect(Collectors.toMap(k -> k.toLowerCase(), k -> 1, Integer::sum));
//
//                    log.info("Count:" + countMap);
//                }
//            }
//        } catch(Exception e) {
//            errorHandler.accept(this, e);
//        }
//    }
//}
