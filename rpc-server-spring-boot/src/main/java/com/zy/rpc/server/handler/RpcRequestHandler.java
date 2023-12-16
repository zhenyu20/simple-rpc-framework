package com.zy.rpc.server.handler;

import com.zy.rpc.core.message.RpcResponse;
import com.zy.rpc.core.protocol.RpcMessage;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/***
 * @author zy
 * @date 2023年12月04日 13:55
 */
@Slf4j
@ChannelHandler.Sharable
public class RpcRequestHandler extends SimpleChannelInboundHandler<RpcMessage> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcMessage message) {
        RpcResponse response = new RpcResponse();
        log.debug("{}", "test......");
        try {

        } catch (Exception e) {
            // 调用异常
            response.setExceptionValue(e);
        }
        // 返回结果
        ctx.writeAndFlush(response);
    }
}

