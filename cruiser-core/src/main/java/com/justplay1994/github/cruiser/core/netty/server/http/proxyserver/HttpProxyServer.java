package com.justplay1994.github.cruiser.core.netty.server.http.proxyserver;

import com.justplay1994.github.cruiser.core.netty.server.CruiserServerBootStrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.logging.LoggingHandler;

/**
 * @Package: com.cetc.cloud.padu.gateway.service.impl
 * @Project: pandu-gateway-netty
 * @Creator: huangzezhou
 * @Create_Date: 2018/12/25 15:12
 * @Updater: huangzezhou
 * @Update_Date: 2018/12/25 15:12
 * @Update_Description: huangzezhou 补充
 * @Description:
 **/

/**
 * http代理服务器
 */
public class HttpProxyServer extends CruiserServerBootStrap{

    public HttpProxyServer(int port){
        super(port, new HttpProxyChannelInitializer());
    }

}
