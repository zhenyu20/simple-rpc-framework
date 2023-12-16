package com.zy.rpc.core.codec;

import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * 粘包拆包编码器，使用固定长度的帧解码器，通过约定用定长字节表示接下来数据的长度。
 *
 * @author zy
 * @version 1.0
 */
public class RpcFrameDecoder extends LengthFieldBasedFrameDecoder {

    /**
     * 得到当前约定协议的帧解码器
     */
    public RpcFrameDecoder() {
        this(1024, 12, 4);
    }

    /**
     * 构造方法
     *
     * @param maxFrameLength    数据帧的最大长度
     * @param lengthFieldOffset 长度域的偏移字节数
     * @param lengthFieldLength 长度域所占的字节数
     */
    public RpcFrameDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength) {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength);
    }

}
