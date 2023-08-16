package com.zy.consumer;

import com.zy.api.HelloService;
import com.zy.protocol.ProxyFactory;

public class Consumer {

    public static void main(String[] args) {
        HelloService helloService = ProxyFactory.getProxy(HelloService.class);
        String result = helloService.hello("zhangyang");
        System.out.println(result);
    }
}
