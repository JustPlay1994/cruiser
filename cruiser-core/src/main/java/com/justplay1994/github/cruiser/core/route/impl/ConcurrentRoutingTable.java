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
import com.justplay1994.github.cruiser.core.route.CruiserRoute;
import com.justplay1994.github.cruiser.core.route.RoutingTable;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 线程安全路由表
 */
public class ConcurrentRoutingTable implements RoutingTable {

    ConcurrentHashMap<String, CruiserRoute> routingTable = new ConcurrentHashMap<>();


    /**
     * 支持通配符
     * = 表示精确匹配
     * ^~ 表示uri以某个常规字符串开头,大多情况下用来匹配url路径，nginx不对url做编码，因此请求为/static/20%/aa，可以被规则^~ /static/ /aa匹配到（注意是空格）。
     * ~ 正则匹配(区分大小写)
     * ~* 正则匹配(不区分大小写)
     * !~和!~*分别为区分大小写不匹配及不区分大小写不匹配 的正则
     * / 任何请求都会匹配
     * @return
     */
    public CruiserRoute match(String regex){
        // TODO 完善逻辑
        for (String location: routingTable.keySet()){
            location.matches(regex);
        }
        return null;
    }

    @Override
    public void addRoute(String location, CruiserRoute cruiserRoute) throws RouteException {
        if (routingTable.get(location) != null)
            throw new RouteException("Add route error, this location["+location+"] is exist");
        routingTable.put(location, cruiserRoute);
    }

    @Override
    public void deleteRoute(String location) throws RouteException {
        if (routingTable.get(location) != null)
            throw new RouteException("Delete route error, this location["+location+"] is not exist");
        routingTable.remove(location);
    }

    @Override
    public CruiserRoute getRoute(String location) {
        return routingTable.get(location);
    }

    /**
     * 更新机制？
     *
     * 更新方式：
     * 1. 直接把整个value指向另一个对象。
     * 2. 在原有对象上进行修改。 这种方式更好V
     *
     * 如何保证线程安全？
     * 同一个cruiserRoute对象，只能有一个进行修改。
     *
     * 锁如何设计？
     *
     *
     * @param location
     * @param cruiserRoute
     */
    @Override
    public void updateRoute(String location, CruiserRoute cruiserRoute) {

    }
}
