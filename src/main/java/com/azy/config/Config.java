package com.azy.config;

import com.azy.serialize.Serializer;
import com.azy.serialize.SerializerFactory;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 2023-11-29 17:23:22
 */
@Slf4j
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

    public static Serializer getSerializerAlgorithm() {
        String serializerType = properties.getProperty("serializer.algorithm");
        log.debug("序列化方式{}",serializerType);
        if(serializerType == null) {
            return SerializerFactory.getSerializer("json");
        } else {
            return SerializerFactory.getSerializer(serializerType);
        }
    }
}