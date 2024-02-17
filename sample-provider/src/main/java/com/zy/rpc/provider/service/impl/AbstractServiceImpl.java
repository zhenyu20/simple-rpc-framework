package com.zy.rpc.provider.service.impl;

import com.zy.rpc.api.service.AbstractService;
import com.zy.rpc.server.annotation.RpcService;

/**
 * @author xy
 * @version 1.0
 */
@RpcService(interfaceClass = AbstractService.class)
public class AbstractServiceImpl extends AbstractService {
    @Override
    public String abstractHello(String name) {
        return "abstract hello " + name;
    }
}
