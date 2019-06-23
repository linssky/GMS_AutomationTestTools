package com.meizu.control;

import com.meizu.utils.DevicesUtils;
import com.meizu.utils.SQLiteJDBC;
import com.meizu.utils.exportHistoryBean;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.List;
import java.util.Timer;

/**
 * Created by wuchaolin on 17-11-29.
 */
public class HeartBeatListener implements ServletContextListener {
    private Timer timer = null;

    public void contextInitialized(ServletContextEvent event) {

        //初始化在线设备的状态，全部设置为空闲，任务id为0
        SQLiteJDBC sj=new SQLiteJDBC();
            System.out.println("初始化所有手机状态--------------");
                sj.initSQL("DevicesInfo","1","0");
            System.out.println("初始化所有手机状态完毕--------------");



        // 在这里初始化监听器，在tomcat启动的时候监听器启动，可以在这里实现定时器功能
        timer = new Timer(true);
        System.out.println("启动成功");
        // 添加日志，可在tomcat日志中查看到
            event.getServletContext().log("定时器已启动--------------");
            // 调用exportHistoryBean，0表示任务无延迟，60*1000表示执行间隔时间。
            timer.schedule(new exportHistoryBean(event.getServletContext()), 0, 60 * 1000);
            event.getServletContext().log("已经添加任务-------------");

    }

    // 在这里关闭监听器，所以在这里销毁定时器。
    public void contextDestroyed(ServletContextEvent event) {
        timer.cancel();
        event.getServletContext().log("定时器销毁---------------");
    }

}
