package com.zy.rpc.core.extension.factory;

import com.zy.rpc.core.extension.ExtensionFactory;
import com.zy.rpc.core.extension.ExtensionLoader;
import com.zy.rpc.core.extension.SPI;

/**
 * @author zy
 * @version 1.0
 */
public class SpiExtensionFactory implements ExtensionFactory {
    @Override
    public <T> T getExtension(Class<T> type, String name) {
        if (type.isInterface() && type.isAnnotationPresent(SPI.class)) {
            return ExtensionLoader.getExtensionLoader(type).getExtension(name);
        }
        return null;
    }
}
