package com.meizu.utils;

import javax.servlet.ServletContext;
import java.util.Calendar;
import java.util.TimerTask;

/**
 * Created by wuchaolin on 17-11-29.
 */
public class exportHistoryBean extends TimerTask {
    private static final int C_SCHEDULE_HOUR = 0;
    private static boolean isRunning = false;
    private ServletContext context = null;

    public exportHistoryBean(ServletContext context) {
        this.context = context;
    }

    public void run() {
        isRunning = true;
        context.log("开始执行指定任务");
        try {
            new WebServiceBean().doWork();// 这里就是调用自己的方法了
        }catch (Exception e){
            System.out.println("定时器执行异常！获取所有设备状态出错！");
        }

        isRunning = false;
        context.log("指定任务执行结束");

    }
}