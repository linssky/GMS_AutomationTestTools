package com.meizu.utils;

import com.meizu.model.DevicesInfo;

import java.io.*;
import java.util.List;

/**
 * Created by wuchaolin on 17-11-5.
 */
public class copyResultAndLogs {


    //运行前先删除result和logs的所有内容
    public void delResultAndLog(){
        ADBUtils.getCommandResult("rm -rf ./GMSTools/SendServer/");
//        ADBUtils.getCommandResult("rm -rf ./android7/android-cts/logs");
//        ADBUtils.getCommandResult("rm -rf ./android71/android-cts/results");
//        ADBUtils.getCommandResult("rm -rf ./android71/android-cts/logs");
//        ADBUtils.getCommandResult("rm -rf ./SendServer");
    }

    //拷贝result和log文件到ResultAndLog和SendSever（此目录用来scp结果到服务器）
    public void copyResultAndLogs(String resultsName,String XTS,String SN,String command) {
        String ResultsName = resultsName.trim();//去掉首尾空格
        DevicesUtils DS = new DevicesUtils();
        List<DevicesInfo> Ds = DS.getDevicesInfo(SN);
        String DevicesModel=null;
        String AndroidVersion = null;
        String updateVersion=null;
        String AndroidFile=null;
            DevicesModel = Ds.get(0).getDevicesModel();
            AndroidVersion = Ds.get(0).getAndroidVersion();
            updateVersion = Ds.get(0).getUpdateVersion();


        String oldPathResult="";
        String oldPathLog="";

        String NewSeverResult="."+File.separator+"GMSTools"+File.separator+"SendServer"+File.separator+XTS.toUpperCase()
                +File.separator+DevicesModel+File.separator+updateVersion;//要保存的地址

        String NewSeverLog="."+File.separator+"GMSTools"+File.separator+"SendServer"+File.separator+XTS.toUpperCase()
                +File.separator+DevicesModel+File.separator+updateVersion;

        String NewPathResult="."+File.separator+"GMSTools"+File.separator+"ResultsAndLogs"+File.separator+XTS.toUpperCase()
                +File.separator+DevicesModel+File.separator+updateVersion;
                //要保存的地址
        String NewPathLog="."+File.separator+"GMSTools"+File.separator+"ResultsAndLogs"+File.separator+XTS.toUpperCase()
                +File.separator+DevicesModel+File.separator+updateVersion;

        if (command==null||command.trim().equals("")||command.toLowerCase().trim().equals("no")){
            NewPathResult=NewPathResult+File.separator+"ALL"+File.separator+ResultsName+File.separator+"results";
            NewPathLog=NewPathLog+File.separator+"ALL"+File.separator+ResultsName+File.separator +"logs";
            NewSeverResult=NewSeverResult+File.separator+"ALL"+File.separator+ResultsName+File.separator+"results";
            NewSeverLog=NewSeverLog+File.separator+"ALL"+File.separator+ResultsName+File.separator +"logs";
        }else {
            NewPathResult=NewPathResult+File.separator+command.toString().trim()+File.separator+ResultsName+File.separator+"results";
            NewPathLog=NewPathLog +File.separator+command.toString().trim()+File.separator+ResultsName+File.separator +"logs";
            NewSeverResult=NewSeverResult+File.separator+command.toString().trim()+File.separator+ResultsName+File.separator+"results";
            NewSeverLog=NewSeverLog+File.separator+command.toString().trim()+File.separator+ResultsName+File.separator +"logs";
        }


        if (XTS.toLowerCase().contains("cts")) {
            if (AndroidVersion.indexOf("7.0") != -1) {//-1表示没有
                AndroidFile = "." + File.separator+"GMSTools" +File.separator+ "android7";
            }
            if (AndroidVersion.indexOf("7.1") != -1) {
                AndroidFile = "." + File.separator+"GMSTools" +File.separator+ "android71";
            }
        }else {
            AndroidFile ="."+File.separator+"GMSTools"+File.separator+XTS.toLowerCase().trim();
        }
            oldPathResult=AndroidFile+File.separator
                    +"android-"+XTS.toLowerCase().trim()+File.separator+"results"+File.separator+ResultsName;

            oldPathLog=AndroidFile+File.separator
                    +"android-"+XTS.toLowerCase().trim()+File.separator+"logs"+File.separator+ResultsName;
        System.out.println(oldPathResult);

            copyF(NewPathResult,oldPathResult);
            copyF(NewPathLog,oldPathLog);

            copyF(NewSeverResult,oldPathResult);
            copyF(NewSeverLog,oldPathLog);

        try{//这样拷贝过后的结果文件就有权限了
            ADBUtils.getCommandResult("chmod -R 777 "+"."+File.separator+"GMSTools"+File.separator+"SendServer");
            ADBUtils.getCommandResult("chmod -R 777 "+"."+File.separator+"GMSTools"+File.separator+"ResultsAndLogs");
        }catch (Exception e){
            System.out.println("结果文件夹赋权失败！");
        }



    }


    public void sendto121(){
        try{
            ADBUtils.getCommandResult("."+ File.separator+"GMSTools"+File.separator+"copyResult.sh");
        }catch (Exception e){
            System.out.println("结果文件上传服务器失败");
        }

    }
    //拷贝文件夹内所有文件

    public void copyF(String newPath,String oldPath){
        File old=new File(oldPath);
        File fileList[]=old.listFiles();
        File news=new File(newPath);
        news.mkdirs();
        news.setWritable(true,false);
        news.setReadable(true,false);

        try {
            for (File filename : fileList) {
                if (filename.isDirectory()) {
                    copyF(newPath + File.separator + filename.getName(), oldPath + File.separator + filename.getName());
                } else {
                    try {
                        copyAToB(oldPath + File.separator + filename.getName(), newPath + File.separator + filename.getName());
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }catch (NullPointerException e){
            System.out.println("拷贝文件地址中有空指针！");
        }

    }


    //完成以后拷贝文件到指定目录
    public void copyAToB(String oldPath,String newPath) throws IOException {
        InputStream is = null;
        is = new FileInputStream(oldPath);
        FileOutputStream fos = new FileOutputStream(new File(newPath));
        byte[] buffer = new byte[1024];
        int byteCount = 0;
        while ((byteCount = is.read(buffer)) != -1) {//循环从输入流读取 buffer字节
            fos.write(buffer, 0, byteCount);//将读取的输入流写入到输出流
        }
        fos.flush();//刷新缓冲区
        is.close();
        fos.close();
    }
}
