package com.azy.server.handler;

import com.azy.message.LoginRequestMessage;
import com.azy.message.LoginResponseMessage;
import com.azy.server.service.UserServiceFactory;
import com.azy.server.session.SessionFactory;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 2023-11-29 15:08:08
 */
public class LoginRequestMessageHandler extends SimpleChannelInboundHandler<LoginRequestMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequestMessage msg) throws Exception {
        String username = msg.getUsername();
        String password = msg.getPassword();
        boolean login = UserServiceFactory.getUserService().login(username, password);
        LoginResponseMessage message;
        if (login) {
            SessionFactory.getSession().bind(ctx.channel(), username);
            message = new LoginResponseMessage(true, "login success");
        } else {
            message = new LoginResponseMessage(false, "login failed");
        }
        ctx.writeAndFlush(message);
    }
}
