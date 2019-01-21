package com.justplay1994.github.cruiser.core.exception;

/**
 * @Package: com.justplay1994.github.cruiser.core.exception
 * @Project: cruiser
 * @Creator: huangzezhou
 * @Create_Date: 2019/1/17 11:26
 * @Updater: huangzezhou
 * @Update_Date: 2019/1/17 11:26
 * @Update_Description: huangzezhou 补充
 * @Description:
 **/
public class RouteException extends Exception {

    //无参构造方法
    public RouteException(){

        super();
    }

    //有参的构造方法
    public RouteException(String message){
        super(message);

    }

    // 用指定的详细信息和原因构造一个新的异常
    public RouteException(String message, Throwable cause){

        super(message,cause);
    }

    //用指定原因构造一个新的异常
    public RouteException(Throwable cause) {

        super(cause);
    }

}
