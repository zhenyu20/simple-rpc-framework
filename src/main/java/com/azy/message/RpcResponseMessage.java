package com.azy.message;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 2023-12-04 13:46:30
 */
@Setter
@Getter
@ToString(callSuper = true)
public class RpcResponseMessage extends Message{
    /**
     * 返回值
     */
    private Object returnValue;
    /**
     * 异常值
     */
    private Exception exceptionValue;

    @Override
    public int getMessageType() {
        return RPC_MESSAGE_TYPE_RESPONSE;
    }
}
