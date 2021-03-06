package com.meizu.model;

/**
 * Created by wuchaolin on 17-11-21.
 */
public class FilePathInfo {
    private String oldResultPath;//结果文件路径
    private String oldLogPath;//log文件路径
    private String runXtsPath;//运行测试的绝对路径
    private String newResultPath;//设备内核信息
    private String newLogPath;//设备基带信息
    private String mDevicesModel;//设备型号
    private int mBattery;//设备当前电量
    private boolean mSim;//设备是否有SIM卡

    public String getOldResultPath() {
        return oldResultPath;
    }

    public void setOldResultPath(String OldResultPath) {
        this.oldResultPath = OldResultPath;
    }

    public String getOldLogPath() {
        return oldLogPath;
    }

    public void setOldLogPath(String OldLogPath) {
        this.oldLogPath = OldLogPath;
    }


    public String getRunXtsPath() {
        return runXtsPath;
    }

    public void setRunXtsPath(String RunXtsPath) {
        this.runXtsPath = RunXtsPath;
    }


    public String getNewResultPath() {
        return newResultPath;
    }

    public void setNewResultPath(String NewResultPath) {
        this.newResultPath = NewResultPath;
    }

    public String getNewLogPath() {
        return newLogPath;
    }

    public void setNewLogPath(String NewLogPath) {
        this.newLogPath = NewLogPath;
    }
}
