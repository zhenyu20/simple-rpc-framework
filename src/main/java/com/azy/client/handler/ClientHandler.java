package com.azy.client.handler;

import com.azy.message.*;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 2023-11-29 15:40:00
 */
public class ClientHandler extends ChannelInboundHandlerAdapter {
    private final CountDownLatch COUNT;
    private final AtomicBoolean LOGIN_STATUS;

    public ClientHandler(CountDownLatch downLatch, AtomicBoolean LOGIN_STATUS) {
        this.COUNT = downLatch;
        this.LOGIN_STATUS = LOGIN_STATUS;
    }

    //建立连接后
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        new Thread(() -> {
            Scanner sc = new Scanner(System.in);
            System.out.println("enter your username:");
            String username = sc.nextLine();//应该校验
            System.out.println("enter your password:");
            String password = sc.nextLine();//应该校验
            LoginRequestMessage message = new LoginRequestMessage(username, password);
            ctx.writeAndFlush(message);

            try {
                COUNT.await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if (!LOGIN_STATUS.get()) {
                ctx.channel().close();
                return;
            }
            while (true) {
                System.out.println("==================================");
                System.out.println("send [username] [content]");
                System.out.println("gsend [group name] [content]");
                System.out.println("gcreate [group name] [m1,m2,m3...]");
                System.out.println("gmembers [group name]");
                System.out.println("gjoin [group name]");
                System.out.println("gquit [group name]");
                System.out.println("quit");
                System.out.println("==================================");
                String command = sc.nextLine();
                String[] s = command.split(" ");
                switch (s[0]) {
                    case "send":
                        ctx.writeAndFlush(new ChatRequestMessage(username, s[1], s[2]));
                        break;
                    case "gsend":
                        ctx.writeAndFlush(new GroupChatRequestMessage(username, s[1], s[2]));
                        break;
                    case "gcreate":
                        Set<String> set = new HashSet<>(Arrays.asList(s[2].split(",")));
                        set.add(username); // 加入自己
                        ctx.writeAndFlush(new GroupCreateRequestMessage(s[1], set));
                        break;
                    case "gmembers":
                        ctx.writeAndFlush(new GroupMembersRequestMessage(s[1]));
                        break;
                    case "gjoin":
                        ctx.writeAndFlush(new GroupJoinRequestMessage(username, s[1]));
                        break;
                    case "gquit":
                        ctx.writeAndFlush(new GroupQuitRequestMessage(username, s[1]));
                        break;
                    case "quit":
                        ctx.channel().close();
                        return;
                }
            }

        }, "login").start();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        LoginResponseMessage message = null;
        if (msg instanceof LoginResponseMessage) {
            message = (LoginResponseMessage) msg;
            System.out.println(message.getReason());
            LOGIN_STATUS.set(message.isSuccess());
            COUNT.countDown();
        }

    }
}
