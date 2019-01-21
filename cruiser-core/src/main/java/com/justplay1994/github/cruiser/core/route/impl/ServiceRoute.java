package com.justplay1994.github.cruiser.core.route.impl;

/**
 * @Package: com.justplay1994.github.cruiser.core.route
 * @Project: cruiser
 * @Creator: huangzezhou
 * @Create_Date: 2019/1/17 10:43
 * @Updater: huangzezhou
 * @Update_Date: 2019/1/17 10:43
 * @Update_Description: huangzezhou 补充
 * @Description:
 **/

import com.justplay1994.github.cruiser.core.route.CruiserRoute;
import lombok.Data;

import java.util.List;

/**
 * 服务路由
 * 支持location与path 一对多关系，通过负载均衡算法返回转发路径
 */
public class ServiceRoute extends CruiserRoute {

    String location;
    List<RouteEntry> routeEntryList;


    @Override
    public String getLocation() {
        return location;
    }

    @Override
    public String getPath() {
        return routeEntryList.get((int) (Math.random() * routeEntryList.size())).getPath();
    }

    @Data
    class RouteEntry{
        String path;
    }

    /**
     * 线程安全修改属性
     * @param location
     * @param cruiserRoute
     * @param includeNull true：将所有属性进行覆盖，包括null值。 false：只修改入参不为null的值
     */
    synchronized public void updateRoute(String location, CruiserRoute cruiserRoute, boolean includeNull) {
        if (includeNull){

        }else {

        }
    }
}
