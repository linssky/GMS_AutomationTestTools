package com.meizu.utils;

import com.meizu.model.DevicesInfo;
import com.meizu.model.FilePathInfo;

import java.io.File;
import java.util.List;

/**
 * Created by wuchaolin on 17-11-21.
 */
public class FilePathUtils {
    public FilePathInfo getFilePathInfo(String SN,String XTS){
        FilePathInfo filePathInfo=new FilePathInfo();
        String runFile=getOldFile(SN,XTS)+"tools"+File.separator+XTS.toLowerCase().trim()+"-tradefed";
        String oldResultPath=getOldFile(SN,XTS)+"results"+File.separator;
        String oldLogPath=getOldFile(SN,XTS)+"logs"+File.separator;
        String newResultPath=getNewFile(SN,XTS)+"results"+File.separator;
        String newLogPath=getNewFile(SN,XTS)+"logs"+File.separator;


        filePathInfo.setOldLogPath(oldLogPath);
        filePathInfo.setOldResultPath(oldResultPath);
        filePathInfo.setRunXtsPath(runFile);
        filePathInfo.setNewResultPath(newResultPath);
        filePathInfo.setNewLogPath(newLogPath);
        return filePathInfo;
    }


    public String getOldFile(String SN,String Xts){
        String runFile=null;
        if (Xts.toLowerCase().contains("cts"))
        {
            DevicesUtils ds=new DevicesUtils();
            List<DevicesInfo> DS=ds.getDevicesInfo(SN.trim());
            String androidVersion=DS.get(0).getAndroidVersion();
            if (androidVersion.toLowerCase().trim().contains("7.0")){
                runFile="."+ File.separator+"GMSTools"+File.separator+"android7"+File.separator+"android-cts"+File.separator;
            }
            if (androidVersion.toLowerCase().trim().contains("7.1")) {
                runFile = "." + File.separator +"GMSTools"+File.separator+ "android71" + File.separator + "android-cts" + File.separator;
            }
        }else {
            runFile = "." + File.separator+"GMSTools"+File.separator+Xts.toLowerCase().trim()+File.separator + "android-" + Xts.toLowerCase().trim() + File.separator;
        }

        return runFile;
    }

    public String getNewFile(String  SN,String XTS){
        String file=null;
        DevicesUtils ds=new DevicesUtils();
        List<DevicesInfo> DS=ds.getDevicesInfo(SN.trim());
        file="."+File.separator+"GMSTools"+File.separator+"ResultsAndLogs"+File.separator+XTS.toUpperCase().trim()
                +File.separator+"GMSTools"+File.separator+DS.get(0).getDevicesModel()+File.separator+
                DS.get(0).getUpdateVersion().trim()+File.separator;
        return file;
    }
}
