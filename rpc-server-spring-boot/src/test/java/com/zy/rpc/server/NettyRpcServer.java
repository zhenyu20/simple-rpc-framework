package com.zy.rpc.server;

import com.zy.rpc.core.codec.RpcFrameDecoder;
import com.zy.rpc.core.codec.SharableRpcMessageCodec;
import com.zy.rpc.server.handler.RpcRequestHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * 基于 Netty 实现的 RpcServer 服务类
 *
 * @author zy
 * @version 1.0
 */
@Slf4j
public class NettyRpcServer {

    public static void main(String[] args) {
        // boss 处理 accept 事件
        EventLoopGroup boss = new NioEventLoopGroup();
        // worker 处理 read/write 事件
        EventLoopGroup worker = new NioEventLoopGroup();

        try {

            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(boss, worker)
                    .channel(NioServerSocketChannel.class)
                    // 当客户端第一次请求时才会进行初始化
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            // 30s内没有收到客户端的请求就关闭连接，会触发一个 IdleState#READER_IDLE 事件
//                            ch.pipeline().addLast(new IdleStateHandler(30, 0, 0, TimeUnit.SECONDS));
                            ch.pipeline().addLast(new LoggingHandler(LogLevel.DEBUG));
                            ch.pipeline().addLast(new RpcFrameDecoder());
                            ch.pipeline().addLast(new SharableRpcMessageCodec());
                            ch.pipeline().addLast(new RpcRequestHandler());
                        }
                    });
            // 绑定端口，同步等待绑定成功
            ChannelFuture channelFuture = serverBootstrap.bind("localhost", 8888).sync();
            log.debug("Rpc server add {} started on the port {}.", "localhost", 8888);
            // 等待服务端监听端口关闭
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            log.error("An error occurred while starting the rpc service.", e);
        } finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }
}
