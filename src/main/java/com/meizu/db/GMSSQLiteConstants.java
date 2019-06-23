package com.meizu.db;

/**
 * Created by wuchaolin on 2018/1/13.
 */
public class GMSSQLiteConstants {

    public static final String GMS_TASK_INFO = "CREATE TABLE IF NOT EXISTS GMSTaskInfo (" +
            "taskID integer primary key autoincrement," +   //任务ID
            "taskName varchar(100) UNIQUE, " +              //任务名
            "taskResultFilePath varchar(100), " +           //任务结果保存路径
            "taskType varchar(100), " +                     //任务类型
            "taskModularTotal integer, " +                  //模块总数
            "taskTestedModularTotal integer, " +            //已跑模块总数
            "taskMethodTotal integer, " +                   //case总数
            "taskPassTotal integer, " +                     //pass总数
            "taskFailTotal integer, " +                     //fail总数
            "taskStartTime varchar(100), " +                 //任务启动时间
            "taskFinishTime varchar(100), " +                //任务完成时间
            "isTaskTested integer " +                       //任务是否测试完成
            ");";

    public static final String GMS_TASK_RESULT_INFO = "CREATE TABLE IF NOT EXISTS GMSTaskResultInfo (" +
            "taskID integer primary key autoincrement," +   //任务ID
            "taskName varchar(100) UNIQUE, " +              //任务名
            "modularName varchar(100), " +                 //模块名
            "className varchar(100), " +                   //类名
            "methodName varchar(100), " +                   //方法名
            "armBit varchar(10), " +                          //测试系统位数
            "result varchar(10), " +                       //测试结果
            "details varchar(500), " +                     //测试结果详情
            "isTested integer " +                          //是否已被测试
            ");";
}
