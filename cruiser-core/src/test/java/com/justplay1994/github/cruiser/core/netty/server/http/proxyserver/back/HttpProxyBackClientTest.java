package com.justplay1994.github.cruiser.core.netty.server.http.proxyserver.back;

import com.justplay1994.github.cruiser.core.exception.RouteException;
import com.justplay1994.github.cruiser.core.netty.server.http.proxyserver.HttpProxyServer;
import com.justplay1994.github.cruiser.core.route.impl.SimpleRoutingTable;
import io.netty.handler.codec.DefaultHeaders;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @Package: com.justplay1994.github.cruiser.core.netty.server.http.proxyserver.back
 * @Project: cruiser
 * @Creator: huangzezhou
 * @Create_Date: 2019/1/21 20:42
 * @Updater: huangzezhou
 * @Update_Date: 2019/1/21 20:42
 * @Update_Description: huangzezhou 补充
 * @Description:
 **/
public class HttpProxyBackClientTest {


    //TODO 判断基本变量属性 是放到静态方法区，还是堆，jvm虚拟机栈是否存放临时变量？ 程序计数器是每个程序一个？那多个函数怎么保留执行到哪一行（答：执行结束有个回跳）？

    @Test
    public void finalTest(){
        final int a = 10;
        int b = a;
        b = 1;
        System.out.println(a+" "+b);

        final OB ob1 = new OB();
        ob1.x = 10;
        ob1.y = 15;
        OB ob2 = ob1;
    }

    class OB{
        int x;
        int y;
    }


    @Test
    public void forwardTest() throws Exception {
        SimpleRoutingTable.getInstance().addRoute("/futian/","http://10.192.19.121:8081/");
        SimpleRoutingTable.getInstance().addRoute("/baidu","https://www.baidu.com");
        HttpProxyServer httpProxyServer = new HttpProxyServer(8844);
        httpProxyServer.start();
    }

    @Test
    public void defaultHeadersUpdateTest(){
        DefaultHttpHeaders defaultHttpHeaders = new DefaultHttpHeaders();


        defaultHttpHeaders.add("111",111);
        defaultHttpHeaders.add("222",222);
        defaultHttpHeaders.add("333",333);
        defaultHttpHeaders.remove("111");

        DefaultHttpHeaders defaultHttpHeaders1 = new DefaultHttpHeaders();
        defaultHttpHeaders.set(defaultHttpHeaders1);

        System.out.println();
    }


}