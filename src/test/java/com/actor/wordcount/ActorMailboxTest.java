package com.actor.wordcount;

import org.junit.Before;
import org.junit.Test;

import java.util.function.BiConsumer;

import static org.mockito.Mockito.*;

public class ActorMailboxTest {

    private static final long TEST_TIMEOUT = 500L;

    private BiConsumer<ActorMailbox<String>,String> behaviorHandler = mock(BiConsumer.class);
    private BiConsumer<ActorMailbox<String>,Throwable> errorHandler = mock(BiConsumer.class);
    private ActorMailbox<String> actor;

    @Before
    public void setUp() {
        actor.create(behaviorHandler, errorHandler);
    }

    @Test
    public void testAcceptMessage(){
        actor.send("Hello");
        verify(behaviorHandler, times(1).description("Hello"));
    }
}
