package com.meizu.db;

import com.meizu.model.TaskInfo;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


/**
 * Created by wuchaolin on 2017/5/25.
 */
public class SQLiteManager {

    private static final String DBPATH = "CTS";

    //创建TaskInfo表
    String taskInfoSQL = "CREATE TABLE IF NOT EXISTS TaskInfo (" +
            "taskID integer primary key autoincrement," +
            "modularName varchar(50), " +
            "className varchar(100), " +
            "methodName varchar(100) UNIQUE, " +
            "packageName varchar(50), " +
            "isTested varchar(10), " +
            "result varchar(10), " +
            "details varchar(200), " +
            "bit_System varchar(10) " +
            ");";

    public static void main(String[] args) {
        SQLiteManager sqLiteManager = new SQLiteManager();
        sqLiteManager.createTable();
        List<TaskInfo> taskInfos = new ArrayList<>();
        for (int i = 0; i < 10; i++){
            TaskInfo taskInfo = new TaskInfo();
            taskInfo.setModularName("modularName_" + i);
            taskInfos.add(taskInfo);
        }
        sqLiteManager.insertTableCTSTask(taskInfos);
        List<TaskInfo> taskInfoList = sqLiteManager.selectTableCTSTask();
//        long starTime=System.currentTimeMillis();
//        sqLiteManager.insertTableCTSTask(taskInfos);
//        long endTime=System.currentTimeMillis();
//        long time = endTime - starTime;
//        System.out.println("insertTableCTSTask: " + time);
//        sqLiteManager.selectTableCTSTask();
//        long lastTime=System.currentTimeMillis();
//        time = lastTime - endTime;
//        System.out.println("selectTableCTSTask: " + time);
    }

    /**
     * 连接数据库
     * @return 连接成功返回Connection，否则置为null
     */
    private Connection connectionSQLite(){
        try {
            //"jdbc:sqlite:" + dbPath + ".db"
            String dbFilePath = String.format("jdbc:sqlite:%s.db", DBPATH);
            Class.forName("org.sqlite.JDBC");
            Connection connection = DriverManager.getConnection(dbFilePath);
            connection.setAutoCommit(false);
            return connection;
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            return null;
        }
    }

    /**
     * 创建数据库表
     */
    public void createTable(){
        try {
            //"jdbc:sqlite:" + dbPath + ".db"
            Connection connection = this.connectionSQLite();
            Statement statement = connection.createStatement();
            statement.execute(taskInfoSQL);//创建task表
            statement.close();
            connection.commit();
            connection.close();
        } catch (Exception e) {
            //System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            e.printStackTrace();
        }
    }

    /**
     * 向TaskInfo表写入数据
     * @param taskInfoList 任务信息集合
     * @return 写入成功返回True
     */
    public boolean insertTableCTSTask(List<TaskInfo> taskInfoList){
        Connection connection = this.connectionSQLite();
        return this.insertTableCTSTask(taskInfoList, connection);
    }

    private boolean insertTableCTSTask(List<TaskInfo> taskInfoList, Connection connection){
        boolean isInsert = false;
        String insertTaskSQL = "INSERT INTO TaskInfo VALUES (NULL, ?, ?, ?, ?, ?, ?, ?, ?);";
        PreparedStatement taskStatement = null;
        try {
            taskStatement = connection.prepareStatement(insertTaskSQL);
            // 插入占位符变量
            for (TaskInfo taskInfo : taskInfoList){
                taskStatement.setString(1, taskInfo.getModularName());
                taskStatement.setString(2, taskInfo.getClassName());
                taskStatement.setString(3, taskInfo.getMethodName());
                taskStatement.setString(4, taskInfo.getPackageName());
                taskStatement.setString(5, taskInfo.isTested());
                taskStatement.setString(6, taskInfo.getResult());
                taskStatement.setString(7, taskInfo.getDetails());
                taskStatement.setString(8, taskInfo.getBit());
                taskStatement.addBatch();
            }
            taskStatement.executeBatch();
            isInsert =  true;
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            this.closeSQLConnection(connection, taskStatement);
        }
        return isInsert;
    }

    /**
     * 读取数据库TaskInfo表中信息
     * @return 读取成功返回任务信息集合
     */
    public List<TaskInfo> selectTableCTSTask(){
        Connection connection = this.connectionSQLite();
        return this.selectTableCTSTask(connection);
    }

    private List<TaskInfo> selectTableCTSTask(Connection connection){
        String selectTaskSQL = "SELECT * FROM TaskInfo;";
        List<TaskInfo> taskInfoList = new LinkedList<>();
        PreparedStatement selectStatement = null;
        ResultSet selectResultSet = null;
        try {
            selectStatement = connection.prepareStatement(selectTaskSQL);
            selectResultSet = selectStatement.executeQuery();
            while (selectResultSet.next()){
                TaskInfo taskInfo = new TaskInfo();
                taskInfo.setTaskID(selectResultSet.getInt("taskID"));
                taskInfo.setModularName(selectResultSet.getString("modularName"));
                taskInfo.setClassName(selectResultSet.getString("className"));
                taskInfo.setMethodName(selectResultSet.getString("methodName"));
                taskInfo.setPackageName(selectResultSet.getString("packageName"));
                taskInfo.setIsTested(selectResultSet.getString("isTested"));
                taskInfo.setResult(selectResultSet.getString("result"));
                taskInfo.setDetails(selectResultSet.getString("details"));
                taskInfo.setBit(selectResultSet.getString("bit_System"));
                taskInfoList.add(taskInfo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            this.closeSQLConnection(connection, selectStatement, selectResultSet);
        }
        return taskInfoList;
    }

    public boolean updateSingleResultByMethod(TaskInfo taskInfo){
        if (taskInfo != null){
            Connection connection = this.connectionSQLite();
            return this.updateSingleResultByMethod(taskInfo, connection);
        }
        return false;
    }

    private boolean updateSingleResultByMethod(TaskInfo taskInfo, Connection connection){
        boolean isUpdate = false;
        String updateByMethodSQL = "UPDATE taskInfo set result = ? where methodName = ?;";
        PreparedStatement updateStatement = null;
        try {
            updateStatement = connection.prepareStatement(updateByMethodSQL);
            updateStatement.setString(1, taskInfo.getResult());
            updateStatement.setString(2, taskInfo.getMethodName());
            updateStatement.executeQuery();
            isUpdate = true;
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            this.closeSQLConnection(connection, updateStatement);
        }
        return isUpdate;
    }

    /**
     * 关闭数据库连接
     * @param connection
     * @param preparedStatement
     */
    private void closeSQLConnection(Connection connection, PreparedStatement preparedStatement){
        try {
            if(null != preparedStatement){
                preparedStatement.close();
            }
            if(null != connection){
                connection.commit();
                connection.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void closeSQLConnection(Connection connection, PreparedStatement preparedStatement, ResultSet resultSet){
        this.closeSQLConnection(connection, preparedStatement);
        try {
            resultSet.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
