package com.zy.rpc.server;

import lombok.extern.slf4j.Slf4j;

/**
 * 基于 Netty 实现的 RpcServer 服务类
 *
 * @author zy
 * @version 1.0
 */
@Slf4j
public class TestNettyRpcServer {

    public static void main(String[] args) {
        new com.zy.rpc.server.transport.netty.NettyRpcServer().start(8888);
    }
}
