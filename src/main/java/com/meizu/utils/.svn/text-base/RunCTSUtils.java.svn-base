package com.meizu.utils;

import com.meizu.model.DevicesInfo;
import com.meizu.model.FilePathInfo;

import java.util.Calendar;
import java.util.List;

/**
 * Created by wuchaolin on 17-6-6.
 */
public class RunCTSUtils {

    /**1是结果路径，2是fail数量，3是已跑模块，4是总模块*/
    public String[]  GoRun(String SN, String arm, String XTS, String commod,String taskId){
        String sn=SN.trim();
        String[] resultsPath;

        if (commod==null||commod.trim().equals("")||commod.toLowerCase().trim().equals("no")){
            resultsPath=runAllCase(sn,arm,XTS,taskId);
        }else {
            resultsPath=runOneCase(sn,arm,XTS,commod);
        }
        return resultsPath;
    }


    /**1是结果路径，2是fail数量，3是已跑模块，4是总模块.5是pass总数
     * 如果跑失败了，则1是crash，2是错误提示
     * */
    public String[] runAllCase(String SN,String arm,String xts,String taskId){
        FilePathUtils FP=new FilePathUtils();
        FilePathInfo fp=FP.getFilePathInfo(SN,xts);
        String runfile=fp.getRunXtsPath() +" run " + xts.toLowerCase().trim() ;
        String commond="NO Run";

        DevicesUtils DU=new DevicesUtils();
        List<DevicesInfo> du=DU.getDevicesInfo(SN);
        int length=du.size();

        String[] rName = new String[5];
        if (length>0) {//有设备在线才跑
            if (SN.toLowerCase().trim().equals("all")) {//跑整体
//        if (!haveSN(SN)) {//跑整体
                if (arm.toLowerCase().trim().equals("64")) {
                    commond = runfile + " --shards " + length + " -a arm64-v8a";
                }
                if (arm.toLowerCase().trim().equals("32")) {
                    commond = runfile + " --shards " + length + " -a armeabi-v7a";
                }
                if (arm.toLowerCase().trim().contains("all")) {
                    commond = runfile + " --shards " + length;
                }
            } else {
                if (haveSN(SN)) {//该设备在线才跑
                    if (arm.toLowerCase().trim().equals("64")) {
                        commond = runfile + " -s " + SN.trim() + " -a arm64-v8a";
                    }
                    if (arm.toLowerCase().trim().equals("32")) {
                        commond = runfile + " -s " + SN.trim() + " -a armeabi-v7a";
                    }
                    if (arm.toLowerCase().trim().contains("all")) {
                        commond = runfile + " -s " + SN.trim();
                    }
                }else {//sn号不在线
                    rName[0]="crash";
                    rName[1]="设备："+SN+"不在线,"+xts.toUpperCase()+"测试中断，请检查手机连接状态！";
                    return rName;
                }
            }
        }else {//没有在线手机
            rName[0]="crash";
            rName[1]="该电脑无在线手机,"+xts.toUpperCase()+"测试中断，请检查手机连接状态！";
            return rName;
        }
            rName = ADBUtils.getGmsResult(commond);

        return rName;
    }

    /**1是结果路径，2是fail数量，3是已跑模块，4是总模块
     * */
    public String[] runOneCase(String SN,String arm,String xts,String Commond){
        FilePathUtils FP=new FilePathUtils();
        FilePathInfo fp=FP.getFilePathInfo(SN,xts);

        String commond="NO Run";
        String module = null;
        String[] cm;
        if (!Commond.contains("#")){
            module=" -m "+Commond.trim();
        }else {
            cm=Commond.split("#");
            if (cm.length == 2) {
                module = " -m " + cm[0].trim() + " -t " + cm[1].trim();
            }
            if (cm.length == 3) {
                module = " -m " + cm[0].trim() + " -t " + cm[1].trim() + "#" + cm[2].trim();
            }
        }
        String runfile=fp.getRunXtsPath() +" run " + xts.toLowerCase().trim() +module;

        DevicesUtils DU=new DevicesUtils();
        List<DevicesInfo> du=DU.getDevicesInfo(SN);
        int length=du.size();

        String[] rName = new String[5];
        if (length>0) {//有设备在线才跑
            if (SN.toLowerCase().trim().equals("all")) {//跑整体
//        if (!haveSN(SN)) {//跑整体
                if (arm.toLowerCase().trim().equals("64")) {
                    commond = runfile + " --shards " + length + " -a arm64-v8a";
                }
                if (arm.toLowerCase().trim().equals("32")) {
                    commond = runfile + " --shards " + length + " -a armeabi-v7a";
                }
                if (arm.toLowerCase().trim().contains("all")) {
                    commond = runfile + " --shards " + length;
                }
            } else {
                if (haveSN(SN)) {//该设备在线才跑
                    if (arm.toLowerCase().trim().equals("64")) {
                        commond = runfile + " -s " + SN.trim() + " -a arm64-v8a";
                    }
                    if (arm.toLowerCase().trim().equals("32")) {
                        commond = runfile + " -s " + SN.trim() + " -a armeabi-v7a";
                    }
                    if (arm.toLowerCase().trim().contains("all")) {
                        commond = runfile + " -s " + SN.trim();
                    }
                } else {//该设备不在线
                    rName[0]="crash";
                    rName[1]="设备："+SN+"不在线,"+xts.toUpperCase()+"测试中断，请检查手机连接状态！";
                    return rName;
                }

            }
        }else {//没有在线设备
            rName[0]="crash";
            rName[1]="该电脑无在线手机,"+xts.toUpperCase()+"测试中断，请检查手机连接状态！";
            return rName;
        }


            rName = ADBUtils.getGmsResult(commond);
        return rName;
    }


    /**传入的SN是否存在
     * 返回true表示存在*/
    public boolean haveSN(String SN){
        boolean HaveSN=false;
        if (SN!=null) {
            List<String> devices=DevicesUtils.getDevicesSN();
            if (devices != null){
                for (String serialNumber : devices){
                    if (serialNumber.trim().equals(SN)){
                        HaveSN=true;
                        break;
                    }
                }
            }
        }
        return HaveSN;
    }

    /**全跑时候检测是否所有在线手机都是同一型号
     *  * 返回true表示存在*/
    public boolean isSanmeModel(){
        boolean isSame=true;//默认是一致的
        DevicesUtils devicesUtils=new DevicesUtils();
        List<DevicesInfo> devicesUtilses=devicesUtils.getDevicesInfo("All");

            if (devicesUtilses!= null){
                int length=devicesUtilses.size();
                if (length>2) {
                    for (int i=0;i<length-1;i++) {//遍历所有在线手机的型号是否一致
                        if (!devicesUtilses.get(i).getDevicesModel().toString().trim().equals(devicesUtilses.get(i+1).getDevicesModel().toString().trim())) {
                            isSame = false;
                            break;
                        }
                    }
                }else {
                    if (length==2){//如果只有两台
                        if (!devicesUtilses.get(0).getDevicesModel().toString().trim().equals(devicesUtilses.get(1).getDevicesModel().toString().trim())) {
                            isSame = false;
                        }
                    }
                }
            }
        return isSame;
    }

    /**获取seesionID
     **/
    public String getSeesionId(String SN,String xts,String  ResultDirectory ){
        FilePathUtils FP=new FilePathUtils();
        FilePathInfo fp=FP.getFilePathInfo(SN,xts);
        String lr=fp.getRunXtsPath()+" l r";
        String seesionId=ADBUtils.getCommandSeesionId(lr,ResultDirectory);
        return seesionId;
    }


    /**retry
     * 检测有SIM卡的手机进行retry，若都没有SIM则retry全部手机
     * 返回结果文件名*/
    public String[] retryCts(String SN,String seesionNum,String xts){
        DevicesUtils devicesUtils=new DevicesUtils();
        final List<DevicesInfo> devicesUtilses=devicesUtils.getDevicesInfo(SN);

        int DeviceNum=devicesUtilses.size();

        String serNum=null;
       for(int j=0;j<DeviceNum;j++){
            if(devicesUtilses.get(j).getSim().trim().equals("1")){
                serNum=devicesUtilses.get(j).getSerialNumber();
                System.out.println(serNum+"有SIM卡");
                break;
            }
        }

        FilePathUtils FP=new FilePathUtils();
        FilePathInfo fp=FP.getFilePathInfo(SN,xts);
        String runFile=fp.getRunXtsPath();

        String[] rName;
        if(!SN.toLowerCase().trim().equals("all")){
            String commond = runFile+" run "+xts+" --retry "
                    +seesionNum+" -s " + SN.toString().trim();
           rName=ADBUtils.getGmsResult(commond);
        }else {//否则就是跑全部的
            if (serNum!=null) {//有SIM卡的则跑SIM卡
                String commond = runFile+" run "+xts+" --retry "+seesionNum+" -s "+serNum;
                rName = ADBUtils.getGmsResult(commond);
            }else {//没有则retry全部
                String commond = runFile+" run "+xts+" --retry "+seesionNum;
                rName = ADBUtils.getGmsResult(commond);
            }
        }
        return rName;
    }

    /**retry的逻辑：如果失败项目大于100，或者模块有没完成的，就retry，最多三次
     * 1是结果路径，2是fail数量，3是已跑模块，4是总模块,5是pass数量**/
    public String[] RetryXts(String SN,String[] results,String xts) throws Exception {
        String[] ss = results;
            try {
                int failNum= Integer.parseInt(ss[1]);
                int runModule= Integer.parseInt(ss[2]);
                int totalModule= Integer.parseInt(ss[3]);
                int a=0;
                if (failNum!=0) {
                    out:
                    while (failNum > 50 || (runModule < totalModule)) {
                        String retryID=getSeesionId(SN,xts,ss[0]);
                        ss = retryCts(SN, retryID, xts);
                        failNum = Integer.parseInt(ss[1]);
                        runModule = Integer.parseInt(ss[2]);
                        totalModule = Integer.parseInt(ss[3]);
                        if (a > 2) {
                            break out;
                        }
                        a++;
                    }
                }
            } catch (Exception e){
//                throw new Exception(ss[0]);//如果失败了，则直接抛出出入的路径
            }
        return ss;
    }

    public void sendResult(){
        String commond = "./copyResult.sh";
        ADBUtils.getCommandResult(commond);
    }






}
