package com.justplay1994.github.cruiser.core.netty.server.http.proxyserver.back;

import com.justplay1994.github.cruiser.core.route.RoutingTable;
import org.junit.Test;

/**
 * @Package: com.justplay1994.github.cruiser.core.netty.server.http.proxyserver.back
 * @Project: cruiser
 * @Creator: huangzezhou
 * @Create_Date: 2019/1/21 21:20
 * @Updater: huangzezhou
 * @Update_Date: 2019/1/21 21:20
 * @Update_Description: huangzezhou 补充
 * @Description:
 **/
public class AbstractForwardTest {


    @Test
    public void reflectionTest() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        Class.forName(RoutingTable.class.getName()).newInstance();
    }

}