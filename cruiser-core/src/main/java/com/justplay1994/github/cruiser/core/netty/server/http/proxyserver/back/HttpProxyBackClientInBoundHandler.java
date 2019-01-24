package com.justplay1994.github.cruiser.core.netty.server.http.proxyserver.back;

import com.justplay1994.github.cruiser.core.netty.server.http.proxyserver.front.HttpProxyFrontServerInBoundHandler;
import io.netty.channel.*;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * @Package: com.cetc.cloud.padu.gateway.service.impl
 * @Project: pandu-gateway-netty
 * @Creator: huangzezhou
 * @Create_Date: 2018/12/25 18:16
 * @Updater: huangzezhou
 * @Update_Date: 2018/12/25 18:16
 * @Update_Description: huangzezhou 补充
 * @Description:
 **/

/**
 * 反向代理后端
 * 面向后端服务, 处理反向代理服务器的输入（读）处理
 * 职责：
 * 接收下游服务器返回的包体，并转发给客户端。
 */
@Slf4j
public class HttpProxyBackClientInBoundHandler extends ChannelInboundHandlerAdapter {

    private final Channel frontChannel;

    public HttpProxyBackClientInBoundHandler(Channel frontChannel){
        this.frontChannel = frontChannel;
    }

    @Override
    public void channelRead(final ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof FullHttpResponse){
            HttpResponse response = (FullHttpResponse)msg;
//            response.headers().set(new DefaultHttpHeaders());
            response.headers().add(HttpHeaderNames.CACHE_CONTROL, "no-cache");  //取消缓存，防止301永久重定向，方便调试
            log.info(response.toString());
        }

        frontChannel.writeAndFlush(msg).addListener(future -> {
                if (future.isSuccess()) {
                    ctx.channel().read();
                }
        });
    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        frontChannel.read();
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        ctx.fireChannelRegistered();
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        ctx.fireChannelUnregistered();
    }

}
