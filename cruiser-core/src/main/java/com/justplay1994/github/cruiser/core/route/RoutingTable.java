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

/**
 * 路由表,基础功能CURD：
 * 1. 增：新增一条路由
 * 2. 改：修改一条路由
 * 3. 查：根据path 快速找到后端转发地址
 * 4. 删：删除一条路由
 *
 * TODO 后续需要实现一个路由，具备线程安全，并且保证数据一致性（先改库，再改内存）
 */
public interface RoutingTable {

    // ------------ 路由表维护API -----------------

    void addRoute(String location, CruiserRoute cruiserRoute) throws RouteException;

    void deleteRoute(String location) throws RouteException;

    CruiserRoute getRoute(String location);

    void  updateRoute(String location, CruiserRoute cruiserRoute);
}
