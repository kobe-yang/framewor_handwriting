package com.zy.protocol.dubbo;

import com.zy.msg.Invocation;
import com.zy.protocol.Invoker;
import com.zy.protocol.URL;
import com.zy.protocol.dubbo.client.DubboClient;

public class DubboInvoke implements Invoker {

    private URL url;
    public DubboInvoke(URL url) {
        this.url = url;
    }

    @Override
    public String invoke(Invocation invocation) {
        DubboClient dubboClient = new DubboClient();
        return  dubboClient.send(url.getHostname(),url.getPort(),invocation);
    }
}
