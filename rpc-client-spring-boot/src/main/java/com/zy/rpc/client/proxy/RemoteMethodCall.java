package com.zy.rpc.client.proxy;

import com.zy.rpc.client.message.RequestMetadata;
import com.zy.rpc.client.config.RpcClientProperties;
import com.zy.rpc.client.transport.RpcClient;
import com.zy.rpc.core.message.RpcRequest;
import com.zy.rpc.core.message.RpcResponse;
import com.zy.rpc.core.message.ServiceInfo;
import com.zy.rpc.core.discovery.ServiceDiscovery;
import com.zy.rpc.core.exception.RpcException;
import com.zy.rpc.core.protocol.MessageHeader;
import com.zy.rpc.core.protocol.RpcMessage;

import java.lang.reflect.Method;

/**
 * 远程方法调用工具类
 *
 * @author zy
 * @version 1.0
 */
public class RemoteMethodCall {

    /**
     * 发起 rpc 远程方法调用的公共方法
     *
     * @param discovery   服务发现中心
     * @param rpcClient   rpc 客户端
     * @param serviceName 服务名称
     * @param properties  配置属性
     * @param method      调用的具体方法
     * @param args        方法参数
     * @return 返回方法调用结果
     */
    public static Object remoteCall(ServiceDiscovery discovery, RpcClient rpcClient, String serviceName,
                                    RpcClientProperties properties, Method method, Object[] args) {
        // 构建请求头
        MessageHeader header = MessageHeader.build(properties.getSerialization());
        // 构建请求体
        RpcRequest request = new RpcRequest();
        request.setServiceName(serviceName);
        request.setMethod(method.getName());
        request.setParameterTypes(method.getParameterTypes());
        request.setParameterValues(args);

        // 进行服务发现
        ServiceInfo serviceInfo = discovery.discover(request);
        if (serviceInfo == null) {
            throw new RpcException(String.format("The service [%s] was not found in the remote registry center.",
                    serviceName));
        }

        // 构建通信协议信息
        RpcMessage rpcMessage = new RpcMessage();
        rpcMessage.setHeader(header);
        rpcMessage.setBody(request);

        // 构建请求元数据
        RequestMetadata metadata = RequestMetadata.builder()
                .rpcMessage(rpcMessage)
                .serverAddr(serviceInfo.getAddress())
                .port(serviceInfo.getPort())
                .timeout(properties.getTimeout()).build();

        // TODO：此处可以实现失败重试机制
        // 发送网络请求，获取结果
        RpcMessage responseRpcMessage = rpcClient.sendRpcRequest(metadata);

        if (responseRpcMessage == null) {
            throw new RpcException("Remote procedure call timeout.");
        }

        // 获取响应结果
        RpcResponse response = (RpcResponse) responseRpcMessage.getBody();

        // 如果 远程调用 发生错误
        if (response.getExceptionValue() != null) {
            throw new RpcException(response.getExceptionValue());
        }
        // 返回响应结果
        return response.getReturnValue();
    }

}
