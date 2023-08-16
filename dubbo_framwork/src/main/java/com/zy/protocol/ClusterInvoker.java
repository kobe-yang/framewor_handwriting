package com.zy.protocol;

import com.zy.msg.Invocation;
import com.zy.register.RemoteRegister;

import java.util.ArrayList;
import java.util.List;

public class ClusterInvoker implements Invoker{
    private static List<Invoker> invokers = new ArrayList<>();

    public List<Invoker> getInvokers() {
        return invokers;
    }

    public void setInvokers(List<Invoker> invokers) {
        this.invokers = invokers;
    }

    public static Invoker join(Class interfaceClass) {
        ClusterInvoker clusterInvoker = new ClusterInvoker();
        List<URL> urls = RemoteRegister.get(interfaceClass.getName());
        for (URL url : urls) {
            Protocol protocol = ProtocolFactory.getProtocol(url.getProtocol());
            Invoker refer = protocol.refer(url);
            invokers.add(refer);
        }
        return clusterInvoker;
    }

    @Override
    public String invoke(Invocation invocation) {
        Invoker invoker = LoadBalance.random(invokers);
        return invoker.invoke(invocation);
    }
}
