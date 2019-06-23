package com.meizu.utils;

import com.meizu.model.DevicesInfo;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
/**
 * Created by wuchaolin on 2017/5/17.
 */
public class DevicesUtils {
//
//    public static void main(String[] args) {
//        System.out.println(new DevicesUtils().getDevicesInfo(null).size());
//    }



    /**获取所有设备的信息
     * SN是null的时候获取所有设备信息，否则获取对应SN号的信息
     * */
    public List<DevicesInfo> getDevicesInfo(String SN){

        RunCTSUtils rc=new RunCTSUtils();
        //1、获取所有设备序列号
        List<String> devicesSerialNumber = DevicesUtils.getDevicesSN();
        //2、通过序列号循环获取手机信息
        List<DevicesInfo> devicesInfoList = new ArrayList<>();
        if(!rc.haveSN(SN)){//如果没有传入有效SN号，则遍历全部手机
            if (devicesSerialNumber != null){
                for (String serialNumber : devicesSerialNumber){
                    devicesInfoList.add(this.getSingleDevicesInfo(serialNumber.trim()));
                }
            }
        }else {//否则读取对应SN号手机
            devicesInfoList.add(this.getSingleDevicesInfo(SN.trim()));
        }
        return devicesInfoList;
    }

    private DevicesInfo getSingleDevicesInfo(String serialNumber){
        //1、通过序列号获取设备信息
        DevicesInfo devicesInfo = new DevicesInfo();
        String propMessage = this.getPropMessage(serialNumber);
        devicesInfo.setSerialNumber(serialNumber);//SN号
        devicesInfo.setBattery(this.getBattery(serialNumber));//电量
        devicesInfo.setGcc(this.getDevicesGcc(serialNumber));//内核
        if (propMessage != null){
            devicesInfo.setAndroidVersion(this.getAndroidVersion(propMessage));//Android版本
            devicesInfo.setDevicesImei(this.getDevicesImei(propMessage));//imei
            devicesInfo.setUpdateVersion(this.getUpdateVersion(propMessage));//固件版本
            devicesInfo.setGsm(this.getDevicesGsm(propMessage));//基带
            devicesInfo.setDevicesModel(this.getDevicesModel(propMessage));//机型
            devicesInfo.setSim(this.getIsSim(propMessage));//是否有sim卡
        }
        return devicesInfo;
    }

    private String getDevicesImei(String getPropMessage) {
        String startReg = "\\[ril.gsm.imei]: \\[";
        String overReg = "]";
        String imei=ADBUtils.getCentreStringBySplit(getPropMessage, startReg, overReg);
        if (imei!=null) {
            if (imei.contains(",")) {
                imei = imei.split(",")[0].trim();
            }
        }else {
            imei="null";
        }
        return imei;
    }


    private String getPropMessage(String serialNumber){
        String command = "adb -s " + serialNumber + " shell getprop";
        return ADBUtils.getCommandResult(command);
    }

    //获取所有设备序列号
    public static List<String> getDevicesSN() {
        String result = ADBUtils.getCommandResult("adb devices");
        if (result != null){
            List<String> devicesSNList = new LinkedList<>();
            String[] devicesIDArray = result.split(System.getProperty("line.separator", "\n"));
            for (String devicesID : devicesIDArray) {
                if (!devicesID.contains("devices") && devicesID.contains("device")){
                    devicesSNList.add(devicesID.split("device")[0].trim());
                }
            }
            return devicesSNList;
        }
        return null;
    }

    //获取手机安卓版本信息
    private String  getAndroidVersion(String getPropMessage){
        String startReg = "\\[ro.build.version.release]: \\[";
        String overReg = "]";
        return ADBUtils.getCentreStringBySplit(getPropMessage, startReg, overReg);
    }

    //获取手机固件版本信息
    private String  getUpdateVersion(String getPropMessage){
        String startReg = "\\[ro.build.inside.id]: \\[";
        String overReg = "]";
        return ADBUtils.getCentreStringBySplit(getPropMessage, startReg, overReg);
    }

    //获取手机基带信息
    private String getDevicesGsm(String getPropMessage) {
        String startReg = "\\[gsm.version.baseband]: \\[";
        String overReg = "]";
        return ADBUtils.getCentreStringBySplit(getPropMessage, startReg, overReg);
    }

    //获取手机型号
    private String getDevicesModel(String getPropMessage){
        String s="\\[ro.product.name]: \\[";
        String startReg = "\\[ro.product.mobile.name]: \\[";
        String ss="\\[ro.product.flyme.model]: \\[";
        String overReg = "]";
        String aa = null;
        String a1=ADBUtils.getCentreStringBySplit(getPropMessage, startReg, overReg);
        if(a1!=null&&!a1.equals("")){
            aa=ADBUtils.getCentreStringBySplit(getPropMessage, startReg, overReg);
        }
        String a2=ADBUtils.getCentreStringBySplit(getPropMessage, s, overReg);
        if (a2!=null&&!a2.equals("")){
            aa=a2;
        }
        String a3=ADBUtils.getCentreStringBySplit(getPropMessage, ss, overReg);
        if (a3!=null&&!a3.equals("")){//a3是内部机型型号
            aa=a3;
        }
        return aa;
    }

    //获取手机内核信息
    private String getDevicesGcc(String serialNumber) {
        return ADBUtils.getCommandResult("adb " + "-s " + serialNumber + " shell cat /proc/version");
    }

    //获取当前电量
    private int getBattery(String serialNumber){
        int batt=0;
        String  batteryMessage = ADBUtils.getCommandResult("adb " + "-s " + serialNumber + " shell dumpsys battery");
        if (batteryMessage != null && batteryMessage.contains("level:")){
            String[] split1 = batteryMessage.split("level: ");
            String battery = split1[1].split(" ")[0].trim();
            batt= Integer.parseInt(battery);
        }
        return batt;
    }

    //手机是否有SIM卡
    private String getIsSim(String getPropMessage){
        String isSim="0";
        String startReg1 = "\\[ril.iccid.sim1]: \\[";
        String startReg2 = "\\[ril.iccid.sim2]: \\[";
        String overReg = "]";
        String sim1=ADBUtils.getCentreStringBySplit(getPropMessage, startReg1, overReg);
        String sim2=ADBUtils.getCentreStringBySplit(getPropMessage, startReg2, overReg);
        if(sim1==null){
            sim1="N/A";
        }
        if(sim2==null){
            sim2="N/A";
        }
        if (sim1.contains("N/A")&&sim2.equals("N/A")){
        }else {
            if (sim1.toLowerCase().contains("null")&&sim2.toLowerCase().equals("null")){

            }else {
                isSim = "1";
            }
        }
        return isSim;
    }

}
