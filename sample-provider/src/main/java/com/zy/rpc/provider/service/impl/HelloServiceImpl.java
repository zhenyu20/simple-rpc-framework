package com.zy.rpc.provider.service.impl;

import com.zy.rpc.api.service.HelloService;
import com.zy.rpc.server.annotation.RpcService;

@RpcService(interfaceClass = HelloService.class)
public class HelloServiceImpl implements HelloService {
    @Override
    public String sayHello(String name) {
        return "Hello, " + name;
    }
}
