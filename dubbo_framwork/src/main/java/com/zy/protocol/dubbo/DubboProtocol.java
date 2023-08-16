package com.zy.protocol.dubbo;

import com.zy.protocol.Invoker;
import com.zy.protocol.Protocol;
import com.zy.protocol.URL;
import com.zy.protocol.dubbo.server.DubboServer;
import com.zy.register.LocalRegister;
import com.zy.register.RemoteRegister;

public class DubboProtocol implements Protocol {
    /**
     * 服务端 注册
     * 启动服务端服务
     * @param url
     */
    public void export(URL url,Class clazz) {
        LocalRegister.register(url.getInterfaceName(),clazz);
        RemoteRegister.regist(url.getInterfaceName(),url);
        new DubboServer().start(url.getHostname(), url.getPort());
    }

    /**
     * 客户端调用使用
     *
     * @param url
     * @return
     */
    public Invoker refer(URL url) {
        return new DubboInvoke(url);
    }
}
