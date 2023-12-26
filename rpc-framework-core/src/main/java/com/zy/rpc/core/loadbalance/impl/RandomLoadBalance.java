package com.zy.rpc.core.loadbalance.impl;


import com.zy.rpc.core.loadbalance.AbstractLoadBalance;
import com.zy.rpc.core.message.RpcRequest;
import com.zy.rpc.core.message.ServiceInfo;

import java.util.List;
import java.util.Random;

/**
 * 随机负载均衡策略实现类
 *
 * @author zy
 * @version 1.0
 */
public class RandomLoadBalance extends AbstractLoadBalance {

    final Random random = new Random();

    @Override
    protected ServiceInfo doSelect(List<ServiceInfo> invokers, RpcRequest request) {
        return invokers.get(random.nextInt(invokers.size()));
    }
}
