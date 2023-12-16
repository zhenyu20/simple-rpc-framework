package com.zy.rpc.client.transport;


import com.zy.rpc.client.message.RequestMetadata;
import com.zy.rpc.core.protocol.RpcMessage;

/**
 * Rpc 客户端类，负责向服务端发起请求（远程过程调用）
 *
 * @author zy
 * @version 1.0
 */
public interface RpcClient {

    /**
     * 发起远程过程调用
     *
     * @param requestMetadata rpc 请求元数据
     * @return 响应结果
     */
    RpcMessage sendRpcRequest(RequestMetadata requestMetadata);

}
