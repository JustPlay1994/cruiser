package com.justplay1994.github.cruiser.core.route;

/**
 * @Package: com.justplay1994.github.cruiser.core.route
 * @Project: cruiser
 * @Creator: huangzezhou
 * @Create_Date: 2019/1/17 10:26
 * @Updater: huangzezhou
 * @Update_Date: 2019/1/17 10:26
 * @Update_Description: huangzezhou 补充
 * @Description:
 **/

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * 路由项基类
 */
@Data
@NoArgsConstructor
public class BaseCruiserRouteItem {

    //====== base properties ==================

    URL proxy_pass;
    String location;

    // ========== front properties ===============
//    String frontMaxConnection;

    // ========== back properties ================
//    String backMaxConnection;

    public BaseCruiserRouteItem(String location, String proxy_pass) throws MalformedURLException {
        this.location = location;
        this.proxy_pass = new URL(proxy_pass);
    }

    public void updateAll(BaseCruiserRouteItem baseCruiserRoute){
        this.proxy_pass = baseCruiserRoute.getProxy_pass();
        this.location = baseCruiserRoute.getLocation();
    }

    /**
     * 将请求url转换为完整的转发url地址
     * 参考nginx proxy_pass 设计
     *
     * 1. proxy_pass 为 ip:port 后面没有任何字符时，将整个requestUrl直接加到proxy_pass之后，传递给后端
     * 2. proxy_pass 为 ip:port/ 后面有字符时，会将requestPath中的前缀location删除(替换为空)，然后加到proxy_pass之后，传递给后端
     *
     * 表一
     *
     * 案例	location	proxy_pass	        结果
     * 1	/foo/	http://192.168.1.48/	/api
     * 2	/foo	http://192.168.1.48/	//api
     * 3	/foo/	http://192.168.1.48 	/foo/api
     * 4	/foo	http://192.168.1.48	    /foo/api
     * 表二
     *
     * 案例	location	proxy_pass	            结果
     * 5	/foo/	http://192.168.1.48/bar/	/bar/api
     * 6	/foo	http://192.168.1.48/bar/	/bar//api
     * 7	/foo/	http://192.168.1.48/bar	    /barapi
     * 8	/foo	http://192.168.1.48/bar	    /bar/api
     * @param requestPath
     * @return
     */
    public URL getForwardUrl(String requestPath) throws MalformedURLException {
        String forward;
        if (StringUtils.isEmpty(proxy_pass.getPath())){
            forward = proxy_pass+requestPath;
        }else {
            forward = proxy_pass+requestPath.replace(location,"");
        }

        return new URL(forward);
    }
}
