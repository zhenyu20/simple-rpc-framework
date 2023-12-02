package com.azy.message;

import java.awt.*;

/**
 * 2023-11-29 15:45:36
 */
public class PingMessage extends Message{
    @Override
    public int getMessageType() {
        return PingMessage;
    }
}
