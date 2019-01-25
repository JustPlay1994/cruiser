package com.justplay1994.github.cruiser.core.netty.server.http.proxyserver.back;

/**
 * @Package: com.justplay1994.github.cruiser.core.netty.server.http.proxyserver
 * @Project: cruiser
 * @Creator: huangzezhou
 * @Create_Date: 2019/1/21 20:15
 * @Updater: huangzezhou
 * @Update_Date: 2019/1/21 20:15
 * @Update_Description: huangzezhou 补充
 * @Description:
 **/

import com.justplay1994.github.cruiser.core.exception.RouteException;
import com.justplay1994.github.cruiser.core.route.BaseCruiserRouteItem;
import com.justplay1994.github.cruiser.core.route.impl.MemoryRoutingTable;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;
import lombok.extern.slf4j.Slf4j;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * http反向代理后端客户端，用于与下游服务建立连接
 */
@Slf4j
public class HttpProxyBackClient extends AbstractForward{

    Channel backChannel;
    ChannelHandlerContext ctx;



    public HttpProxyBackClient(ChannelHandlerContext ctx, FullHttpRequest msg){
        this.ctx = ctx;
        this.msg = msg;
    }

    public void connect() {
        Channel frontChannel = ctx.channel();

        //TODO client bootstrap 各类配置封装
        Bootstrap b = new Bootstrap();
        b.group(new NioEventLoopGroup(1))
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, false);
        b.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            public void initChannel(SocketChannel ch) throws Exception {
//                ch.pipeline().addLast(new LoggingHandler());
                ch.pipeline().addLast(new HttpClientCodec());
                ch.pipeline().addLast("aggregator", new HttpObjectAggregator(1024 * 1024 * 64));// 将 解码到的多个http消息合成一个FullHttpRequest/FullHttpRespone
                ch.pipeline().addLast(new HttpProxyBackClientInBoundHandler(frontChannel));
            }
        });


        try {
            forward();
            URL url = new URL(msg.uri());
            int backPort = url.getPort() == -1 ? 80 : url.getPort();
            ChannelFuture f = b.connect(url.getHost(), backPort);

            this.backChannel = f.channel();

            f.addListener(future -> {
                if (future.isSuccess()){
                    backChannel.writeAndFlush(msg).addListener((ChannelFutureListener) future1 -> {
                        if (future1.isSuccess()) {
                            // was able to flush out data, start to read the next chunk
                            ctx.channel().read();
                        } else {
                            future1.channel().close();
                        }
                    });
                }
            });
        }catch (Exception e){
            log.error("forward error!",e);
        }
    }

    @Override
    public void forward() throws RouteException, URISyntaxException, MalformedURLException {
        URI uri = new URI(msg.uri());
        BaseCruiserRouteItem baseCruiserRoute = MemoryRoutingTable.getInstance().match(uri.getPath());
        if (baseCruiserRoute == null)
            throw new RouteException("this location is not match! "+msg.uri());
        msg.setUri(baseCruiserRoute.getForwardUrl(getFullPath(uri)).toString());
    }

    private String getFullPath(URI uri){
        if (uri.getQuery() == null)
            return uri.getPath();
        else
            return uri.getPath() + "?" + uri.getQuery();
    }
}
