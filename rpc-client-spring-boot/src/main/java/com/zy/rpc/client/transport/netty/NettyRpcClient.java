package com.zy.rpc.client.transport.netty;

import com.zy.rpc.client.message.RequestMetadata;
import com.zy.rpc.client.transport.RpcClient;
import com.zy.rpc.core.protocol.RpcMessage;
import lombok.extern.slf4j.Slf4j;


/**
 * 基于 Netty 实现的 Rpc Client 类
 *
 * @author zy
 * @version 1.0
 */
@Slf4j
public class NettyRpcClient implements RpcClient {

    @Override
    public RpcMessage sendRpcRequest(RequestMetadata requestMetadata) {
        return null;
    }
}
