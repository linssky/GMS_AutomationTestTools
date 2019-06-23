package com.meizu.utils;//package com.meizu.utils;
//
//import com.meizu.model.TaskInfo;
//import org.apache.poi.hssf.usermodel.HSSFRow;
//import org.apache.poi.hssf.usermodel.HSSFSheet;
//import org.apache.poi.hssf.usermodel.HSSFWorkbook;
//import org.apache.poi.sl.usermodel.Sheet;
//import org.apache.poi.ss.formula.functions.T;
//import org.apache.poi.ss.usermodel.Row;
//import org.apache.poi.ss.usermodel.Workbook;
//import org.apache.poi.xssf.usermodel.XSSFCell;
//import org.apache.poi.xssf.usermodel.XSSFRow;
//import org.apache.poi.xssf.usermodel.XSSFSheet;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//
//import java.io.*;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Created by zengliang on 2017/6/1.
// */
//public class ExcelUtil {
//
//    public static void main(String[] args) {
//        long startTime = System.currentTimeMillis();
//
//        long endTime = System.currentTimeMillis();
//        System.out.println(String.format("Time: %d ", endTime - startTime));
//
//    }
//
//    /**
//     * 从Excel中读取Task信息
//     * @param fileName Excel文件完整路径
//     * @param sheetIndex 数据所在Excel表中的第几个Sheet表中
//     * @return
//     */
//    public List<TaskInfo> readExcelForTaskInfo(String fileName, int sheetIndex) {
//        Workbook workbook = null;
//        List<TaskInfo> taskInfoList = null;
//        String excelType = this.getExcelType(fileName);
//        if (excelType != null){
//            try {
//                if (excelType.equals("2007")){//07版excel
//                    workbook = new XSSFWorkbook(fileName);
//                    XSSFSheet xssfSheet = (XSSFSheet) workbook.getSheetAt(sheetIndex);
//                    List<XSSFRow> xssfRowList = this.getXSSFRows(xssfSheet);
//                    taskInfoList = this.readTaskInfoByXlsxExcel(xssfRowList);
//                }else{//03版excel
//                    workbook = new HSSFWorkbook(new FileInputStream(fileName));
//                    HSSFSheet hssfSheet = (HSSFSheet) workbook.getSheetAt(sheetIndex);
//                    List<HSSFRow> hssfRowList = this.getHSSFRows(hssfSheet);
//                    taskInfoList = this.readTaskInfoByXlsExcel(hssfRowList);
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }finally {
//                try {
//                    if (null != workbook){
//                        workbook.close();
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        return taskInfoList;
//    }
//
//    /**
//     * 从2007Excel中读取Task信息
//     * @param xssfRowList Excel表中获取的XSSFRow集合
//     * @return 返回Task信息集合
//     */
//    private List<TaskInfo> readTaskInfoByXlsxExcel(List<XSSFRow> xssfRowList){
//        List<TaskInfo> taskInfoList = new ArrayList<>();
//        if (!xssfRowList.isEmpty()){
//            int type = xssfRowList.get(0).getLastCellNum();
//            for (XSSFRow xssfRow : xssfRowList){
//                TaskInfo taskInfo = new TaskInfo();
//                switch (type){
//                    case 3://excel表中只有模块
//                        //表格中为公式，想获取其数值xssfCell.getNumericCellValue()
//                        taskInfo.setMethodName(xssfRow.getCell(2).toString());
//
//                    case 2://excel表中含有模块跟类
//                        taskInfo.setClassName(xssfRow.getCell(1).toString());
//
//                    case 1://excel表中含有模块、类、方法
//                        taskInfo.setModularName(xssfRow.getCell(0).toString());
//
//                        break;
//                    default:
//
//                        break;
//                }
//                taskInfoList.add(taskInfo);
//            }
//        }
//        return taskInfoList;
//    }
//
//    /**
//     * 从2003Excel中读取Task信息
//     * @param hssfRowList Excel表中获取的HSSFRow集合
//     * @return 返回Task信息集合
//     */
//    private List<TaskInfo> readTaskInfoByXlsExcel(List<HSSFRow> hssfRowList){
//        List<TaskInfo> taskInfoList = new ArrayList<>();
//        if (!hssfRowList.isEmpty()){
//            int type = hssfRowList.get(0).getLastCellNum();
//            for (HSSFRow hssfRow : hssfRowList){
//                TaskInfo taskInfo = new TaskInfo();
//                switch (type){
//                    case 3://excel表中只有模块
//                        //表格中为公式，想获取其数值xssfCell.getNumericCellValue()
//                        taskInfo.setMethodName(hssfRow.getCell(2).toString());
//
//                    case 2://excel表中含有模块跟类
//                        taskInfo.setClassName(hssfRow.getCell(1).toString());
//
//                    case 1://excel表中含有模块、类、方法
//                        taskInfo.setModularName(hssfRow.getCell(0).toString());
//
//                        break;
//                    default:
//
//                        break;
//                }
//                taskInfoList.add(taskInfo);
//            }
//        }
//        return taskInfoList;
//    }
//
//    /**
//     * 从2007Excel中读取Task信息
//     * @param xssfSheet Excel表中获取的XSSFSheet表
//     * @return 返回Excel表中获取的XSSFRow集合
//     */
//    private List<XSSFRow> getXSSFRows(XSSFSheet xssfSheet){
//        int rowCount = 0;
//        List<XSSFRow> xssfRowList = new ArrayList<>();
//        XSSFRow xssfRow = xssfSheet.getRow(rowCount);
//        while (xssfRow != null){
//            rowCount++;
//            xssfRowList.add(xssfRow);
//            xssfRow = xssfSheet.getRow(rowCount);
//        }
//        return xssfRowList;
//    }
//
//    /**
//     * 从2003Excel中读取Task信息
//     * @param hssfSheet Excel表中获取的HSSFSheet表
//     * @return 返回Excel表中获取的HSSFRow集合
//     */
//    private List<HSSFRow> getHSSFRows(HSSFSheet hssfSheet){
//        int rowCount = 0;
//        List<HSSFRow> hssfRowList = new ArrayList<>();
//        HSSFRow hssfRow = hssfSheet.getRow(rowCount);
//        while (hssfRow != null){
//            rowCount++;
//            hssfRowList.add(hssfRow);
//            hssfRow = hssfSheet.getRow(rowCount);
//        }
//        return hssfRowList;
//    }
//
//    /**
//     * 跟据文件名创建Excel表
//     * @param fileName 需要创建的文件完整路径
//     * @return 创建成功返回True
//     */
//    public boolean createExcel(String fileName) {
//        boolean isCreate = false;
//        Workbook workbook = null;
//        FileOutputStream outputStream = null;
//        Sheet sheet = null;
//        try {
//            String excelType = this.getExtensionName(fileName);
//            if (excelType.equals("2007")){
//                workbook = new XSSFWorkbook();
//            }else if (excelType.equals("2003")){
//                workbook = new HSSFWorkbook();
//            }
//            outputStream = new FileOutputStream(fileName);
//            workbook.write(outputStream);
//            isCreate =  new File(fileName).exists();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }finally {
//            try {
//                if (null != workbook){
//                    workbook.close();
//                }
//                if (null != outputStream) {
//                    outputStream.flush();
//                    outputStream.close();
//                }
//                if (null != workbook){
//                    workbook.close();
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//        return isCreate;
//    }
//
//    /**
//     * 根据文件完整路径获取Excel表类型
//     * @param fileName 文件完整路径
//     * @return 返回Excel表类型
//     */
//    private String getExcelType(String fileName){
//        try {
//            new XSSFWorkbook(new FileInputStream(fileName));
//            return "2007";
//        } catch (Exception e) {
//            try {
//                new HSSFWorkbook(new FileInputStream(fileName));
//                return "2003";
//            } catch (Exception e1) {
//                e1.printStackTrace();
//                return null;
//            }
//        }
//    }
//
//    /**
//     * 根据文件完整路径获取文件扩展名
//     * @param filename 文件完整路径
//     * @return 返回文件扩展名
//     */
//    private String getExtensionName(String filename) {
//        if ((filename != null) && (filename.length() > 0)) {
//            int dot = filename.lastIndexOf('.');
//            if ((dot >-1) && (dot < (filename.length() - 1))) {
//                return filename.substring(dot + 1);
//            }
//        }
//        return filename;
//    }
//
//    private void writeTaskInfoInExcel(List<TaskInfo> taskInfoList, String fileName){
//
//    }
//
////    public boolean updateExcel(String fileName,String sheetName, int row, int column, Object text){
////        boolean isUpdate = false;
////        try {
////            FileInputStream fileInputStream = new FileInputStream(fileName);
////            XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
////            XSSFSheet sheet = workbook.getSheet(sheetName);
////            FileOutputStream fileOutputStream = null;
////            if(sheet == null){
////                sheet = workbook.createSheet(sheetName);
////                fileOutputStream = new FileOutputStream(fileName);
////                workbook.write(fileOutputStream);
////            }
////            XSSFRow xssfRow = sheet.getRow(row);
////            if (xssfRow == null){
////                xssfRow = sheet.createRow(row);
////            }
////            XSSFCell xssfCell = xssfRow.getCell(column);
////            if (xssfCell == null){
////                xssfCell = xssfRow.createCell(column);
////            }
////            xssfCell.setCellValue(text.toString());
////            fileInputStream.close();
////            fileOutputStream = new FileOutputStream(fileName);
////            workbook.write(fileOutputStream);
////            fileOutputStream.flush();
////            fileOutputStream.close();
////            workbook.close();
////        } catch (Exception e) {
////            e.printStackTrace();
////            return isUpdate;
////        }
////        String presentValue = this.readExcelToString(fileName, sheetName, row, column);
////        isUpdate = text.toString().equals(presentValue);
////        return isUpdate;
////    }
////
////
////    public void hiddenSheet(String targetFile, String sheetName) {
////        try {
////            FileInputStream fis = new FileInputStream(targetFile);
////            XSSFWorkbook wb = new XSSFWorkbook(fis);
////            //隐藏Sheet
////            wb.setSheetHidden(wb.getSheetIndex(sheetName), 1);
////            FileOutputStream fileOut = new FileOutputStream(targetFile);
////            wb.write(fileOut);
////            fileOut.flush();
////            fileOut.close();
////            fis.close();
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
////    }
////
////
////    public void deleteSheet(String fileName, int sheetIndex) {
////        FileInputStream fileInputStream = null;
////        try {
////            fileInputStream = new FileInputStream(fileName);
////            XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
////            //删除Sheet
////            int sheetNums = workbook.getNumberOfSheets();
////            if (sheetNums > 3){
////                workbook.removeSheetAt(sheetIndex);
////                FileOutputStream fileOutputStream = new FileOutputStream(fileName);
////                workbook.write(fileOutputStream);
////                fileOutputStream.flush();
////                fileOutputStream.close();
////                fileInputStream.close();
////            }
////        } catch (FileNotFoundException e) {
////            e.printStackTrace();
////        } catch (IOException e) {
////            e.printStackTrace();
////        }
////    }
////
////    public void deleteRowAndMove(String fileName, String sheetName, int row){
////        try {
////            FileInputStream fileInputStream = new FileInputStream(fileName);
////            XSSFWorkbook xssfWorkbook = new XSSFWorkbook(fileInputStream);
////            XSSFSheet xssfSheet = xssfWorkbook.getSheet(sheetName);
////            int rowCount = xssfSheet.getLastRowNum();
////            if (row < rowCount ){
////                xssfSheet.shiftRows(row, rowCount, -1);
////            }else {
////                xssfSheet.shiftRows(row, row, -1);
////            }
////            FileOutputStream fileOutputStream = new FileOutputStream(fileName);
////            xssfWorkbook.write(fileOutputStream);
////            fileInputStream.close();
////            fileOutputStream.close();
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
////    }
//
//}
