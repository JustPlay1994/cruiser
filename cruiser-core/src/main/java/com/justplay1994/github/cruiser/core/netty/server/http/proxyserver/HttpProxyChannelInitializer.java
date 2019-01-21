package com.justplay1994.github.cruiser.core.netty.server.http.proxyserver;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * @Package: com.justplay1994.github.cruiser.core.netty.server.http
 * @Project: cruiser
 * @Creator: huangzezhou
 * @Create_Date: 2019/1/16 20:16
 * @Updater: huangzezhou
 * @Update_Date: 2019/1/16 20:16
 * @Update_Description: huangzezhou 补充
 * @Description:
 **/
public class HttpProxyChannelInitializer extends ChannelInitializer<SocketChannel> {

    ChannelPipeline channelPipeline;

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        this.channelPipeline = ch.pipeline();
        // 新的一个编解码器
        ch.pipeline().addLast(new HttpServerCodec());
        //将多个http消息聚合
        ch.pipeline().addLast("aggregator", new HttpObjectAggregator(1024*1024*64));
        //反向代理前端
        ch.pipeline().addLast("proxyFrontInBound", new HttpProxyServerFrontInBoundHandler());
    }

    public void addLastChannel(ChannelHandler channelHandler){
        channelPipeline.addLast(channelHandler);
    }

    public void removeChannel(ChannelHandler channelHandler){
        channelPipeline.remove(channelHandler);
    }

}
