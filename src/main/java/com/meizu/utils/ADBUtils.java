package com.meizu.utils;

import com.meizu.model.GMSTaskResultInfo;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by wuchaolin on 2017/5/23.
 */
public class ADBUtils {

    /**
     * 利用Split截取指定字符串之间的字符
     * @param target 需要截取的原字符串
     * @param startReg 开始位置
     * @param overReg 结束位置
     * @return 返回所截取的字符串
     */
    public static String getCentreStringBySplit(String target, String startReg, String overReg) {
        try {
            String[] split1 = target.split(startReg);
            return split1[1].split(overReg)[0];
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 利用java执行cmd命令
     * @param command 需要执行的命令
     * @return 返回执行结果
     */
    public static String getCommandResult(String command){
        String commandResult = null;
        Process process = null;
        StringBuilder builder = new StringBuilder();
        BufferedInputStream inputStream = null;
        BufferedReader reader = null;
        try {
            process = Runtime.getRuntime().exec(command);
            inputStream = new BufferedInputStream(process.getInputStream());
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line = null;
            while ((line = reader.readLine()) != null) {
                builder.append(line).append(System.getProperty("line.separator", "\n"));
//                System.out.println(line);
            }
            commandResult = builder.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if (null != process){
                    process.destroy();
                }

                if (null != inputStream) {
                    inputStream.close();
                }

                if (null != reader){
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return commandResult;
    }


    /**
     * 获取seesionID
     * @param command 需要执行的命令
     * @return 返回执行结果
     */
    public static String getCommandSeesionId(String command,String ResultDirectory){
        String commandResult = null;
        Process process = null;
        StringBuilder builder = new StringBuilder();
        BufferedInputStream inputStream = null;
        BufferedReader reader = null;
        try {
            process = Runtime.getRuntime().exec(command);
            inputStream = new BufferedInputStream(process.getInputStream());
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line = null;

            out:
            while ((line = reader.readLine()) != null) {

                System.out.println(line);
                if(line.contains(ResultDirectory.trim())){
                    commandResult = line.split(" ")[0].trim();
                    break out;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if (null != process){
                    process.destroy();
                }

                if (null != inputStream) {
                    inputStream.close();
                }

                if (null != reader){
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return commandResult;
    }

    private String getStringBetweenString(String target, String startReg, String overReg) {
        String result = "";
        String regEx= startReg + "(.*?)" + overReg;
        Pattern pattern = Pattern.compile(regEx);
        Matcher m = pattern.matcher(target);
        if (m.find()){
            result = m.group(1);
        }
        return result;
    }


    /**
     * 利用java执行cmd命令
     * @param command 需要执行的命令
     * @return 返回执行结果数组，1是结果路径，2是fail数量，3是已跑模块，4是总模块,5是pass数量
     */
    public static String[] getGmsResult(String command){
        String commandResult = null;
        Process process = null;
        StringBuilder builder = new StringBuilder();
        BufferedInputStream inputStream = null;
        BufferedReader reader = null;
        String[] aa=new String[5];
        try {
            process = Runtime.getRuntime().exec(command);
            inputStream = new BufferedInputStream(process.getInputStream());
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line = null;
            String result=null;
            List<GMSTaskResultInfo> GMSTaskResultInfoList = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
                try {
                    if (line.contains("ResultReporter")) {
                        builder.append(line).append(System.getProperty("line.separator", "\n"));
                    }else {
                        GMSTaskResultInfo gmsTaskResultInfo = GMSThreadHelper.analysisResultOfAndroid7(line);
                        GMSTaskResultInfoList.add(gmsTaskResultInfo);
                        if (GMSTaskResultInfoList.size() > 100){
                            // TODO: 2018/2/2 开启线程，获取数据保存进数据库
                            new Thread(new Runnable() {
                                @Override
                                public void run() {

                                }
                            }).start();
                            GMSTaskResultInfoList.clear();
                        }
                    }
                }catch (Exception e){
                    System.out.println(e.toString());
                }
                System.out.println(line);
            }
            commandResult = builder.toString();


            Calendar cal = Calendar.getInstance();
            int month=cal.get(Calendar.MONTH) + 1;
            String mouths=Integer.toString(month);

//            String resultName=getCentreStringBySplit(commandResult,"ts/results/",mouths+"-");
            String resultName=getCentreStringBySplit(commandResult,"ts/results/","\n");

            String failnum=getCentreStringBySplit(commandResult,"FAILED:",",").trim();//获取失败用例总数

            String passnum=getCentreStringBySplit(commandResult,"PASSED:",",").trim();//获取成功用例总数

            String runMoudle=getCentreStringBySplit(commandResult,"MODULES:","of").trim();//跑完的模块数


            String totalMoudle=getCentreStringBySplit(commandResult,"of","\n").trim();//总模块数

            aa[0]=resultName.trim();

            aa[1]=failnum.trim();

            aa[2]=runMoudle.trim();

            aa[3]=totalMoudle.trim();

            aa[4]=passnum.trim();

        } catch (Exception e) {
            aa[0]="crash";
            aa[1]="测试异常中断,请检查测试包版本，是否需要更新解析逻辑！";
        }finally {
            try {
                if (null != process){
                    process.destroy();
                }

                if (null != inputStream) {
                    inputStream.close();
                }

                if (null != reader){
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return aa;
    }
}
