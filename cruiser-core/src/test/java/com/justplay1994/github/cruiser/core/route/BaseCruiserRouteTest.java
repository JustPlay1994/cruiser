package com.justplay1994.github.cruiser.core.route;

import org.junit.Test;

import java.net.MalformedURLException;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Package: com.justplay1994.github.cruiser.core.route
 * @Project: cruiser
 * @Creator: huangzezhou
 * @Create_Date: 2019/1/22 21:10
 * @Updater: huangzezhou
 * @Update_Date: 2019/1/22 21:10
 * @Update_Description: huangzezhou 补充
 * @Description:
 **/
public class BaseCruiserRouteTest {

    ThreadPoolExecutor executor = new ThreadPoolExecutor(8,8,100, TimeUnit.MILLISECONDS,new LinkedBlockingDeque<>());

    public void waitFinish(){
        while (!(executor.getActiveCount() == 0 && executor.getQueue().size() == 0)){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void mutexTest(){

        Write write = new Write();

        executor.execute(() -> write.write("aaa"));
        executor.execute(() -> write.write("bbb"));
        waitFinish();
    }

    @Test
    public void getForwardPath() throws MalformedURLException {

        BaseCruiserRouteItem baseCruiserRoute = new BaseCruiserRouteItem("/foo/","http://192.168.1.48/bar/");
        System.out.println(baseCruiserRoute.getForwardUrl("/foo/api"));

    }

    private class Write{

        synchronized private void write(String name){
            for (int i = 0; i < 10; ++i){
                System.out.println(name);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }


    }


}