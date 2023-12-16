package com.zy.rpc.client;

import com.zy.rpc.client.handler.RpcResponseHandler;
import com.zy.rpc.core.codec.RpcFrameDecoder;
import com.zy.rpc.core.codec.SharableRpcMessageCodec;
import com.zy.rpc.core.enums.MessageStatus;
import com.zy.rpc.core.protocol.MessageHeader;
import com.zy.rpc.core.protocol.RpcMessage;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

/***
 * @author zy
 * @date 2023年12月04日 13:57
 */
@Slf4j
public class NettyRpcClient {
    public static void main(String[] args) {
        NioEventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.group(group);
            bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new RpcFrameDecoder());
                    ch.pipeline().addLast(new LoggingHandler(LogLevel.DEBUG));
                    ch.pipeline().addLast(new SharableRpcMessageCodec());
                    ch.pipeline().addLast(new RpcResponseHandler());
                }
            });
            Channel channel = bootstrap.connect("localhost", 8888).sync().channel();


            MessageHeader messageHeader = new MessageHeader();
            messageHeader.setMagicNum(new byte[]{'a','r','p','c'});
            messageHeader.setVersion((byte) 1);
            messageHeader.setSerializerType((byte) 0);
            messageHeader.setMessageType((byte) 3);
            messageHeader.setMessageStatus(MessageStatus.SUCCESS.getCode());
            messageHeader.setSequenceId(1);
            messageHeader.setLength("sayHello".length());
            RpcMessage rpcMessage = new RpcMessage();
            rpcMessage.setHeader(messageHeader);
            rpcMessage.setBody("sayHello");
            channel.writeAndFlush(rpcMessage
            ).addListener(promise -> {
                if (!promise.isSuccess()) {
                    Throwable cause = promise.cause();
                    log.error("error", cause);
                }else {
                    log.error("success");
                }
            });
            channel.closeFuture().sync();
        } catch (Exception e) {
            log.error("client error", e);
        } finally {
            group.shutdownGracefully();
        }
    }
}
