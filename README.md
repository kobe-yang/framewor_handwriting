# framewor_handwriting
# dubbo

rpc 功能就是将远程调用模拟成本地调用。需要做一下几步

- 网络通信框架
- 定制服务端、客户端消息协议格式
    
    ```java
    	
    private String interfaceName;
    
        private String methodName;
    
        private Object[] params;
    
        private Class[] paramsType;
    ```
    
- 服务端注册、客户端拉去注册信息，注册信息格式
    
    ```java
    	
    // 协议名 获取不同的协议 dubbo、http等
        private String protocol;
    //服务端地址
        private String hostname;
        private Integer port;
    //调用的服务名称
        private String interfaceName;
    //todo 一个接口多个实现类+ 版本好实现
    ```
    
- 客户端
    - 根据接口名称获取对应的cluster配置
    - 调用invoke（内部进行负载均衡）
    - 客户端server将消息发送到对应的服务器
    - 等待接收服务端返回数据
    
    ```java
    
    Invocation invocation = new Invocation(interfaceClass.getName(), method.getName(), args, method.getParameterTypes());
    						
                    Invoker invoker = ClusterInvoker.join(interfaceClass);
    
                    return invoker.invoke(invocation);
    ```
    
    - 根据接口构建数据发送给服务端并且接受消息
    
    ```java
    
    DubboClient dubboClient = new DubboClient();
            return  dubboClient.send(url.getHostname(),url.getPort(),invocation);
    ```
    

- 服务端
    
    注册服务、启动服务端、接受消息，根据消息解析调用对应的类和方法。
    

- 服务端
    - 注册服务
    - 接受

- netty实现客户端 服务端

服务端：

```
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
```

客户端：

```
public void start(String hostName, Integer port) {
        client = new ClientHandler();

        Bootstrap b = new Bootstrap();
        EventLoopGroup group = new NioEventLoopGroup();
        b.group(group)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        ChannelPipeline pipeline = socketChannel.pipeline();
                        pipeline.addLast("decoder", new ObjectDecoder(ClassResolvers
                                .weakCachingConcurrentResolver(this.getClass()
                                        .getClassLoader())));
                        pipeline.addLast("encoder", new ObjectEncoder());
                        pipeline.addLast("handler", client);
                    }
                });

        try {
            b.connect(hostName, port).sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public String send(String hostName, Integer port, Invocation invocation) {
        if (client == null) {
            start(hostName, port);
        }

        client.setInvocation(invocation);
        try {
            return (String) executorService.submit(client).get();
        } catch (InterruptedException e) {
              e.printStackTrace();
        } catch (ExecutionException e) {
          e.printStackTrace();
        }
        return null;
    }

```

1
