
package com.meizu.utils;

import com.meizu.model.ConstantInfo;

import java.sql.*;
import java.util.List;

/**
 * Created by wuchaolin on 17-4-26.
 */
public class SQLiteJDBC {
    public static ConstantInfo VInfo=new ConstantInfo();

    /**
     * 创建表格的参数，默认格式是如果表格不存在则创建
     * 创建的表格包括ID（默认为主键自增）/Modular(模块)等，后续需要则再添加
     * @param formName 需要创建的表格名称
     * @return 返回一个sql的语句，用于判断并创建表格
     */
//    public String CreatTable(String formName){
//        String sql="CREATE TABLE if not exists "+formName+ "(" +//如果表格不存在则创建
//                VInfo.ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"+//分配CTS任务时的标记
//                VInfo.Modular+ " CHAR(150) ,"+//模块（android7分类方式）
//                VInfo.Packge+ " CHAR(150),"+//包（android6时会有，不确定android8会不会改回来，暂时预留）
//                VInfo.Class + " CHAR(150) ,"+//类
//                VInfo.Method+ " CHAR(150) ,"+//方法
//                VInfo.Result+ " CHAR(50) ,"+//测试结果
//                VInfo.Details+ " CHAR(150) " +
//                ")";//失败项的描述
//        return sql;
//    }


    /**
     * 这个方法是用在读取数据库中某一行的信息并以数组形式返回，读取完成后删除该行
     * 返回的参数包括表格当前总行数/模块/类/方法等
     * @param formName sqilte的表格名称
     * @param rows  需要查询并删除的行数（如果为负数，则只查询表格的总行数）
     * @return  如果传入的rows是非负数，则返回该行的数据，然后删除该行；如果传入的rows是负数，则返回表格的当前总行数
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws InterruptedException
     */
//    public synchronized List<String> GetTableInfo(String formName , int rows) throws ClassNotFoundException, SQLException, InterruptedException {
//        List<String> info=new ArrayList();
//        Connection c = null;//数据库初始化
//        Statement stmt = null;//数据库初始化
//        Class.forName("org.sqlite.JDBC");
//        c = DriverManager.getConnection("jdbc:sqlite:" + "GMS_Test" + ".db");//连接数据库
//        stmt=c.createStatement();
//        String sql = CreatTable(formName);
//        stmt.executeUpdate(sql);//连接表格
//        if(rows>=0) {//如果传入的是非负数，就进行查询和删除
//            sql = "SELECT * FROM " + formName + " LIMIT " + rows + ",1;";
//            ResultSet rs = stmt.executeQuery(sql);
//            if(rs.next()) {//如果查询的行数不是null，则读取相应数据
//                int id = rs.getInt(VInfo.ID);
//                System.out.println("线程" + Thread.currentThread().getName() + "读取过ID：" + id);
//                String Modular = rs.getString(VInfo.Modular);
//                String Package = rs.getString(VInfo.Packge);
//                String Class = rs.getString(VInfo.Class);
//                String method = rs.getString(VInfo.Method);
//                System.out.println("modular=" + Modular);
//                info.add(Modular);
//                info.add(Package);
//                info.add(Class);
//                info.add(method);
//                sql = "DELETE  FROM " + formName + " WHERE " + VInfo.ID + "=" + id + ";";
//                stmt.executeUpdate(sql);
//                System.out.println("线程" + Thread.currentThread().getName() + "删除了 模块" + Modular);
//                Thread.sleep(1000 * 2);
//            }else {//如果查询的行数是null，则返回特殊字符，读取到该字符时候则表示已经测试完成
//                String Modular = "it's over";
//                String Package = "it's over";
//                String Class = "it's over";
//                String method = "it's over";
//                info.add(Modular);
//                info.add(Package);
//                info.add(Class);
//                info.add(method);
//            }
//            rs.close();
//        }else{//如果传入的rows是负数，则进行总行数查询
//            sql = "SELECT count(*) FROM " + formName + ";";
//            ResultSet rs = stmt.executeQuery(sql);
//            if(rs.next()) {
//                int s = rs.getInt(1);//返回第一列的值
//                String ss = String.valueOf(s);
//                info.add(0, ss);
//            }
//            rs.close();
//        }
//        stmt.close();
//        c.close();
//        return info;
//    }


    /**
     * 插入表格操作，用于结果统计
     * @param formName 表格名
     * @param modularName 模块名
     * @param packgeName 包名
     * @param className 类名
     * @param methodName 方法名
     * @param Results 结果
     * @param Details 结果描述
     */
//    public synchronized void InsertForm(String formName,//表格名称
//                          String modularName,//模块名
//                          String packgeName,//包名
//                          String className,//类名
//                          String methodName,//方法名
//                          String Results,//测试结果，状态为fail或者pass，CTS测试完成后填入
//                          String Details//问题描述，CTS测试完成后，fail项填入
//                          ){//formName参数为需要创建的表格名称
//        Connection c = null;
//        Statement stmt = null;
//        try {
//            Class.forName("org.sqlite.JDBC");
//            c = DriverManager.getConnection("jdbc:sqlite:"+"GMS_Test"+".db");
//            stmt=c.createStatement();
//            String sql=CreatTable(formName);
//            stmt.executeUpdate(sql);
//            sql="INSERT INTO "+formName+"("+
//                    VInfo.ID+","+
//                    VInfo.Modular+","+
//                    VInfo.Packge+","+
//                    VInfo.Class+","+
//                    VInfo.Method+","+
//                    VInfo.Result+","+
//                    VInfo.Details+") "+
//                            "VALUES ("+null+ ",'"+
//                    modularName+"','"+
//                    packgeName+"','"+
//                    className+"','"+
//                    methodName+"','"+
//                    Results+"','"+
//                    Details+"'"+");";
//            stmt.executeUpdate(sql);
//            stmt.close();
//            c.close();
//
//        }catch (Exception e){
//            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
//            System.exit(0);
//        }
//        System.out.println("Table run successfully");
//    }

    //-----------------分割线----------------


    /**
     * 插入表格操作，用于结果统计
     * @param formName 表格名
     * @param Status 状态
     * @param SN SN
     * @param TaskID 任务ID
     */
    public synchronized void InsertStatusForm(String formName,//表格名称
                                        String SN,//SN好
                                        String Status,//状态
                                        String TaskID //任务ID
    ){//formName参数为需要创建的表格名称
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:"+"GMS_Test"+".db");
            stmt=c.createStatement();
            String sql=CreatStatusTable(formName);
            stmt.executeUpdate(sql);
            sql="INSERT INTO "+formName+"("+VInfo.ID+","+VInfo.SN+","+VInfo.Status+","+VInfo.TaskId+") "+
                    "VALUES ( "+null+ " , '"+SN+" ',' "+Status+" ',' "+TaskID+"'"+");";//插入的格式必须这样，否则会报错
            stmt.executeUpdate(sql);
            stmt.close();
            c.close();

        }catch (Exception e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }


    /**
     * 修改指定SN号行数表格的数据
     *
     * @param
     */
    public synchronized void initSQL( String formName,//表格名称
                                           String Status,//状态
                                           String TaskID //任务ID
    ) {
            Connection c = null;
            Statement stmt = null;
            try {
                Class.forName("org.sqlite.JDBC");
                c = DriverManager.getConnection("jdbc:sqlite:" + "GMS_Test" + ".db");
                stmt = c.createStatement();
                String sql = CreatStatusTable(formName);//793QBDQP222GN  79BQADQNFHNCW
                stmt.executeUpdate(sql);

                sql = " UPDATE " + formName + " set " + VInfo.Status + "= '" + Status + "';";
                stmt.executeUpdate(sql);
                sql = " UPDATE " + formName + " set " + VInfo.TaskId + "= '" + TaskID + "';";
                stmt.executeUpdate(sql);
                stmt.close();
                c.close();

            } catch (Exception e) {
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
                System.exit(0);
            }

    }

    /**
     * 修改指定SN号行数表格的数据
     *
     * @param
     */
    public synchronized void updateStatus( String formName,//表格名称
                                    String SN,//SN号
                                    String Status,//状态
                                    String TaskID //任务ID
                                     ) {
        int id=getID(formName,SN);
        if (id>0) {
            Connection c = null;
            Statement stmt = null;
            try {
                Class.forName("org.sqlite.JDBC");
                c = DriverManager.getConnection("jdbc:sqlite:" + "GMS_Test" + ".db");
                stmt = c.createStatement();
                String sql = CreatStatusTable(formName);//793QBDQP222GN  79BQADQNFHNCW
                stmt.executeUpdate(sql);

                sql = " UPDATE " + formName + " set " + VInfo.Status + "= '" + Status + "'  WHERE " + VInfo.ID + "=" + id + ";";
                stmt.executeUpdate(sql);
                sql = " UPDATE " + formName + " set " + VInfo.TaskId + "= '" + TaskID + "'  WHERE " + VInfo.ID + "=" + id + ";";
                stmt.executeUpdate(sql);
                stmt.close();
                c.close();

            } catch (Exception e) {
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
                System.exit(0);
            }
        }else {
            System.out.println("sqlite中未找到对应的SN号");
        }
    }


    /**
     * 查询表格中是否存在该SN,true表示存在
     *
     * @param
     */
    public synchronized boolean haveSNinfornName( String formName,//表格名称
                                     String SN//SN号
    ) {
        boolean results=false;//默认不存在
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:"+"GMS_Test"+".db");
            c.setAutoCommit(false);
            stmt=c.createStatement();
            String sql=CreatStatusTable(formName);

            stmt.executeUpdate(sql);
            sql="SELECT * FROM "+formName+";";
            ResultSet rs = stmt.executeQuery(sql);
           out: while (rs.next()){
                String sn=rs.getString(VInfo.SN);
                if ((sn.toString().trim()).equals(SN.toString().trim())){//必须这么写才能匹配的上，否则一直false
                    results=true;
                    break out;
                }
            }
            rs.close();
            stmt.close();
            c.close();

        }catch (Exception e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        return  results;
    }

    /**
     * 查询表格中是否存在忙碌状态的手机，用于全跑时判断,若有手机忙碌则返回true
     *
     * @param
     */
    public synchronized boolean haveAnyBusy( String formName//表格名称
    ) {
        boolean results=false;//默认空闲
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:"+"GMS_Test"+".db");
            c.setAutoCommit(false);
            stmt=c.createStatement();
            String sql=CreatStatusTable(formName);

            stmt.executeUpdate(sql);
            sql="SELECT * FROM "+formName+";";
            ResultSet rs = stmt.executeQuery(sql);
            out: while (rs.next()){
                String status=rs.getString(VInfo.Status);
                if ((status.toString().trim()).equals("2")){
                    results=true;
                    break out;
                }
            }
            rs.close();
            stmt.close();
            c.close();

        }catch (Exception e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        return  results;
    }

    /**
     * 通过SN号获取对应行数的ID
     *
     * @param
     */
    public synchronized int getID( String formName,//表格名称
                                        String SN//SN号
    ) {
        int results = 0;
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:"+"GMS_Test"+".db");
            c.setAutoCommit(false);
            stmt=c.createStatement();
            String sql=CreatStatusTable(formName);

            stmt.executeUpdate(sql);
            sql="SELECT * FROM "+formName+";";
            ResultSet rs = stmt.executeQuery(sql);
            out: while (rs.next()){
                String sn=rs.getString(VInfo.SN);
                if ((sn.toString().trim()).equals(SN.toString().trim())){//必须这么写才能匹配的上，否则一直false
                    results=rs.getInt(VInfo.ID);
                    break out;
                }
            }
            rs.close();
            stmt.close();
            c.close();

        }catch (Exception e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        return  results;
    }


    //判断传入的SN是否存在，如果不存在，就修改所有的结果
    public void setStatusAndTaskID(String formName,String SN,String Status,String TaskID  ){
        RunCTSUtils runCTSUtils=new RunCTSUtils();
        List<String> devicesSerialNumber = DevicesUtils.getDevicesSN();


        if (!SN.toLowerCase().trim().equals("all")){
            SetStatusAndTaskID(formName,SN,Status,TaskID);
        }else {
            if (devicesSerialNumber != null){
                for (String serialNumber : devicesSerialNumber){
                    System.out.println("全跑，修改所有设备状态："+serialNumber);
                    SetStatusAndTaskID(formName,serialNumber.toString().trim(),Status,TaskID);
                }
            }
        }
    }

    //必须存在的SN才会判断
    public void SetStatusAndTaskID(String formName,String SN,String Status,String TaskID  ){
            if (haveSNinfornName(formName,SN)){
                System.out.println("走的updat");
                updateStatus(formName,SN,Status,TaskID);
            }else {
                System.out.println("走的inser");
                InsertStatusForm(formName,SN,Status,TaskID);
            }
    }

    /**获取对应SN号手机的状态和任务id
     * status:String  数组第一个  1是空闲，2是忙碌
     * TaskId:string  数组第二个*/
    public String[] getStatusAndTaskID(String formName,String SN){
        RunCTSUtils runCTSUtils=new RunCTSUtils();
        List<String> devicesSerialNumber = DevicesUtils.getDevicesSN();
        String[] st=new String[2];
        String status="1";
        String taskId="0";
        st[0]=status;
        st[1]=taskId;

        if (runCTSUtils.haveSN(SN)){
            if (!haveSNinfornName(formName,SN)){//如果这个设备在数据库中找不到，则在数据库中初始化这个设备
                SetStatusAndTaskID(formName,SN,st[0],st[1]);
            }else {
                st=GetStatusAndTaskID(formName,SN);
            }
        }else {
            out:if (devicesSerialNumber != null){
                for (String serialNumber : devicesSerialNumber){
                    st=GetStatusAndTaskID(formName,serialNumber);
                    break out;
                }
            }
        }
        return st;
    }
    /**获取对应SN号手机的状态和任务id
     * status:String  数组第一个  1是空闲，2是忙碌
     * TaskId:string  数组第二个*/
    public String[] GetStatusAndTaskID(String formName,String SN){
        String[] st=new String[2];
        String status="1";
        String taskId="0";
        st[0]=status;
        st[1]=taskId;

//        System.out.println("=-=-=-=-=-=-SQL的路径是：=-=-=-:"+ ADBUtils.getCommandResult("pwd"));

        if (haveSNinfornName(formName,SN)) {//如果有SN号，则读取
            int id = getID(formName, SN);
            System.out.println("------id------:"+id);
            if (id > 0) {//存在该ID
               st=getStutsID(formName,id);
            }
        }else {//若没有SN号，则初始化：ststus1 空闲，taskID 0
            System.out.println("SN号有误，无法获取到status和TaskId");
        }
        return st;
    }

    public synchronized String[] getStutsID(String formName, int ID){
        String[] st=new String[2];
        String status="1";
        String taskId="0";
        st[0]=status;
        st[1]=taskId;
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:" + "GMS_Test" + ".db");
            stmt = c.createStatement();
            String sql = CreatStatusTable(formName);
            stmt.executeUpdate(sql);
            sql = "SELECT * FROM " + formName + " WHERE " + VInfo.ID + "=" + ID + ";";
            ResultSet rs = stmt.executeQuery(sql);
            st[0] = rs.getString(VInfo.Status);
            st[1] = rs.getString(VInfo.TaskId);
            rs.close();
            stmt.close();
            c.close();

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return st;
    }


    /**
     * 创建表格的参数，默认格式是如果表格不存在则创建
     * 创建的表格包括ID（默认为主键自增）/Modular(模块)等，后续需要则再添加
     * @param formName 需要创建的表格名称
     * @return 返回一个sql的语句，用于判断并创建表格
     */
    public String CreatStatusTable(String formName){
        String sql="CREATE TABLE if not exists "+formName+ "(" +//如果表格不存在则创建
                VInfo.ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"+//分配CTS任务时的标记
                VInfo.SN+ " CHAR(150) ,"+//SN号
                VInfo.Status+ " CHAR(150),"+//状态
                VInfo.TaskId + " CHAR(150)"+//任务ID
                ")";
        return sql;
    }

}
