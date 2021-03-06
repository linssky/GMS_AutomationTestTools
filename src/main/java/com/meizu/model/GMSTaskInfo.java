package com.meizu.model;

/**
 * Created by wuchaolin on 2018/1/13.
 */
public class GMSTaskInfo {
    private String mTaskName;//任务名
    private int mTaskID;//任务ID
    private String mTaskResultFilePath;//任务结果保存路径
    private String mTaskType;//任务类型
    private int mTaskModularTotal;//模块总数
    private int mTaskTestedModularTotal;//已跑模块总数
    private int mTaskMethodTotal;//case总数
    private int mTaskPassTotal;//pass总数
    private int mTaskFailTotal;//fail总数
    private String mTaskStartTime;//任务启动时间
    private String mTaskFinishTime;//任务完成时间
    private boolean isTaskTested;//任务是否测试完成

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

    public String getTaskResultFilePath() {
        return mTaskResultFilePath;
    }

    public void setTaskResultFilePath(String taskResultFilePath) {
        this.mTaskResultFilePath = taskResultFilePath;
    }

    public String getTaskType() {
        return mTaskType;
    }

    public void setTaskType(String taskType) {
        this.mTaskType = taskType;
    }

    public int getTaskModularTotal() {
        return mTaskModularTotal;
    }

    public void setTaskModularTotal(int taskModularTotal) {
        this.mTaskModularTotal = taskModularTotal;
    }

    public int getTaskTestedModularTotal() {
        return mTaskTestedModularTotal;
    }

    public void setTaskTestedModularTotal(int taskTestedModularTotal) {
        this.mTaskTestedModularTotal = taskTestedModularTotal;
    }

    public int getTaskMethodTotal() {
        return mTaskMethodTotal;
    }

    public void setTaskMethodTotal(int taskMethodTotal) {
        this.mTaskMethodTotal = taskMethodTotal;
    }

    public int getTaskPassTotal() {
        return mTaskPassTotal;
    }

    public void setTaskPassTotal(int taskPassTotal) {
        this.mTaskPassTotal = taskPassTotal;
    }

    public int getTaskFailTotal() {
        return mTaskFailTotal;
    }

    public void setTaskFailTotal(int taskFailTotal) {
        this.mTaskFailTotal = taskFailTotal;
    }

    public String getTaskStartTime() {
        return mTaskStartTime;
    }

    public void setTaskStartTime(String taskStartTime) {
        this.mTaskStartTime = taskStartTime;
    }

    public String getTaskFinshTime() {
        return mTaskFinishTime;
    }

    public void setTaskFinshTime(String taskFinishTime) {
        this.mTaskFinishTime = taskFinishTime;
    }

    public boolean isTaskTested() {
        return isTaskTested;
    }

    public void setTaskTested(boolean isTaskTested) {
        this.isTaskTested = isTaskTested;
    }

    @Override
    public String toString() {
        return "GMSTaskInfo{" +
                "TaskName='" + mTaskName + '\'' +
                ", TaskID=" + mTaskID +
                ", TaskResultFilePath='" + mTaskResultFilePath + '\'' +
                ", TaskType='" + mTaskType + '\'' +
                ", TaskModularTotal=" + mTaskModularTotal +
                ", TaskTestedModularTotal=" + mTaskTestedModularTotal +
                ", TaskMethodTotal=" + mTaskMethodTotal +
                ", TaskPassTotal=" + mTaskPassTotal +
                ", TaskFailTotal=" + mTaskFailTotal +
                ", TaskStartTime='" + mTaskStartTime + '\'' +
                ", TaskFinishTime='" + mTaskFinishTime + '\'' +
                ", isTaskTested=" + isTaskTested +
                '}';
    }

}
