package com.meizu.utils;

import com.meizu.model.DevicesInfo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by wuchaolin on 17-6-6.
 */
public class PhoneReadyHelper {
//    Logger log = Logger.getLogger(Class.forName("CTS_Tool_Log"));


//数组1是是否成功，2是失败才会有的返回状态
    public String[] PhoneReady(final String SN, final String UpdateFirmware, final String XTS, final String taskId) throws IOException, InterruptedException {
       String[] fa=new String[2];
        fa[0]="yes";//默认配置成功

        final DevicesUtils devicesUtils=new DevicesUtils();
        final List<DevicesInfo> devicesUtilses=devicesUtils.getDevicesInfo(SN);
        final UpdateHelper up=new UpdateHelper();
            final int PhoneNum=devicesUtilses.size();
            System.out.println("手机数量是："+PhoneNum);

            ExecutorService pool = Executors.newCachedThreadPool();
            ArrayList<Callable<Integer>> callers = new ArrayList<>();//创建线程池，此池等待所有任务完成后才会继续



        final int[] aa=new int[PhoneNum];
        final String[] bb=new String[PhoneNum];
            for (int i = 0; i < PhoneNum; i++) {
                String deviceSN = devicesUtilses.get(i).getSerialNumber();
                final String updateVersion=devicesUtilses.get(i).getUpdateVersion().split("-")[1].trim();//固件版本的数字
                final String sn=deviceSN;
                final String uv;
                if (updateVersion.contains("_")){
                    uv=updateVersion.split("_")[0];
                }else {
                    uv=updateVersion;
                }
                final int finalI = i;
                callers.add(new Callable<Integer>() {
                    @Override
                    public Integer call() throws Exception {
                        try {
                            //CTS环境配置
                            if (UpdateFirmware!=null&&UpdateFirmware.contains("DailyBuild4Test")) {//如果传入的路径包含DailyBuild4Test则继续
                                if (PhoneNum==1) {//如果是一台手机
                                    WebServiceBean.sendStutaPost(taskId, "2", XTS.toUpperCase() + "固件升级中！");//发送状态给服务器
                                }
                                String[] upok=up.goUpdate(sn.trim(),UpdateFirmware,uv,taskId);
                                if (upok[0].trim().equals("yes")) {//固件拷贝成功才会执行
                                    String newVersion = devicesUtils.getDevicesInfo(sn).get(0).getUpdateVersion().split("-")[1].trim();//分割版本时间戳
                                    if (newVersion.contains("_")) {
                                        newVersion = newVersion.split("_")[0].trim();
                                    }
                                    System.out.println("设备：" + sn + "升级固件后的版本是：" + newVersion);
                                    if (UpdateFirmware.contains(newVersion.toString())) {//如果刷完以后固件版本和触发版本一致，则往下走
                                        System.out.println("设备" + sn + "升级固件完成！");
                                        if (PhoneNum == 1) {
                                            WebServiceBean.sendStutaPost(taskId, "3", "配置手机环境中！");//发送状态给服务器
                                        }
                                        ready(sn, UpdateFirmware, XTS, uv);
                                    } else {//固件升级完成后与所发版本不一致
                                        System.out.println("设备：" + sn + "固件升级失败，请检查固件路径是否正确，或者手机是否支持update升级！");
                                        aa[finalI] = 1;
                                        bb[finalI]="固件升级失败，请检查固件路径是否正确，或者手机是否支持update升级！";
                                    }
                                }else {//如果固件拷贝失败
                                    aa[finalI] = 1;
                                    bb[finalI]=upok[1]; //要发送给服务器的状态
                                }
                            }else {//如果传入路径不包含DailyBuild4Test
                                if (PhoneNum == 1) {
                                    WebServiceBean.sendStutaPost(taskId, "3", "手机环境配置中！");//发送状态给服务器
                                }
                                ready(sn, UpdateFirmware, XTS, uv);
                            }
                        } catch (Exception e) {
                            System.out.println("环境配置中断！！！");
                        }
                        return null;
                    }
                });

            }
            pool.invokeAll(callers);
            pool.shutdown();
       out: for (int i=0;i<aa.length;i++){
            if (aa[i]==1){
                fa[0]="no";
                fa[1]=bb[i];
                break out;
            }
        }
        Thread.sleep(1000*10);
        return fa;
    }


    //updateVersion是用来检查固件版本是否和要刷的版本是一样的，如果是就不用刷了
    public void ready(String SN,String UpdateFirmware,String XTS,String updateVersion) throws IOException, InterruptedException {



        if(XTS.toLowerCase().trim().equals("cts")) {
            System.out.println("设备" + SN + "正在拷贝媒体文件！");
            PhoneReadyHelper.CopyMedia(SN);
            System.out.println("设备" + SN + "媒体文件拷贝完成！");
        }

        System.out.println("设备"+SN+"正在配置手机环境！");
        PhoneReadyHelper.installReadyApk(SN);

//        System.out.println("设备"+SN+"正在重启手机！");
//        reboot(SN);
//        System.out.println("设备"+SN+"环境配置已经完成");
    }


    //判断手机是否有媒体包，规则是存在test文件夹且test大于3G
    public static boolean haveMediaFile(String SN){
        boolean isit=false;
        String have=ADBUtils.getCommandResult("adb -s "+SN.trim()+" shell du -m /sdcard/test/");//察看是否有test文件夹以及其大小
        if(!have.contains("No such file or directory")){
            try{
                String size="0";
                String[] a1=have.split("/sdcard/test/");//bbb_short
                int b1=a1.length;
                if (b1>1) {
                    String[] a2 = a1[b1 - 2].split("\n");
                    int b2 = a2.length;
                    size=a2[b2-1].trim();
                }else {
                    if (b1==1){
                        String a2 = a1[0].trim();
                        size=a2;
                    }
                }
                int Size1= Integer.parseInt(size);
            if (Size1>2000){
                isit=true;
            }
            }catch (Exception e){

            }

        }

        return isit;
    }

    public static void CopyMedia(String SerialNumber){
        if (!haveMediaFile(SerialNumber)){
        ADBUtils.getCommandResult("."+ File.separator+"GMSTools"+File.separator+"copy_media.sh all -s "+SerialNumber.trim());
        }else {
            System.out.println("设备："+SerialNumber+"已经有媒体包，不需要重复拷贝");
        }
    }


    //是否为解锁到桌面状态
    public static void isStatusBarKeyguard(String SN){
        String results=ADBUtils.getCommandResult("adb -s "+SN.trim()+" shell dumpsys window policy|grep isStatusBarKeyguard");
        if (!results.contains("false")){//false表示已经在桌面
            ADBUtils.getCommandResult("adb -s "+SN+" shell input keyevent 82");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ADBUtils.getCommandResult("adb -s "+SN+" shell input keyevent 82");
        }
    }
    /**安装环境配置APK进行手机环境配置
     * **/
    public static void installReadyApk(final String SerialNumber) throws InterruptedException {
        isStatusBarKeyguard(SerialNumber);
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    for (int i=0;i<8;i++) {
//                        Thread.sleep(1000 * 1);
//                        ADBUtils.getCommandResult("adb -s " + SerialNumber + " shell input keyevent 3");
//                    }
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();

        ADBUtils.getCommandResult("adb -s "+SerialNumber+" install -r ."+File.separator+"GMSTools"+File.separator+"apk"+File.separator+"CtsPreconditions.apk");//安装CTS测试准备的apk，来关闭手管的usb开关


        ADBUtils.getCommandResult("adb -s "+SerialNumber+" install -r ."+File.separator+"GMSTools"+File.separator+"apk"+File.separator+"Compatibilityinit_prd.apk");//安装环境配置apk

        Thread.sleep(1000*3);
        ADBUtils.getCommandResult("adb -s "+SerialNumber+
                " shell am start com.meizu.testdev.compatibilityinit/.MainActivity");//启动
        Thread.sleep(1000*5);
        ADBUtils.getCommandResult("adb -s "+SerialNumber+
                " shell am broadcast -a com.flyme.action.END_SECOND_SCREEN_WELCOME");//关闭副屏广播
        Thread.sleep(1000);

        ADBUtils.getCommandResult("adb -s "+SerialNumber+
                " shell am broadcast -a com.flyme.action.CTS_SETTING");//CTS环境初始化
        Thread.sleep(1000);
        ADBUtils.getCommandResult("adb -s "+SerialNumber+" install -r ."+File.separator+"GMSTools"+File.separator+"apk"+File.separator+"CtsAbiOverrideTestApp.apk");//安装CTS测试准备的apk，来关闭手管的usb开关

        Thread.sleep(1000*5);
        ADBUtils.getCommandResult("adb -s "+SerialNumber+
                " uninstall com.meizu.testdev.compatibilityinit");//卸载环境配置apk

        ADBUtils.getCommandResult("adb -s "+SerialNumber+
                " uninstall android.abioverride.app");//卸载ctsapk2


        System.out.println("设备"+SerialNumber+"环境配置完成！");

    }

    public void reboot(String SN) throws InterruptedException {
        ADBUtils.getCommandResult("adb -s "+SN+" reboot");
        Thread.sleep(1000*15);
        int i=0;
        String isDestTop="";
        while (!(isDestTop.contains("com.meizu.flyme.launcher.Launcher")
                ||isDestTop.contains("com.meizu.setup.activity.SetupLanguageActivity"))){
            if(i>60){
                break;
            }
            isDestTop=ADBUtils.getCommandResult("adb -s "+SN+" shell dumpsys SurfaceFlinger --list");
            Thread.sleep(1000*5);
            i++;
        }
        Thread.sleep(1000*30);
        System.out.println("设备"+SN+"重启完成！");
    }

}
