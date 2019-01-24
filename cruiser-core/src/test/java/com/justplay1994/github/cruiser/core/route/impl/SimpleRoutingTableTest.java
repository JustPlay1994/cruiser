package com.justplay1994.github.cruiser.core.route.impl;

import org.junit.Test;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import static org.junit.Assert.*;

/**
 * @Package: com.justplay1994.github.cruiser.core.route.impl
 * @Project: cruiser
 * @Creator: huangzezhou
 * @Create_Date: 2019/1/23 20:50
 * @Updater: huangzezhou
 * @Update_Date: 2019/1/23 20:50
 * @Update_Description: huangzezhou 补充
 * @Description:
 **/
public class SimpleRoutingTableTest {

    @Test
    public void match() {
        PathMatcher pathMatcher = new AntPathMatcher();
        System.out.println("1:"+pathMatcher.match("/futian","/futian"));
        System.out.println();
    }
}