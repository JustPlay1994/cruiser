package com.justplay1994.github.cruiser.core.netty.server;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @Package: com.justplay1994.github.cruiser.core.netty.server
 * @Project: cruiser
 * @Creator: huangzezhou
 * @Create_Date: 2019/1/21 19:15
 * @Updater: huangzezhou
 * @Update_Date: 2019/1/21 19:15
 * @Update_Description: huangzezhou 补充
 * @Description:
 **/
public class CruiserServerBootStrapTest {

    @Test
    public void startHttpProxyServer(){
        CruiserServerBootStrap bootStrap = new CruiserServerBootStrap(8844);
        bootStrap.start();
    }

}