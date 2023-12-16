package com.zy.rpc.server.transport.netty;

import com.zy.rpc.core.codec.RpcFrameDecoder;
import com.zy.rpc.core.codec.SharableRpcMessageCodec;
import com.zy.rpc.server.handler.RpcRequestHandler;
import com.zy.rpc.server.transport.RpcServer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

/**
 * 基于 Netty 实现的 RpcServer 服务类
 *
 * @author zy
 * @version 1.0
 */
@Slf4j
public class NettyRpcServer implements RpcServer {

    @SneakyThrows
    @Override
    public void start(Integer port) {
        // boss 处理 accept 事件
        EventLoopGroup boss = new NioEventLoopGroup();
        // worker 处理 read/write 事件
        EventLoopGroup worker = new NioEventLoopGroup();

        try {

            InetAddress inetAddress = InetAddress.getLocalHost();

            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(boss, worker)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.DEBUG))
                    // 当客户端第一次请求时才会进行初始化
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            // 30s内没有收到客户端的请求就关闭连接，会触发一个 IdleState#READER_IDLE 事件
                            ch.pipeline().addLast(new IdleStateHandler(30, 0, 0, TimeUnit.SECONDS));
                            ch.pipeline().addLast(new RpcFrameDecoder());
                            ch.pipeline().addLast(new SharableRpcMessageCodec());
                            ch.pipeline().addLast(new RpcRequestHandler());
                        }
                    });
            // 绑定端口，同步等待绑定成功
            ChannelFuture channelFuture = serverBootstrap.bind(inetAddress, port).sync();
            log.debug("Rpc server add {} started on the port {}.", inetAddress, port);
            // 等待服务端监听端口关闭
            channelFuture.channel().closeFuture().sync();
        } catch (UnknownHostException | InterruptedException e) {
            log.error("An error occurred while starting the rpc service.", e);
        } finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }
}
