package com.meizu.control;

import com.meizu.utils.PhoneReadyHelper;
import com.meizu.utils.RunCTSUtils;
import com.meizu.utils.SQLiteJDBC;
import com.meizu.utils.WebServiceBean;

import javax.swing.*;

/**
 * Created by wuchaolin on 2016-8-4.
 * 主类
 */
public class SanityTestCase {
    //    public static final ProPertyUtil property = new ProPertyUtil(/home/wuzhaolin/play-2.2.6/runPlay.sh"compareTest.properties");
    private static  boolean isRunCts;

    public static boolean isRunCts() {
        return isRunCts;
    }

    public static void setIsRunCts(boolean isRunCts) {
        SanityTestCase.isRunCts = isRunCts;
    }


    public static void main(String arge[]) throws Exception {
//        ADBUtils.getCommandResult("aapt v");


        SQLiteJDBC sj=new SQLiteJDBC();
        sj.setStatusAndTaskID("DevicesInfo","fsdf", "2", "233");
        RunCTSUtils runCTSUtils=new RunCTSUtils();
//        System.out.println(runCTSUtils.getSeesionId("","xts","2017.11.25_18.51.36"));



        String SN ="no";
        SN= JOptionPane.showInputDialog("是否只跑某个手机？\n" +
                "若是则输入该手机的SN号");
        System.out.println(SN);
        WebServiceBean wb=new WebServiceBean();
        if (wb.isRuning("DevicesInfo","79BQADQNFHNCW","1")){
            System.out.println("!!!!!有问题，还是报错！！！");
        }else {
            System.out.println("没有问题了！！！");
        }
        System.out.println("==--=-=-=-=---=:"+wb.isRuning("DevicesInfo","79BQADQNFHNCW","1"));



        if (sj.haveSNinfornName("DevicesInfo",SN)){
            System.out.println("=-=-=表格中存在该SN号码！！-=-=-");
            sj.setStatusAndTaskID("DevicesInfo",SN,"1","sdfsadfa");
        }else {
            System.out.println("=-=-=表格中不存在存在该SN号码！！-=-=-");
            sj.setStatusAndTaskID("DevicesInfo",SN,"1","no ext");
        }
        System.out.println("初始化以后的Status数据是！！！=-=-==-=-=:"+sj.getStatusAndTaskID("DevicesInfo",SN)[0]+
                "\n初始化以后的Taskid数据是！！！=-=-:"+sj.getStatusAndTaskID("DevicesInfo",SN)[1]);

        String aa=wb.getEveryDevicesInfo().toString();
        System.out.println("-------------读出的数组是：——————"+aa);
        System.out.println("--------------本机IP是：-------"+wb.getIp());

        Object[] obj1 ={ "CTS", "GTS", "VTS" };
        String XTS = (String) JOptionPane.showInputDialog(null,"请选择要跑的类型:\n",
                "测试类型", JOptionPane.PLAIN_MESSAGE, new ImageIcon("icon.png"),
                obj1, "");
        System.out.println(XTS);

        Object[] obj2 ={ "全跑", "64", "32" };
        String arm= (String) JOptionPane.showInputDialog(null,"请选择要跑的位数:\n",
                "测试位数", JOptionPane.PLAIN_MESSAGE, new ImageIcon("icon.png"),
                obj2, "");
        if (arm.equals("全跑")){
            arm="all";
        }
        System.out.println(arm);
        SN= JOptionPane.showInputDialog("是否只跑某个手机？\n" +
                "若是则输入该手机的SN号");
        System.out.println(SN);

        String firmPath;
        firmPath= JOptionPane.showInputDialog("是否需要固件升级？\n" +
                "若需要升级，请输入测试固件的完整路径\n" +
                "（如smb://172.16.1.98/firmware/DailyBuild4Test/DailyBuildS25/app/国内版/Marshmall" +
                "ow_S25-6.0/S25-m_base/user/20160926232101_I）" +
                "\n不需要升级请点取消");
        System.out.println(firmPath);

        String command;
        command= JOptionPane.showInputDialog("是否单跑某一条case？\n" +
                "若要单跑，请以‘模块#类#方法’的格式输入（用井号隔开）\n" +
                "（如 CtsAtraceHostTestCases#android.atrace.cts.AtraceHostTest#testCategories）" +
                "\n不需要升级请点取消");
        System.out.println(command);



        PhoneReadyHelper ph=new PhoneReadyHelper();
        ph.PhoneReady(SN,firmPath,XTS,"");

        RunCTSUtils cs=new RunCTSUtils();
        String[]  results=cs.GoRun(SN,arm,XTS,command,"00");
        System.out.println(results[0]);
        System.out.println(results[1]);
        System.out.println(results[2]);
        System.out.println(results[3]);
        System.out.println("设备："+SN+"的"+XTS+"测试已经完成！！");
        try{
            int failNum= Integer.parseInt(results[1]);
            int runModule= Integer.parseInt(results[2]);
            int totalModule= Integer.parseInt(results[3]);
            while (failNum>100||(runModule<totalModule)){
                for (int i=0;i<3;i++){
                    cs.retryCts(SN,cs.getSeesionId(SN,XTS,results[0]),XTS);

                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }


}




