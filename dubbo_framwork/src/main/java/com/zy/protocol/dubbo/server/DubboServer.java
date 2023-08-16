package com.zy.protocol.dubbo.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

import java.net.Socket;

public class DubboServer {
    public void start(String hostName, int port) {
        try {
            final ServerBootstrap bootstrap = new ServerBootstrap();
            NioEventLoopGroup boss = new NioEventLoopGroup(1);
            bootstrap.group(boss)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            pipeline.addLast("decoder", new ObjectDecoder(ClassResolvers
                                            .weakCachingConcurrentResolver(this.getClass()
                                                    .getClassLoader()))).
                                    addLast("encoder", new ObjectEncoder()).
                                    addLast("handler",new ServerHandler());

                        }
                    });
            bootstrap.bind(hostName, port).sync();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
