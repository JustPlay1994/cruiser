package com.justplay1994.github.cruiser.core.netty.server.http.proxyserver.back;

import com.justplay1994.github.cruiser.core.exception.RouteException;
import com.justplay1994.github.cruiser.core.route.BaseCruiserRoute;
import com.justplay1994.github.cruiser.core.route.RoutingTable;
import com.justplay1994.github.cruiser.core.route.impl.SimpleRoutingTable;
import io.netty.handler.codec.http.FullHttpRequest;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * @Package: com.justplay1994.github.cruiser.core.netty.server.http.proxyserver.back
 * @Project: cruiser
 * @Creator: huangzezhou
 * @Create_Date: 2019/1/21 20:41
 * @Updater: huangzezhou
 * @Update_Date: 2019/1/21 20:41
 * @Update_Description: huangzezhou 补充
 * @Description:
 **/
public abstract class AbstractForward {

    FullHttpRequest msg;

    Class<? extends RoutingTable> routingTable;

    abstract public void forward() throws URISyntaxException, MalformedURLException, RouteException;
}
