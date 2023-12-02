package com.azy.config;

import com.azy.serialize.Serializer;
import com.azy.serialize.SerializerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 2023-11-29 17:23:22
 */
public abstract class Config {
    static Properties properties;
    static {
        try (InputStream in = Config.class.getResourceAsStream("/application.properties")) {
            properties = new Properties();
            properties.load(in);
        } catch (IOException e) {
            throw new ExceptionInInitializerError(e);
        }
    }
    public static int getServerPort() {
        String value = properties.getProperty("server.port");
        if(value == null) {
            return 8080;
        } else {
            return Integer.parseInt(value);
        }
    }
    public static Serializer getSerializerAlgorithm() {
//        String serializerType = properties.getProperty("serializer.algorithm");
        return SerializerFactory.getSerializer();

//        if(value == null) {
//            return SerializerFactory.getSerializer(serializerType);
//        } else {
//            return SerializerFactory.getSerializer(serializerType);
//        }
    }
}