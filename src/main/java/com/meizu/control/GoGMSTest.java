package com.meizu.control;

/**
 * Created by wuchaolin on 17-11-28.
 */

import com.meizu.utils.SQLiteJDBC;
import com.meizu.utils.WebServiceBean;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class GoGMSTest extends HttpServlet {
    SQLiteJDBC sj=new SQLiteJDBC();


    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException
    {

        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        final String SN = request.getParameter("SN");
        final String XTS=request.getParameter("XTS");
        final String arm=request.getParameter("arm");
        final String firmPath=request.getParameter("firmPath");
//        final String firmPath="/home/wuzhaolin/GMSTools/mount/172.16.1.98.firmware/DailyBuild4Test/DailyBuildM1891/app/海外版/Nougat_Flyme7_M1891/M1891_NF7_base/user/20171204115545_intl_I_GMS";
        final String command=request.getParameter("command");
        final String taskId=request.getParameter("taskId");

        System.out.println("解析到的各个值----:" +
                "\nSN="+SN+"\nXTS="+XTS+"\narm="+arm+
                "\nfirmware="+firmPath+"\ncommand="+command
        +"\ntaskId="+taskId);


        WebServiceBean wb=new WebServiceBean();
        if (wb.haveRuning("DevicesInfo",SN,taskId)) {//判断传入的SN号是否空闲，否则直接跳出
            System.out.println("传入的固件路径是！！！！："+firmPath);
            if (wb.isXts(XTS,arm)) {//如果XTS和arm参数不是空的
                System.out.println("准备开始run了！！");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        sj.setStatusAndTaskID("DevicesInfo",SN, "2", taskId);
                        System.out.println("=-=-=-=-=-="+sj.getStatusAndTaskID("DevicesInfo",SN)[0]+"-=-=-="+sj.getStatusAndTaskID("DevicesInfo",SN)[1]);
                        try {
                            runXts.goXTS(SN, XTS, arm, firmPath, command, taskId);
                        } catch (Exception e) {
                            System.out.println(XTS + "测试运行出错！-------");
                            e.printStackTrace();
                        }
                        sj.setStatusAndTaskID("DevicesInfo",SN, "1", "0");
                    }
                }).start();

                setHeadAndBody( taskId,"0","任务创建成功!", response);

            }else {
                setHeadAndBody(taskId,"-1","xts或arm参数错误！",response);
            }
        }else {
            setHeadAndBody(taskId,"-1","机器忙碌！",response);
        }


    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
        //Do some other work
    }



    public void setHeadAndBody(String TaskId, String Status, String detail,
                               HttpServletResponse response) throws IOException {
//        response.setContentType("text/html;charset=UTF-8");
//        PrintWriter out = response.getWriter();

                response.setContentType("text/plain");
                response.setCharacterEncoding("utf-8");
                //模拟数据库数据
               try {
                   Map<String, String> map = new HashMap<String, String>();
                   map.put("task_id", TaskId);
                   map.put("status", Status);
                   map.put("message", detail);
                   //前台传过来的参数
//                String data=request.getParameter("data");
                   JSONObject re = new JSONObject(map);
                   String result = re.toString();
                   System.out.println("result" + result);
                   PrintWriter writer = response.getWriter();
                   writer.write(result);//返回json数组
                   writer.flush();
                   writer.close();
               }catch (Exception e){

               }

//
//        response.setContentType("application/json; charset=utf-8");
//        response.setCharacterEncoding("UTF-8");
//
//        String userJson = body;
//        OutputStream out = response.getOutputStream();
//        out.write(userJson.getBytes("UTF-8"));
//        out.flush();
//        try {
//            // Write some content
//
//        } finally {
//            out.close();
//        }
    }


}