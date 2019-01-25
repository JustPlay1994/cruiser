package com.justplay1994.github.cruiser.core.route;

import com.justplay1994.github.cruiser.core.exception.RouteException;
import com.justplay1994.github.cruiser.core.route.impl.MemoryRoutingTable;
import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * @Package: com.justplay1994.github.cruiser.core.route
 * @Project: cruiser
 * @Creator: huangzezhou
 * @Create_Date: 2019/1/23 19:52
 * @Updater: huangzezhou
 * @Update_Date: 2019/1/23 19:52
 * @Update_Description: huangzezhou 补充
 * @Description:
 **/
public class RoutingTableTest {

    @Test
    public void routingTableTest() throws RouteException, MalformedURLException {
        BaseCruiserRouteItem baseCruiserRoute = new BaseCruiserRouteItem();
        baseCruiserRoute.setProxy_pass(new URL("https://www.baidu.com"));
        baseCruiserRoute.setLocation("/baidu/*");
        MemoryRoutingTable.getInstance().addRoute("/baidu", baseCruiserRoute);

        BaseCruiserRouteItem b = MemoryRoutingTable.getInstance().match("/baidu");
        BaseCruiserRouteItem b2 = MemoryRoutingTable.getInstance().match("/baidu/123");
        System.out.println();

    }


}