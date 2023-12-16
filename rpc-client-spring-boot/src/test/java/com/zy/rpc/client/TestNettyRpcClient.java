package com.zy.rpc.client;

import com.zy.rpc.client.message.RequestMetadata;
import com.zy.rpc.client.transport.netty.NettyRpcClient;
import com.zy.rpc.core.enums.MessageType;
import com.zy.rpc.core.enums.SerializationType;
import com.zy.rpc.core.protocol.MessageHeader;
import com.zy.rpc.core.protocol.RpcMessage;
import lombok.extern.slf4j.Slf4j;

/***
 * @author zy
 * @date 2023年12月04日 13:57
 */
@Slf4j
public class TestNettyRpcClient {
    public static void main(String[] args) {
        MessageHeader messageHeader = MessageHeader.build(SerializationType.KRYO.name());
        messageHeader.setMessageType(MessageType.HEARTBEAT_REQUEST.getType());
        messageHeader.setLength("sayHello".length());
        RpcMessage rpcMessage = new RpcMessage();
        rpcMessage.setHeader(messageHeader);
        rpcMessage.setBody("sayHello");

        RequestMetadata requestMetadata = RequestMetadata.builder()
                .rpcMessage(rpcMessage)
                .port(8888)
                .serverAddr("localhost")
                .timeout(-1).build();
        RpcMessage response = new NettyRpcClient().sendRpcRequest(requestMetadata);
        System.out.println(response);
    }
}
