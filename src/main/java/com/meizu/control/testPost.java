package com.meizu.control;

/**
 * Created by wuzhaolin on 17-11-28.
 */

import com.meizu.model.DevicesInfo;
import com.meizu.utils.DevicesUtils;
import com.meizu.utils.RunCTSUtils;
import com.meizu.utils.WebServiceBean;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class testPost extends HttpServlet {
    public static List<DevicesInfo> devicesInfos;

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException
    {

        response.setContentType("text/html;charset=UTF-8");
        final String SN = request.getParameter("SN");
        final String XTS=request.getParameter("XTS");
        final String arm=request.getParameter("arm");
        final String firmPath=request.getParameter("firmPath");
        final String command=request.getParameter("command");
        final String taskId=request.getParameter("taskId");

        WebServiceBean wb=new WebServiceBean();
        if (wb.isRuning("DevicesInfo",SN,taskId)) {//判断传入的SN号是否空闲，否则直接跳出
            if (wb.isXts(XTS,arm)) {//如果XTS和arm参数不是空的
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        setStatus(SN, "2", taskId);
                        try {
                            runXts.goXTS(SN, XTS, arm, firmPath, command, taskId);
                        } catch (IOException e) {
                            System.out.println(XTS + "测试运行出错！-------");
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            System.out.println(XTS + "测试运行出错！！！！！");
                            e.printStackTrace();
                        }
                        setStatus(SN, "1", taskId);
                    }
                });
                setHeadAndBody("GMSAutoTest", request.getContextPath() + "任务正在执行", request, response);

            }else {
                setHeadAndBody("error!", "XTS:"+XTS+",arm:"+arm+" XTS or arm is error！", request, response);
            }
        }else {
            setHeadAndBody("busy!","this"+SN+"is running！",request,response);
        }


    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
        //Do some other work
    }

    @Override
    public String getServletInfo() {
        return "GMSAutoTestTool";
    }

    public void setStatus(String SN,String status,String taskId ){
        RunCTSUtils runCTSUtils=new RunCTSUtils();
        if(runCTSUtils.haveSN(SN)){
            DevicesInfo devicesInfo = new DevicesInfo();
            devicesInfo.setSerialNumber(SN);
            devicesInfo.setmStatus(status);
            devicesInfo.setmTaskId(taskId);
            devicesInfos.add(devicesInfo);
        }else {
            List<String> devices= DevicesUtils.getDevicesSN();
            if (devices != null){
                for (String serialNumber : devices){
                    setStatus(serialNumber,status,taskId);
                }
            }
        }
    }

    public void setHeadAndBody(String head,String body,
                               HttpServletRequest request,
                               HttpServletResponse response) throws IOException {
//        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            // Write some content
            out.println("<html>");
            out.println("<head>");
            out.println("<title>"+head+"</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h2>" + body + "</h2>");
            out.println("</body>");
            out.println("</html>");
        } finally {
            out.close();
        }
    }
}