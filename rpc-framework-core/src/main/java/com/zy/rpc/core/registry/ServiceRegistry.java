package com.zy.rpc.core.registry;


import com.zy.rpc.core.extension.SPI;
import com.zy.rpc.core.message.ServiceInfo;

/**
 * 服务注册中心接口
 *
 * @author zy
 * @version 1.0
 */
@SPI
public interface ServiceRegistry {

    /**
     * 注册/重新注册一个服务信息到 注册中心
     *
     * @param serviceInfo 服务信息
     */
    void register(ServiceInfo serviceInfo) throws Exception;

    /**
     * 接触注册/移除一个服务信息从 注册中心
     *
     * @param serviceInfo 服务信息
     */
    void unregister(ServiceInfo serviceInfo) throws Exception;

    /**
     * 关闭与服务注册中心的连接
     */
    void destroy() throws Exception;

}
