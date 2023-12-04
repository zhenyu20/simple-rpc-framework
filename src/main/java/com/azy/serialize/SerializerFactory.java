package com.azy.serialize;

/**
 * 2023-11-29 17:28:20
 */
public class SerializerFactory {
    public static Serializer getSerializer(String serializerType){
        switch(serializerType){
            case "jdk":
                return new JdkSerialization();
            case "json":{
                return new JSONSerialization();
            }
//            default: {
//                //抛异常
//            }
        }

        return null;
    }
}
