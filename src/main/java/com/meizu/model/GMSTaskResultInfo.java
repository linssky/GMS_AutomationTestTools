package com.meizu.model;

/**
 * Created by wuchaolin on 2018/1/13.
 */
public class GMSTaskResultInfo {
    private String mTaskName;//任务名
    private int mTaskID;//任务ID
    private String mModularName;//模块名
    private String mClassName;//类名
    private String mMethodName;//方法名
    private String mBit;//测试系统位数
    private String mResult;//测试结果
    private String mDetails;//测试结果详情
    private boolean isTested;//是否已被测试

    public String getTaskName() {
        return mTaskName;
    }

    public void setTaskName(String taskName) {
        this.mTaskName = taskName;
    }

    public int getTaskID() {
        return mTaskID;
    }

    public void setTaskID(int taskID) {
        this.mTaskID = taskID;
    }

    public String getModularName() {
        return mModularName;
    }

    public void setModularName(String modularName) {
        this.mModularName = modularName;
    }

    public String getClassName() {
        return mClassName;
    }

    public void setClassName(String className) {
        this.mClassName = className;
    }

    public String getMethodName() {
        return mMethodName;
    }

    public void setMethodName(String methodName) {
        this.mMethodName = methodName;
    }

    public String getBit() {
        return mBit;
    }

    public void setBit(String bit) {
        this.mBit = bit;
    }

    public String getResult() {
        return mResult;
    }

    public void setResult(String result) {
        this.mResult = result;
    }

    public String getDetails() {
        return mDetails;
    }

    public void setDetails(String details) {
        this.mDetails = details;
    }

    public boolean isTested() {
        return isTested;
    }

    public void setTested(boolean tested) {
        isTested = tested;
    }

    @Override
    public String toString() {
        return "GMSTaskResultInfo{" +
                "TaskName='" + mTaskName + '\'' +
                ", TaskID=" + mTaskID +
                ", ModularName='" + mModularName + '\'' +
                ", ClassName='" + mClassName + '\'' +
                ", MethodName='" + mMethodName + '\'' +
                ", Bit='" + mBit + '\'' +
                ", Result='" + mResult + '\'' +
                ", Details='" + mDetails + '\'' +
                ", isTested=" + isTested +
                '}';
    }
}
