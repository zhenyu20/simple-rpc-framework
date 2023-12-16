package com.zy.rpc.core.message;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * 心跳检查消息类
 *
 * @author zy
 * @version 1.0
 */
@Data
@Builder
public class HeartbeatMessage implements Serializable {

    /**
     * 消息
     */
    private String msg;

}
