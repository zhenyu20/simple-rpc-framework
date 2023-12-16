package com.zy.rpc.core.constant;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 协议常量类
 *
 * @author zy
 * @version 1.0
 */
public class ProtocolConstants {

    private static final AtomicInteger ai = new AtomicInteger();

    /**
     * 魔数，用来第一时间判断是否无效数据包
     */
    public static final byte[] MAGIC_NUM = new byte[]{(byte) 'a', (byte) 'r', (byte) 'p', (byte) 'c'};

    public static final byte VERSION = 1;

    public static final String PING = "ping";

    public static final String PONG = "pong";

    public static int getSequenceId() {
        return ai.getAndIncrement();
    }

}
