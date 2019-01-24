package com.justplay1994.github.cruiser.core.netty.server.http.proxyserver.front;

import com.justplay1994.github.cruiser.core.netty.server.http.proxyserver.back.HttpProxyBackClient;
import com.justplay1994.github.cruiser.core.netty.server.http.proxyserver.back.HttpProxyBackClientInBoundHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @Package: com.cetc.cloud.padu.gateway.service.impl
 * @Project: pandu-gateway-netty
 * @Creator: huangzezhou
 * @Create_Date: 2018/12/25 15:51
 * @Updater: huangzezhou
 * @Update_Date: 2018/12/25 15:51
 * @Update_Description: huangzezhou 补充
 * @Description:
 **/

/**
 * 前端
 * 面向客户端, 处理反向代理服务器的输入（读）处理
 * 职责：
 * 接收client请求、建立下游连接、将请求包转发至下游服务。
 * <p>
 * channel流程：
 * REGISTERED -> CONNECT -> ACTIVE -> WRITE -> FLUSH -> CLOSE -> INACTIVE -> UNREGISTERED
 * <p>
 * <p>
 * 已解决问题：每次在后端还未建立连接的时候，前端就已经读取并消费了数据，导致建立后没有内容可以发送。
 * <p>
 * 问：在前端 active的时候建立后端连接，还是在read的时候建立连接？
 * 答：TCP反向代理应该active建立，保证链路建立后才发送信息。但是http应该在read中，因为信息只发送一次，错过就没了
 *
 * TODO 解决问题：nginx作为前端服务器，前端的href跳转回带上nginx的端口，导致反向代理的端口被改（ip未被改），最终没法访问前端页面。
 *
 * @see CombinedChannelDuplexHandler 既是inbound也是outbound
 *
 * TODO 新增功能：动态路由表控制管理
 * TODO 新增功能：拦截器机制
 */
@Slf4j
public class HttpProxyFrontServerInBoundHandler extends ChannelInboundHandlerAdapter {


    private Channel tomcatChannel;

    @Override
    public void channelRead(final ChannelHandlerContext ctx, final Object msg) throws Exception {
        if (msg instanceof FullHttpRequest)
            new HttpProxyBackClient(ctx, (FullHttpRequest) msg).connect();
    }

    @Override
    public void channelActive(final ChannelHandlerContext ctx) throws Exception {

    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.fireChannelReadComplete();
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        ctx.fireUserEventTriggered(evt);
    }

    @Override
    public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
        ctx.fireChannelWritabilityChanged();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        if (tomcatChannel != null) {
            closeOnFlush(tomcatChannel);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        closeOnFlush(ctx.channel());
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        ctx.fireChannelRegistered();
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        ctx.fireChannelUnregistered();
    }

    /**
     * Closes the specified channel after all queued write requests are flushed.
     */
    static void closeOnFlush(Channel ch) {
        if (ch.isActive()) {
            ch.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
        }
    }
}
