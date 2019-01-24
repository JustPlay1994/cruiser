package com.justplay1994.github.cruiser.core.netty.server;

import com.justplay1994.github.cruiser.core.netty.server.http.proxyserver.front.HttpProxyChannelInitializer;
import com.justplay1994.github.cruiser.core.netty.server.http.proxyserver.front.HttpProxyFrontServerInBoundHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.logging.LoggingHandler;

/**
 * @Package: com.justplay1994.github.cruiser.core.netty.server
 * @Project: cruiser
 * @Creator: huangzezhou
 * @Create_Date: 2019/1/16 19:51
 * @Updater: huangzezhou
 * @Update_Date: 2019/1/16 19:51
 * @Update_Description: huangzezhou 补充
 * @Description:
 **/


/**
 *
 *
 *
 * 低延迟模式: ChannelOption.TCP_NODELAY
 * handler在初始化时就会执行,而childHandler会在客户端成功connect后才执行,这是两者的区别。
 */
public class CruiserServerBootStrap {

    private EventLoopGroup bossGroup; //建立tcp连接的线程池
    private EventLoopGroup workerGroup ;  //channel线程池

    private ServerBootstrap b = new ServerBootstrap(); //服务端启动引导

    private int connectMaxThread;
    private int channelMaxThread;
    private Class<? extends ServerChannel> channelClass;
    private ChannelInitializer channelInitializer;
    private ChannelOption<Boolean> childOption;
    private Boolean value;

    private int port;

    public CruiserServerBootStrap(int port){
        this(port,8, 8, NioServerSocketChannel.class, new HttpProxyChannelInitializer(), ChannelOption.TCP_NODELAY, true);
    }

    public CruiserServerBootStrap(int port, ChannelInitializer channelInitializer){
        this(port,8, 8, NioServerSocketChannel.class, channelInitializer, ChannelOption.TCP_NODELAY, true);
    }

    public CruiserServerBootStrap(int port, int connectMaxThread, int channelMaxThread, Class<? extends ServerChannel> channelClass,
                                  ChannelInitializer channelInitializer, ChannelOption<Boolean> childOption, Boolean value){
        this.port = port;
        this.bossGroup = new NioEventLoopGroup(connectMaxThread);
        this.workerGroup = new NioEventLoopGroup(channelMaxThread);
        b.group(bossGroup, workerGroup);
        b.channel(channelClass);
        b.childHandler(channelInitializer);
        b.childOption(childOption, value);
    }

    public void start(){

        ServerBootstrap b = new ServerBootstrap();
        b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch) throws Exception {
                        new LoggingHandler();
                        // 新的一个编解码器
                        ch.pipeline().addLast(new HttpServerCodec());
                        //将多个http消息聚合
                        ch.pipeline().addLast("aggregator", new HttpObjectAggregator(1024*1024*64));// 将 解码到的多个http消息合成一个FullHttpRequest/FullHttpRespone
                        ch.pipeline().addLast(new HttpProxyFrontServerInBoundHandler());
                    }
                }).childOption(ChannelOption.SO_KEEPALIVE, false);
        ChannelFuture f = null;
        try {
            f = b.bind(port).sync();
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
}
