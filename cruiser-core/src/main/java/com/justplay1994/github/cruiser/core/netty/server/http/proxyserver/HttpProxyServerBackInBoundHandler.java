package com.justplay1994.github.cruiser.core.netty.server.http.proxyserver;

import io.netty.channel.*;
import io.netty.handler.codec.http.FullHttpResponse;
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
public class HttpProxyServerBackInBoundHandler extends ChannelInboundHandlerAdapter {

    private final Channel inboundChannel;

    public HttpProxyServerBackInBoundHandler(Channel inboundChannel){
        this.inboundChannel = inboundChannel;
    }

    @Override
    public void channelRead(final ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof FullHttpResponse){
            HttpResponse response = (FullHttpResponse)msg;

            log.info(response.toString());
        }

        inboundChannel.writeAndFlush(msg).addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) {
                if (future.isSuccess()) {
                    ctx.channel().read();
                } else {
                    future.channel().close();
                }
            }
        });
    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        inboundChannel.read();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        HttpProxyServerFrontInBoundHandler.closeOnFlush(inboundChannel);
    }
    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        ctx.fireChannelRegistered();
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        ctx.fireChannelUnregistered();
    }
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        log.error("",cause);
        cause.printStackTrace();
        HttpProxyServerFrontInBoundHandler.closeOnFlush(ctx.channel());
    }

}
