package com.azy.server.handler;

import com.azy.message.ChatRequestMessage;
import com.azy.message.ChatResponseMessage;
import com.azy.server.session.SessionFactory;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 2023-11-29 15:10:17
 */
public class ChatRequestMessageHandler extends SimpleChannelInboundHandler<ChatRequestMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ChatRequestMessage msg) throws Exception {
        Channel channelTo = SessionFactory.getSession().getChannel(msg.getTo());
        if(channelTo != null){
            channelTo.writeAndFlush(new ChatResponseMessage(msg.getFrom(),msg.getContent()));
        }else{
            ctx.channel().writeAndFlush(new ChatResponseMessage(false,"对方不存在或者不在线"));
        }

    }
}
