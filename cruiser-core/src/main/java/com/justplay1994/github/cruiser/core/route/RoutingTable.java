package com.justplay1994.github.cruiser.core.route;

/**
 * @Package: com.justplay1994.github.cruiser.core.route
 * @Project: cruiser
 * @Creator: huangzezhou
 * @Create_Date: 2019/1/17 10:17
 * @Updater: huangzezhou
 * @Update_Date: 2019/1/17 10:17
 * @Update_Description: huangzezhou 补充
 * @Description:
 **/

import com.justplay1994.github.cruiser.core.exception.RouteException;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 路由表,基础功能CURD：
 * 1. 增：新增一条路由
 * 2. 改：修改一条路由
 * 3. 查：根据path 快速找到后端转发地址
 * 4. 删：删除一条路由
 *
 * 高级功能[都支持每条路由特殊配置]：
 * 1. 是否保持长连接，保持连接多久
 * 2. 最大连接数
 * 3. 流量控制等等，可扩展功能。
 *
 * TODO 后续需要实现一个路由，具备线程安全，并且保证数据一致性（先改库，再改内存）
 */
public interface RoutingTable {

    ConcurrentHashMap<String, BaseCruiserRouteItem> routingTable = new ConcurrentHashMap<>();

    // ------------ 路由表维护API -----------------

    void addRoute(String location, BaseCruiserRouteItem cruiserRoute) throws RouteException;

    public void addRoute(String location, String path) throws RouteException, Exception;

    void deleteRoute(String location) throws RouteException;

    BaseCruiserRouteItem getRoute(String location);

    void updateRoute(String location, BaseCruiserRouteItem cruiserRoute);

}
