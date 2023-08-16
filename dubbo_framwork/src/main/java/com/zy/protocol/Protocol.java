package com.zy.protocol;

public interface Protocol {
    void export(URL url,Class clazz);
    Invoker refer(URL url);
}
