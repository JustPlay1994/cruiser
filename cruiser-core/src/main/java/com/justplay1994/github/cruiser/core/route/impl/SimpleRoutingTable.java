package com.justplay1994.github.cruiser.core.route.impl;

/**
 * @Package: com.justplay1994.github.cruiser.core.route.impl
 * @Project: cruiser
 * @Creator: huangzezhou
 * @Create_Date: 2019/1/17 11:11
 * @Updater: huangzezhou
 * @Update_Date: 2019/1/17 11:11
 * @Update_Description: huangzezhou 补充
 * @Description:
 **/

import com.justplay1994.github.cruiser.core.exception.RouteException;
import com.justplay1994.github.cruiser.core.route.BaseCruiserRoute;
import com.justplay1994.github.cruiser.core.route.RoutingTable;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * thread safe
 *
 * singleton
 */
@Slf4j
public class SimpleRoutingTable implements RoutingTable {

    private static SimpleRoutingTable simpleRoutingTable;

    public static SimpleRoutingTable getInstance(){
        if (simpleRoutingTable == null)
            simpleRoutingTable =  new SimpleRoutingTable();
        return simpleRoutingTable;
    }


//TODO 需要将以下方法改造为静态方法，否则使用还需要构造，应该是属于工具，静态即可

    /**
     * 支持通配符
     * "?" 一个字符
     * "*" 匹配0个或多个字符
     * "**" 匹配0个或多个目录
     * 使用pathMatch的引擎为org.springframework.util.AntPathMatcher
     * 无固定优先级
     *
     * 改为前缀匹配逻辑
     *
     * 支持path或者full proxy_pass
     * @return
     */
    public BaseCruiserRoute match(String location) {

//        PathMatcher pathMatcher = new AntPathMatcher();

        for (String __location: routingTable.keySet()){

            if (location.startsWith(__location))
                return routingTable.get(__location);

//            if (pathMatcher.match(__location, location))
//                return routingTable.get(__location);
        }
        return null;
    }

    @Override
    public void addRoute(String location, BaseCruiserRoute cruiserRoute) throws RouteException {
        if (routingTable.get(location) != null)
            throw new RouteException("Add route error, this location["+location+"] is exist");
        routingTable.put(location, cruiserRoute);
    }

    @Override
    public void addRoute(String location, String path) throws Exception {
        if (routingTable.get(location) != null)
            throw new RouteException("Add route error, this location["+location+"] is exist");
        routingTable.put(location, new BaseCruiserRoute(location, path));
    }

    @Override
    public void deleteRoute(String location) throws RouteException {
        if (routingTable.get(location) != null)
            throw new RouteException("Delete route error, this location["+location+"] is not exist");
        routingTable.remove(location);
    }

    public BaseCruiserRoute getRoute(String location) {
        return routingTable.get(location);
    }

    /**
     *
     * 声明synchronized的普通方法：在该实例中，只有一个synchronized方法在执行，实例内互斥。因为需要获取synchronized(this)
     * 而synchronized应用在static方法上，那是对当前对应的*.class进行持锁。因为需要获取synchronized(*.class)
     * @param location
     * @param cruiserRoute
     */
    @Override
    synchronized public void updateRoute(String location, BaseCruiserRoute cruiserRoute) {

        routingTable.get(location).updateAll(cruiserRoute);

    }



}
