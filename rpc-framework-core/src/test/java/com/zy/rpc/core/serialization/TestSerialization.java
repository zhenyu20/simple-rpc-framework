package com.zy.rpc.core.serialization;

import com.zy.rpc.core.codec.RpcFrameDecoder;
import com.zy.rpc.core.codec.SharableRpcMessageCodec;
import com.zy.rpc.core.message.RpcRequest;
import com.zy.rpc.core.enums.SerializationType;
import com.zy.rpc.core.protocol.MessageHeader;
import com.zy.rpc.core.protocol.RpcMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * @author zy
 * @version 1.0
 */
public class TestSerialization {

    public static void main(String[] args) {
        LoggingHandler LOGGING = new LoggingHandler(LogLevel.DEBUG);
        EmbeddedChannel embeddedChannel = new EmbeddedChannel(LOGGING, new RpcFrameDecoder(), new SharableRpcMessageCodec(), LOGGING);

        RpcRequest request = new RpcRequest();
        request.setServiceName("com.zy.rpc.api.service.HelloService");
        request.setMethod("sayHello");
        request.setParameterTypes(new Class[]{String.class});
        request.setParameterValues(new Object[]{"zhangsan"});

        RpcMessage protocol = new RpcMessage();
        MessageHeader header = MessageHeader.build("JDK");
        protocol.setHeader(header);
        protocol.setBody(request);

        embeddedChannel.writeOutbound(protocol); // encode

        ByteBuf buf = embeddedChannel.alloc().buffer();
        buf.writeBytes(header.getMagicNum());
        buf.writeByte(header.getVersion());
        buf.writeByte(header.getSerializerType());
        buf.writeByte(header.getMessageType());
        buf.writeByte(header.getMessageStatus());
        buf.writeInt(header.getSequenceId());
        Serialization serialization = SerializationFactory
                .getSerialization(SerializationType.parseByType(header.getSerializerType()));
        byte[] bytes = serialization.serialize(request);
        buf.writeInt(bytes.length);
        buf.writeBytes(bytes);

        embeddedChannel.writeInbound(buf); // decode
    }

}
