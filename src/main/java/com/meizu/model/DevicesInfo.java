package com.meizu.model;

/**
 * Created by wuchaolin on 2017/5/17.
 */
public class DevicesInfo {
    private String mSerialNumber;//设备SN号
    private String mAndroidVersion;//设备Android版本号
    private String mUpdateVersion;//设备固件版本号
    private String mGcc;//设备内核信息
    private String mGsm;//设备基带信息
    private String mDevicesModel;//设备型号
    private String mDevicesImei;
    private String mStatus;//状态
    private int mBattery;//设备当前电量
    private String mSim;//设备是否有SIM卡
    private String mTaskId;//任务id

    public String getSerialNumber() {
        return mSerialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.mSerialNumber = serialNumber;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setmStatus(String status) {
        this.mStatus = status;
    }

    public String getTaskId() {
        return mTaskId;
    }

    public void setmTaskId(String taskId ) {
        this.mTaskId = taskId;
    }

    public String getAndroidVersion() {
        return mAndroidVersion;
    }

    public void setAndroidVersion(String androidVersion) {
        this.mAndroidVersion = androidVersion;
    }

    public String getUpdateVersion() {
        return mUpdateVersion;
    }

    public void setUpdateVersion(String updateVersion) {
        this.mUpdateVersion = updateVersion;
    }

    public String getGcc() {
        return mGcc;
    }

    public void setGcc(String gcc) {
        this.mGcc = gcc;
    }

    public String getGsm() {
        return mGsm;
    }

    public void setGsm(String gsm) {
        this.mGsm = gsm;
    }

    public String getDevicesModel() {
        return mDevicesModel;
    }

    public void setDevicesModel(String devicesModel) {
        this.mDevicesModel = devicesModel;
    }

    public String getDevicesImei() {
        return mDevicesImei;
    }

    public void setDevicesImei(String devicesImei) {
        this.mDevicesImei = devicesImei;
    }

    public int getBattery() {
        return mBattery;
    }

    public void setBattery(int battery) {
        this.mBattery = battery;
    }

    public String getSim() {
        return mSim;
    }

    public void setSim(String Sim) {
        this.mSim = Sim;
    }

    @Override
    public String toString() {
        return "DevicesInfo{" +
                "SerialNumber='" + mSerialNumber + '\'' +
                ", AndroidVersion='" + mAndroidVersion + '\'' +
                ", UpdateVersion='" + mUpdateVersion + '\'' +
                ", Gcc='" + mGcc + '\'' +
                ", Gsm='" + mGsm + '\'' +
                ", DevicesModel='" + mDevicesModel + '\'' +
                ", Battery='" + mBattery + '\'' +
                '}';
    }

}
