package com.meizu.utils;

import com.meizu.model.DevicesInfo;
import com.meizu.model.PostInfo;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.URL;
import java.net.URLConnection;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import static java.lang.System.out;


/**
 * Created by wuchaolin on 17-11-29.
 */
public class WebServiceBean {

    public void doWork() {
        out.println("心跳汇报……………………");

        String aa="";
        try {
            aa = getEveryDevicesInfo().toString();
        }catch (Exception e){
            System.out.println("心跳汇报获取手机状态异常！");
        }

        out.println("-----post的数据是："+aa);
        String sr=sendPost(PostInfo.heart,"devices="+aa);
        out.println(sr);
    }




    /**
     * 向指定URL发送GET方法的请求
     *
     * @param url
     *            发送请求的URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendGet(String url, String param) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                out.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }



    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url
     *            发送请求的 URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            out.println("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }

    /**根据网卡取本机配置的IP**/
    public  String getIp() {
        Enumeration<NetworkInterface> netInterfaces = null;
        try {
            netInterfaces = NetworkInterface.getNetworkInterfaces();
            while (netInterfaces.hasMoreElements()) {
                NetworkInterface ni = netInterfaces.nextElement();
                out.println("DisplayName:" + ni.getDisplayName());
                out.println("Name:" + ni.getName());
                Enumeration<InetAddress> ips = ni.getInetAddresses();
                while (ips.hasMoreElements()) {
                    out.println("IP:" + ips.nextElement().getHostAddress());
                    return ips.nextElement().getHostAddress();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String jsMess(String TaskId, String Status, String detail) {
        JSONArray userArray = new JSONArray();
        JSONObject user = new JSONObject();
        try {
            user.put("task_id", TaskId);
            user.put("status", Status);
            user.put("message", detail);
            userArray.put(user);
        } catch (JSONException e) {
            e.printStackTrace();
            System.out.println("sendTaskPost异常");
        }
//    String aa = userArray.toString();
        String aa = user.toString().split("\\{")[1].split("\\}")[0];
        return aa;
    }

    public static void sendStutaPost(String TaskId, String Status, String detail){
        PostInfo pi=new PostInfo();
        String sr="sendPost error!";
        try {
            sr = sendPost(pi.runStatus, "task_id="+TaskId+"&status="+Status+"&message="+detail);
            System.out.println("post:"+pi.runStatus+"task_id="+TaskId+"&status="+Status+"&message="+detail);
        }catch (Exception e){
        }
        out.println(sr);
    }

    /**获取所有设备的信息**/
    public String getEveryDevicesInfo(){
        SQLiteJDBC sj=new SQLiteJDBC();
        String ip=getIp();
        DevicesUtils devicesUtils=new DevicesUtils();
        List<DevicesInfo> devicesInfos=devicesUtils.getDevicesInfo("");
        String deviceInfo = null;
        int nub=devicesInfos.size();
        JSONArray userArray = new JSONArray();
        for (int i=0;i<nub;i++){
            JSONObject user = new JSONObject();
            String im1=devicesInfos.get(i).getDevicesImei();
            try {
                user.put("haveSIM",devicesInfos.get(i).getSim());
                user.put("ip",ip.trim());
                user.put("model",devicesInfos.get(i).getDevicesModel().trim());
                user.put("imei",im1.toString());
                user.put("sn", devicesInfos.get(i).getSerialNumber().trim());
                user.put("flyme","7");//暂定，还没有获取办法
                user.put("firmware", devicesInfos.get(i).getUpdateVersion().trim());
                user.put("androidversion", devicesInfos.get(i).getAndroidVersion().trim());
                user.put("status",sj.getStatusAndTaskID("DevicesInfo",devicesInfos.get(i).getSerialNumber())[0].trim());
                user.put("taskid",sj.getStatusAndTaskID("DevicesInfo",devicesInfos.get(i).getSerialNumber())[1].trim());
                userArray.put(user);
            } catch (JSONException e) {
                out.println("------添加信息失败-----");
                e.printStackTrace();
            }


        }
        deviceInfo=userArray.toString();
        return deviceInfo;
    }




    //判断传入的SN是否有空闲机器
    public boolean haveRuning(String formName,String SN,String taskId){
        boolean havaRuning=true;//默认空闲
            havaRuning=isRuning(formName,SN,taskId);

        return havaRuning;
    }
    //对于不抛出异常的递归处理方法
    public static class StopMsgException extends RuntimeException {
    }

    //判断传入的SN号是否有空闲
    public boolean isRuning(String formName,String SN,String TaskId){
        boolean isruning=true;//默认空闲
        RunCTSUtils runc=new RunCTSUtils();
        SQLiteJDBC sj=new SQLiteJDBC();

        if (!SN.toString().trim().toLowerCase().equals("all")) {//如果传入的不是all
            if (runc.haveSN(SN)) {//传入的SN号存在
                System.out.println("传入的SN是在线的");
                if (sj.haveSNinfornName(formName, SN)) {//如果在sql中存在
                    if (sj.getStatusAndTaskID(formName, SN)[0].toString().trim().equals("2")) {//如果是忙碌
                        System.out.println("状态是忙碌的2");
                        isruning = false;
                        sendStutaPost(TaskId,"-1","该手机处于忙碌状态");
                        return isruning;
                    }
                }
            } else {//不存在直接跳出
                System.out.println("传入的sn号不存在！！！");
                isruning = false;
                sendStutaPost(TaskId,"-1","该手机不在线，请检查对应手机连接是否正常！");
                return isruning;
            }
        }else {//是all的话
            if (sj.haveAnyBusy(formName)){//如果有忙碌状态的手机
                isruning=false;
                sendStutaPost(TaskId,"-1","该电脑有手机正在执行任务，无法全跑！");
                return isruning;
            }
            if(!runc.isSanmeModel()){//或者手机型号不一致，无法全跑
                isruning=false;
                sendStutaPost(TaskId,"-1","该电脑的手机不是同一型号，无法全跑！");
                return isruning;

            }

        }

        return isruning;
    }

    public boolean isXts(String XTS,String arm){
        boolean isok=true;
        if (XTS!=null&&arm!=null){
            String xts=XTS.toLowerCase().trim();
            if (xts.equals("cts")||xts.equals("gts")||xts.equals("vts")){

            }else {
                isok=false;
            }
        }else {
            isok=false;
        }
        return isok;
    }
}
