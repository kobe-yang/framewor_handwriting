package com.zy.impl;

import com.zy.api.HelloService;

public class HelloServiceImpl implements HelloService {
    public String hello(String msg) {
        return "hello " + msg;
    }
}
