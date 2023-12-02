package com.azy.serialize;

/**
 * 2023-11-29 17:28:20
 */
public class SerializerFactory {
    public static Serializer getSerializer(){
        return new JdkSerialization();
    }
}
