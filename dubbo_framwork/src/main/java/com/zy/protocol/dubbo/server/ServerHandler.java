package com.zy.protocol.dubbo.server;

import com.zy.msg.Invocation;
import com.zy.register.LocalRegister;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.lang.reflect.Method;

public class ServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Invocation invocation = (Invocation)msg;

        String interfaceName = invocation.getInterfaceName();
        Class serviceImpl = LocalRegister.get(interfaceName);

        Method method = serviceImpl.getMethod(invocation.getMethodName(), invocation.getParamsType());
        Object result = method.invoke(serviceImpl.newInstance(), invocation.getParams());
        System.out.println(result);
        ctx.writeAndFlush(String.format("Server response %s",result));
        //super.channelRead(ctx, msg);
    }
}
