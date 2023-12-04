package com.azy.client;

/***
 * @author zy
 * @date 2023年12月04日 16:21
 */
import java.util.concurrent.atomic.AtomicInteger;

public abstract class SequenceIdGenerator {
    private static final AtomicInteger id = new AtomicInteger();

    public static int nextId() {
        return id.incrementAndGet();
    }
}
