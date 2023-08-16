package com.zy.protocol;

import com.zy.msg.Invocation;

public interface Invoker {
    String invoke(Invocation invocation);
}
