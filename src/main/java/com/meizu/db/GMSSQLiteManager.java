package com.meizu.db;

import com.meizu.model.GMSTaskInfo;
import com.meizu.model.GMSTaskResultInfo;
import com.meizu.model.TaskInfo;
import com.meizu.utils.GMSThreadHelper;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by wuchaolin on 2018/1/13.
 */
public class GMSSQLiteManager {
    public static void main(String[] args) {
//        new GMSSQLiteManager().createTable();
//        BufferedReader reader = null;
//        final List<GMSTaskResultInfo> GMSTaskResultInfoList = new ArrayList<>();
//        try {
//            reader = new BufferedReader(new FileReader("host_log.txt"));
//            String line;
//            while ((line = reader.readLine()) != null){
//                GMSTaskResultInfo gmsTaskResultInfo = GMSThreadHelper.analysisResultOfAndroid7(line);
//                if (gmsTaskResultInfo != null){
//                    GMSTaskResultInfoList.add(gmsTaskResultInfo);
//                }
//            }
//            System.out.println(GMSTaskResultInfoList.size());
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    new GMSSQLiteManager().insertTableGMSTaskResult(GMSTaskResultInfoList);
//                }
//            }).start();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        String dbPath = "GMS";
        List<GMSTaskResultInfo> gmsTaskResultInfos = new GMSSQLiteManager(dbPath).selectTableGMSTaskResult();
        List<GMSTaskResultInfo> tempGmsTaskResultInfos = new ArrayList<>();
        for (GMSTaskResultInfo gmsTaskResultInfo : gmsTaskResultInfos) {
            System.out.println(gmsTaskResultInfo);
            gmsTaskResultInfo.setResult("Fail");
            gmsTaskResultInfo.setDetails("Update");
            tempGmsTaskResultInfos.add(gmsTaskResultInfo);
        }
        System.out.println(gmsTaskResultInfos.size());
        new GMSSQLiteManager(dbPath).updateGMSTaskResultByMethod(tempGmsTaskResultInfos);
        List<GMSTaskResultInfo> gmsTaskResultInfoList = new GMSSQLiteManager(dbPath).selectTableGMSTaskResult();

        for (GMSTaskResultInfo newGmsTaskResultInfo : gmsTaskResultInfoList) {
            System.out.println(newGmsTaskResultInfo);
        }
    }

    public static String GMS_DB_PATH;
    public GMSSQLiteManager(String dbPath){
        GMS_DB_PATH = dbPath;
        this.createTable();
    }


    /**
     * 连接数据库
     * @return 连接成功返回Connection，否则置为null
     */
    private Connection connectionSQLite(){
        try {
            //"jdbc:sqlite:" + dbPath + ".db"
            String dbFilePath = String.format("jdbc:sqlite:%s.db", GMSSQLiteManager.GMS_DB_PATH);
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
     * 创建数据库表:GMSTaskInfo表、GMSTaskResultInfo表
     */
    public void createTable(){
        try {
            //"jdbc:sqlite:" + dbPath + ".db"
            Connection connection = this.connectionSQLite();
            if (connection != null){
                Statement statement = connection.createStatement();
                statement.execute(GMSSQLiteConstants.GMS_TASK_INFO);//创建GMSTaskInfo表
                statement.execute(GMSSQLiteConstants.GMS_TASK_RESULT_INFO);//创建GMSTaskResultInfo表
                statement.close();
                connection.commit();
                connection.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 向GMSTaskInfo表写入数据
     * @param gmsTaskInfo 任务信息集合
     * @return 写入成功返回True
     */
    public boolean insertTableGMSTask(List<GMSTaskInfo> gmsTaskInfo){
        Connection connection = this.connectionSQLite();
        return this.insertTableGMSTask(gmsTaskInfo, connection);
    }

    private boolean insertTableGMSTask(List<GMSTaskInfo> gmsTaskInfo, Connection connection){
        boolean isInsert = false;
        String insertGMSTaskSQL = "INSERT INTO GMSTaskInfo VALUES (NULL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        PreparedStatement gmsTaskStatement = null;
        try {
            gmsTaskStatement = connection.prepareStatement(insertGMSTaskSQL);
            // 插入占位符变量
            for (GMSTaskInfo taskInfo : gmsTaskInfo){
                gmsTaskStatement.setString(1, taskInfo.getTaskName());
                gmsTaskStatement.setString(2, taskInfo.getTaskResultFilePath());
                gmsTaskStatement.setString(3, taskInfo.getTaskType());
                gmsTaskStatement.setInt(4, taskInfo.getTaskModularTotal());
                gmsTaskStatement.setInt(5, taskInfo.getTaskTestedModularTotal());
                gmsTaskStatement.setInt(6, taskInfo.getTaskMethodTotal());
                gmsTaskStatement.setInt(7, taskInfo.getTaskPassTotal());
                gmsTaskStatement.setInt(8, taskInfo.getTaskFailTotal());
                gmsTaskStatement.setString(9, taskInfo.getTaskStartTime());
                gmsTaskStatement.setString(10, taskInfo.getTaskFinshTime());
                gmsTaskStatement.setBoolean(11, taskInfo.isTaskTested());
                gmsTaskStatement.addBatch();
            }
            gmsTaskStatement.executeBatch();
            isInsert =  true;
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            this.closeSQLConnection(connection, gmsTaskStatement);
        }
        return isInsert;
    }

    /**
     * 读取数据库GMSTaskInfo表中信息
     * @return 读取成功返回任务信息集合
     */
    public List<GMSTaskInfo> selectTableGMSTask(){
        Connection connection = this.connectionSQLite();
        return this.selectTableGMSTask(connection);
    }

    private List<GMSTaskInfo> selectTableGMSTask(Connection connection){
        String selectTaskSQL = "SELECT * FROM GMSTaskInfo;";
        List<GMSTaskInfo> gmsTaskInfoList = new LinkedList<>();
        PreparedStatement selectStatement = null;
        ResultSet selectResultSet = null;
        try {
            selectStatement = connection.prepareStatement(selectTaskSQL);
            selectResultSet = selectStatement.executeQuery();
            while (selectResultSet.next()){
                GMSTaskInfo gmsTaskInfo = new GMSTaskInfo();
                gmsTaskInfo.setTaskID(selectResultSet.getInt("taskID"));
                gmsTaskInfo.setTaskName(selectResultSet.getString("taskName"));
                gmsTaskInfo.setTaskResultFilePath(selectResultSet.getString("taskResultFilePath"));
                gmsTaskInfo.setTaskType(selectResultSet.getString("taskType"));
                gmsTaskInfo.setTaskModularTotal(selectResultSet.getInt("taskModularTotal"));
                gmsTaskInfo.setTaskTestedModularTotal(selectResultSet.getInt("taskTestedModularTotal"));
                gmsTaskInfo.setTaskMethodTotal(selectResultSet.getInt("taskMethodTotal"));
                gmsTaskInfo.setTaskPassTotal(selectResultSet.getInt("taskPassTotal"));
                gmsTaskInfo.setTaskFailTotal(selectResultSet.getInt("taskFailTotal"));
                gmsTaskInfo.setTaskStartTime(selectResultSet.getString("taskStartTime"));
                gmsTaskInfo.setTaskFinshTime(selectResultSet.getString("taskFinishTime"));
                gmsTaskInfo.setTaskTested(selectResultSet.getBoolean("isTaskTested"));
                gmsTaskInfoList.add(gmsTaskInfo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            this.closeSQLConnection(connection, selectStatement, selectResultSet);
        }
        return gmsTaskInfoList;
    }

    /**
     * 向GMSTaskResultInfo表写入数据
     * @param gmsTaskResultInfo 任务信息集合
     * @return 写入成功返回True
     */
    public boolean insertTableGMSTaskResult(List<GMSTaskResultInfo> gmsTaskResultInfo){
        Connection connection = this.connectionSQLite();
        return this.insertTableGMSTaskResult(gmsTaskResultInfo, connection);
    }

    private boolean insertTableGMSTaskResult(List<GMSTaskResultInfo> gmsTaskResultInfo, Connection connection){
        boolean isInsert = false;
        String insertGMSTaskResultSQL = "INSERT INTO GMSTaskResultInfo VALUES (NULL, ?, ?, ?, ?, ?, ?, ?, ?);";
        PreparedStatement gmsTaskResultStatement = null;
        try {
            gmsTaskResultStatement = connection.prepareStatement(insertGMSTaskResultSQL);
            // 插入占位符变量
            for (GMSTaskResultInfo taskResultInfo : gmsTaskResultInfo){
                gmsTaskResultStatement.setString(1, taskResultInfo.getTaskName());
                gmsTaskResultStatement.setString(2, taskResultInfo.getModularName());
                gmsTaskResultStatement.setString(3, taskResultInfo.getClassName());
                gmsTaskResultStatement.setString(4, taskResultInfo.getMethodName());
                gmsTaskResultStatement.setString(5, taskResultInfo.getBit());
                gmsTaskResultStatement.setString(6, taskResultInfo.getResult());
                gmsTaskResultStatement.setString(7, taskResultInfo.getDetails());
                gmsTaskResultStatement.setBoolean(8, taskResultInfo.isTested());
                gmsTaskResultStatement.addBatch();
            }
            gmsTaskResultStatement.executeBatch();
            isInsert =  true;
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            this.closeSQLConnection(connection, gmsTaskResultStatement);
        }
        return isInsert;
    }

    /**
     * 读取数据库GMSTaskResultInfo表中信息
     * @return 读取成功返回任务信息集合
     */
    public List<GMSTaskResultInfo> selectTableGMSTaskResult(){
        Connection connection = this.connectionSQLite();
        return this.selectTableGMSTaskResult(connection);
    }

    private List<GMSTaskResultInfo> selectTableGMSTaskResult(Connection connection){
        String selectTaskResultSQL = "SELECT * FROM GMSTaskResultInfo;";
        List<GMSTaskResultInfo> gmsTaskResultInfoList = new LinkedList<>();
        PreparedStatement selectStatement = null;
        ResultSet selectResultSet = null;
        try {
            selectStatement = connection.prepareStatement(selectTaskResultSQL);
            selectResultSet = selectStatement.executeQuery();
            while (selectResultSet.next()){
                GMSTaskResultInfo gmsTaskResultInfo = this.getGmsTaskResultByResultSet(selectResultSet);
                gmsTaskResultInfoList.add(gmsTaskResultInfo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            this.closeSQLConnection(connection, selectStatement, selectResultSet);
        }
        return gmsTaskResultInfoList;
    }

    /**
     * 读取数据库GMSTaskResultInfo表中信息：以result结果为筛选依据,注意result必需为"Fail","Pass"中的一个
     * @return 读取成功返回任务信息集合
     */
    public List<GMSTaskResultInfo> selectTableGMSTaskResultByResult(String result){
        Connection connection = this.connectionSQLite();
        return this.selectTableGMSTaskResultByResult(result, connection);
    }

    private List<GMSTaskResultInfo> selectTableGMSTaskResultByResult(String result, Connection connection){
        String selectTaskResultSQL = "SELECT * FROM GMSTaskResultInfo WHERE result = ?;";
        List<GMSTaskResultInfo> gmsTaskResultInfoList = new LinkedList<>();
        PreparedStatement selectStatement = null;
        ResultSet selectResultSet = null;
        try {
            selectStatement = connection.prepareStatement(selectTaskResultSQL);
            selectStatement.setString(1, result);
            selectResultSet = selectStatement.executeQuery();
            while (selectResultSet.next()){
                GMSTaskResultInfo gmsTaskResultInfo = this.getGmsTaskResultByResultSet(selectResultSet);
                gmsTaskResultInfoList.add(gmsTaskResultInfo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            this.closeSQLConnection(connection, selectStatement, selectResultSet);
        }
        return gmsTaskResultInfoList;
    }


    /**
     * 读取数据库GMSTaskResultInfo表中信息：获取需使用SIM卡的case，且以result结果为筛选依据,注意result必需为"Fail","Pass"中的一个
     * @return 读取成功返回任务信息集合
     */
    public List<GMSTaskResultInfo> selectTableGMSTaskResultForSIM(String result){
        Connection connection = this.connectionSQLite();
        return this.selectTableGMSTaskResultForSIM(result, connection);
    }

    private List<GMSTaskResultInfo> selectTableGMSTaskResultForSIM(String result, Connection connection){
        String selectTaskResultSQL = "SELECT * FROM GMSTaskResultInfo WHERE (details LIKE '%SIM%' " +
                "OR modularName = 'CtsTelecomTestCases' OR modularName = 'CtsTelecomTestCases2'" +
                "OR modularName = 'CtsTelephony2TestCases' OR modularName = 'CtsTelephonyTestCases') AND result = ?;";
        List<GMSTaskResultInfo> gmsTaskResultInfoList = new LinkedList<>();
        PreparedStatement selectStatement = null;
        ResultSet selectResultSet = null;
        try {
            selectStatement = connection.prepareStatement(selectTaskResultSQL);
            selectStatement.setString(1, result);
            selectResultSet = selectStatement.executeQuery();
            while (selectResultSet.next()){
                GMSTaskResultInfo gmsTaskResultInfo = this.getGmsTaskResultByResultSet(selectResultSet);
                gmsTaskResultInfoList.add(gmsTaskResultInfo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            this.closeSQLConnection(connection, selectStatement, selectResultSet);
        }
        return gmsTaskResultInfoList;
    }

    private GMSTaskResultInfo getGmsTaskResultByResultSet(ResultSet selectResultSet){
        GMSTaskResultInfo gmsTaskResultInfo = null;
        try {
            gmsTaskResultInfo = new GMSTaskResultInfo();
            gmsTaskResultInfo.setTaskID(selectResultSet.getInt("taskID"));
            gmsTaskResultInfo.setTaskName(selectResultSet.getString("taskName"));
            gmsTaskResultInfo.setModularName(selectResultSet.getString("modularName"));
            gmsTaskResultInfo.setClassName(selectResultSet.getString("className"));
            gmsTaskResultInfo.setMethodName(selectResultSet.getString("methodName"));
            gmsTaskResultInfo.setBit(selectResultSet.getString("armBit"));
            gmsTaskResultInfo.setResult(selectResultSet.getString("result"));
            gmsTaskResultInfo.setDetails(selectResultSet.getString("details"));
            gmsTaskResultInfo.setTested(selectResultSet.getBoolean("isTested"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gmsTaskResultInfo;
    }

    /**
     * 更新GMSTaskResultInfo表中数据：以Method为筛选依据，仅更改result，details
     * @param gmsTaskResultInfo
     * @return
     */
    public boolean updateGMSTaskResultByMethod(List<GMSTaskResultInfo> gmsTaskResultInfo){
        if (gmsTaskResultInfo != null){
            Connection connection = this.connectionSQLite();
            return this.updateGMSTaskResultByMethod(gmsTaskResultInfo, connection);
        }
        return false;
    }

    private boolean updateGMSTaskResultByMethod(List<GMSTaskResultInfo> gmsTaskResultInfo, Connection connection){
        boolean isUpdate = false;
        String updateByMethodSQL = "UPDATE GMSTaskResultInfo SET result = ?, details = ? WHERE className = ? AND methodName = ? AND armBit = ?;";
        PreparedStatement updateStatement = null;
        try {
            updateStatement = connection.prepareStatement(updateByMethodSQL);
            // 插入占位符变量
            for (GMSTaskResultInfo singleResultInfo : gmsTaskResultInfo){
                updateStatement.setString(1, singleResultInfo.getResult());
                updateStatement.setString(2, singleResultInfo.getDetails());
                updateStatement.setString(3, singleResultInfo.getClassName());
                updateStatement.setString(4, singleResultInfo.getMethodName());
                updateStatement.setString(5, singleResultInfo.getBit());
                updateStatement.addBatch();
            }
            updateStatement.executeBatch();
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
