package com.meizu.utils;

import com.meizu.model.DevicesInfo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by wuchaolin on 17-11-14.
 */
public class UpdateHelper {



    public String[] goUpdate(String SN,String firemwareRoute,String updateVersion,String taskId) throws IOException, InterruptedException {
        if (updateVersion.contains("_")){
            updateVersion=updateVersion.split("_")[0];
        }
        String[] aa=new String[2];
        aa[0]="yes";
                if(!firemwareRoute.contains(updateVersion)) {//当固件路径不包含手机固件地址,则刷固件
                    boolean isok = pushUpdate(SN, firemwareRoute);
                    if (isok) {//当升级包push到手机成功时候才进行升级
                        batteryIsReady(SN);
                        WebServiceBean.sendStutaPost(taskId,"2","设备："+SN+"固件升级中！");//发送状态给服务器
                        installUpdateApk(SN);
                        isUpdateOk(SN);
                        isUpdateOk(SN);//后面加的两次是针对1891B2机器升级固件后重启问题的，此问题一解就删除这两行！！
                        isUpdateOk(SN);
                    } else {
                        aa[0]="no";
                        aa[1]="固件拷贝失败，请检查输入的固件路径中是否包含update.zip文件！";
                        System.out.println("update升级包拷贝失败！");
                    }
                }else {
                    System.out.println("设备"+SN+"的固件版本和要刷的版本一致！不需要重复刷固件");
                }
        return aa;
    }



/**@param SN SN号
 * @param firmwareRoute 传入的固件地址
 * **/
    public boolean pushUpdate(String SN,String firmwareRoute) throws IOException {
        boolean isok=false;
        String dangqian=ADBUtils.getCommandResult("pwd").trim();
        if (firmwareRoute.contains("DailyBuild4Test")) {
            String[] listR = firmwareRoute.split("DailyBuild4Test");

            String path00="";

            if(listR[1].contains("\\")) {
                System.out.println("包含反斜杠");
                try {
                    String pathLoad[] = listR[1].split("\\\\");
                    int pathSize = pathLoad.length;
                    for (int i = 0; i < pathSize; i++) {
                        if (i != 0) {
                            path00 = path00 + File.separator + pathLoad[i];
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else {
                path00=listR[1];
            }
            String path=dangqian+File.separator+"GMSTools"+File.separator+"mount"+File.separator+
                    "172.16.1.98.firmware"+File.separator+"DailyBuild4Test" + path00;
            System.out.println("固件完整路径是："+path);
            String aa= null;
            try {
                aa = GetCanonicalPath(path,"update.zip");
            } catch (Exception e) {
                aa=e.getMessage();//跳出递归的方法，true的时候将需要的值以异常抛出，然后在这里获取
                System.out.println("抛出的异常是："+aa);
            }
            if (aa!=null) {
                System.out.println("设备：" + SN + "正在拷贝固件！");
                ADBUtils.getCommandResult("adb -s " + SN + " push " + aa + " /sdcard/update.zip");
                isok=true;
                System.out.println("adb -s " + SN + " push " + aa + " /sdcard/" + "\n设备：" + SN + "固件拷贝完成！");
            }else{
                System.out.println("未找到update包！");
            }
        }
        return isok;
    }



    /**@param SN 设备号
     *安装apk并出发升级指令，在固件拷贝完毕后执行
     * */

    public static void installUpdateApk(final String SN) throws InterruptedException {
        PhoneReadyHelper.isStatusBarKeyguard(SN);
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    for (int i=0;i<90;i++) {
//                        Thread.sleep(1000 * 1);
//                        ADBUtils.getCommandResult("adb -s " + SN.trim() + " shell input keyevent 3");
//                    }
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();

        ADBUtils.getCommandResult("adb -s "+SN+" install -r ."+ File.separator+"GMSTools"+File.separator+"apk"+File.separator+"CtsPreconditions.apk");//安装CTS测试准备的apk，来关闭手管的usb开关
        ADBUtils.getCommandResult("adb -s "+SN+" install -r ."+ File.separator+"GMSTools"+File.separator+"apk"+File.separator+"Compatibilityinit_prd.apk");
        ADBUtils.getCommandResult("adb -s "+SN+" shell am start com.meizu.testdev.compatibilityinit/.MainActivity");
        Thread.sleep(5000);
        ADBUtils.getCommandResult("adb -s "+SN+" shell am broadcast -a com.flyme.action.UPDATE_SYSTEM");
        Thread.sleep(5000);
        System.out.println("设备："+SN+"正在进行固件升级");


    }

//跳出递归的方法
    static class StopMsgException extends RuntimeException {
    }
    /**@param path 指定的路径
     * @param FileName 文件的全名（带后缀）
     * @return 指定目录下的指定文件的绝对路径（如果有多个同名文件则选择第一个）
     * */
    public String  GetCanonicalPath(String path,String FileName) throws Exception {
        String CanonicalPath = null;
        File result = new File(path);
        File list[] = result.listFiles();

        out: for (File filename : list) {
            if (filename.isDirectory()) {
                CanonicalPath=GetCanonicalPath(path + File.separator + filename.getName(),FileName);//递归直到找出testResult.xml文件
            }else {
                if (filename.getName().trim().equals(FileName)){
                        CanonicalPath=filename.getCanonicalPath().trim();
                        System.out.println(filename.getCanonicalPath());
                    throw new Exception(CanonicalPath);
                }
            }
        }
        System.out.println("-=-=-=-=固件地址是："+CanonicalPath);
        return CanonicalPath;
    }

    public void batteryIsReady(String SN) throws InterruptedException {
        System.out.println("判断手机电量");
        DevicesUtils devicesUtils=new DevicesUtils();
        List<DevicesInfo> devicesUtilses=devicesUtils.getDevicesInfo(SN);
        int bat=0;
            bat = devicesUtilses.get(0).getBattery();
        System.out.println("设备"+SN+"当前电量为：" + bat + "%");
        int i = 0;
        while (bat < 25) {
            if (i > 100) {
                break;
            }
            Thread.sleep(1000 * 60);
            try {
                bat = devicesUtils.getDevicesInfo(SN).get(0).getBattery();
            }catch (Exception e){}
            System.out.println("设备："+SN+"的当前电量为：" + bat + "%   ,电量不足25%，等待充电到25%后进行固件升级");
            i++;
        }
    }

    public void isUpdateOk(String SN) throws InterruptedException {
        Thread.sleep(1000*60);
//        ADBUtils.getCommandResult("adb wait-for-device");
        int i=0;
        String isDestTop="";
        while (!(isDestTop.contains("com.meizu.flyme.launcher.Launcher")
                ||isDestTop.contains("com.flyme.systemuitools.wellcomepage.WellcomePage")
                ||isDestTop.contains("com.meizu.setup.activity.SetupLanguageActivity"))){
            if(i>180){
                break;
            }
            isDestTop=ADBUtils.getCommandResult("adb -s "+SN+" shell dumpsys SurfaceFlinger --list");
            Thread.sleep(1000*10);
            i++;
        }
        Thread.sleep(1000*10);
    }

}
