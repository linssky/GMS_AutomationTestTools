package com.meizu.utils;

import com.meizu.model.TaskInfo;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by wuchaolin on 2017/5/23.
 */
public class CTSThreadHelper {
    protected static BlockingQueue<String> DEVICESLIST = new LinkedBlockingQueue<>();
    protected static List<String> CURRENTDEVICESLIST = new LinkedList<>();
    public static void main(String[] args) {
    }



    /**
     * 根据控制台返回信息解析Task结果
     * @param resultLine 控制台返回信息
     * @return 控制台返回信息包含Task结果则返回，否则返回Null
     */
    public static TaskInfo analysisResultOfAndroid7(String resultLine){
        if (resultLine != null){
            if (resultLine.contains("I/ConsoleReporter:")){
                TaskInfo taskInfo = new TaskInfo();
                String transitionResult = null;
                if (resultLine.contains("fail:")){//用例Fail
                    //e . g . : 03-24 20:33:46 I/ConsoleReporter: [3/17 armeabi-v7a CtsCameraTestCases 792QBEQC22289] android.hardware.camera2.cts.CameraDeviceTest#testCameraDeviceCaptureBurst fail: java.lang.Exception
                    taskInfo.setResult("Fail");//测试结果
                    String[] split1 = resultLine.split("fail:");
                    taskInfo.setDetails(split1[1].trim());//测试结果详情
                    transitionResult = split1[0];
                }else if (resultLine.contains("pass")){//用例Pass
                    taskInfo.setResult("Pass");
                    String[] split1 = resultLine.split("pass");
                    transitionResult = split1[0];
                }else {
                    return null;
                }
                if (transitionResult != null){
                    String[] split2 = transitionResult.split(" ");
                    taskInfo.setIsTested("true");//是否测试
                    taskInfo.setModularName(split2[5]);//模块
                    taskInfo.setBit(split2[4]);//系统位数
                    taskInfo.setClassName(split2[7].split("#")[0]);//类名
                    taskInfo.setMethodName(split2[7].split("#")[1]);//方法名
                }
                return taskInfo;
            }
        }
        return null;
    }

    public TaskInfo analysisResultOfAndroid6(String resultLine){

        return null;
    }
}
