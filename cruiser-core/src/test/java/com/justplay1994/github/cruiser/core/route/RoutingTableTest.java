package com.justplay1994.github.cruiser.core.route;

import com.justplay1994.github.cruiser.core.exception.RouteException;
import com.justplay1994.github.cruiser.core.route.impl.SimpleRoutingTable;
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
        BaseCruiserRoute baseCruiserRoute = new BaseCruiserRoute();
        baseCruiserRoute.setProxy_pass(new URL("https://www.baidu.com"));
        baseCruiserRoute.setLocation("/baidu/*");
        SimpleRoutingTable.getInstance().addRoute("/baidu", baseCruiserRoute);

        BaseCruiserRoute b = SimpleRoutingTable.getInstance().match("/baidu");
        BaseCruiserRoute b2 = SimpleRoutingTable.getInstance().match("/baidu/123");
        System.out.println();

    }


}