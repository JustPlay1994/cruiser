package com.justplay1994.github.cruiser.core.netty.server.http.proxyserver;

import com.justplay1994.github.cruiser.core.netty.server.CruiserServerBootStrap;
import com.justplay1994.github.cruiser.core.netty.server.http.proxyserver.front.HttpProxyChannelInitializer;

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
