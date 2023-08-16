package com.zy.protocol;

import com.zy.msg.Invocation;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyFactory {
    @SuppressWarnings("unchecked")
    public static <T>  T getProxy(final Class interfaceClass) {
       return (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(),  new Class[]{interfaceClass}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {


                Invocation invocation = new Invocation(interfaceClass.getName(), method.getName(), args, method.getParameterTypes());

                Invoker invoker = ClusterInvoker.join(interfaceClass);

                return invoker.invoke(invocation);
            }
        });
    }
}
