package com.example.storemanagement.tool;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.storemanagement.entity.Clothes;
import com.example.storemanagement.entity.ClothesFactory;
import com.example.storemanagement.entity.ClothesOrder;
import com.example.storemanagement.entity.Factory;
import com.example.storemanagement.entity.Inventory;
import com.example.storemanagement.entity.Order;
import com.example.storemanagement.entity.Product;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jxl.*;
import jxl.read.biff.BiffException;
import jxl.write.*;

//编写excel工具类
public class ExcelUtil {

    private static WritableFont arial14font = null;

    private static WritableCellFormat arial14format = null;
    private static WritableFont arial10font = null;
    private static WritableCellFormat arial10format = null;
    private static WritableFont arial12font = null;
    private static WritableCellFormat arial12format = null;
    private final static String UTF8_ENCODING = "UTF-8";

    /**
     * 单元格的格式设置 字体大小 颜色 对齐方式、背景颜色等...
     */
    private static void format() {
        try {
            arial14font = new WritableFont(WritableFont.ARIAL, 14, WritableFont.BOLD);
            arial14font.setColour(jxl.format.Colour.LIGHT_BLUE);
            arial14format = new WritableCellFormat(arial14font);
            arial14format.setAlignment(jxl.format.Alignment.CENTRE);
            arial14format.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);
            arial14format.setBackground(jxl.format.Colour.VERY_LIGHT_YELLOW);

            arial10font = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD);
            arial10format = new WritableCellFormat(arial10font);
            arial10format.setAlignment(jxl.format.Alignment.CENTRE);
            arial10format.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);
            arial10format.setBackground(Colour.GRAY_25);

            arial12font = new WritableFont(WritableFont.ARIAL, 10);
            arial12format = new WritableCellFormat(arial12font);
            //对齐格式
            arial10format.setAlignment(jxl.format.Alignment.CENTRE);
            //设置边框
            arial12format.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);

        } catch (WriteException e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化Excel
     *
     * @param fileName 导出excel存放的地址（目录）
     * @param colName  excel中包含的列名（可以有多个）
     */
    public static void initExcel(String fileName, String[] colName) {
        format();
        WritableWorkbook workbook = null;
        try {
            File file = new File(fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
            workbook = Workbook.createWorkbook(file);
            //设置表格的名字
            WritableSheet sheet = workbook.createSheet("盘点清单", 0);
            //创建标题栏
            sheet.addCell((WritableCell) new Label(0, 0, fileName, arial14format));
            for (int col = 0; col < colName.length; col++) {
                sheet.addCell(new Label(col, 0, colName[col], arial10format));
            }
            //设置行高
            sheet.setRowView(0, 340);
            workbook.write();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (workbook != null) {
                try {
                    workbook.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> void writeObjListToExcel(List<Inventory> objList, String fileName, Context c) {
        if (objList != null && objList.size() > 0) {
            WritableWorkbook writebook = null;
            InputStream in = null;
            try {
                WorkbookSettings setEncode = new WorkbookSettings();
                setEncode.setEncoding(UTF8_ENCODING);
                in = new FileInputStream(new File(fileName));
                Workbook workbook = Workbook.getWorkbook(in);
                writebook = Workbook.createWorkbook(new File(fileName), workbook);
                WritableSheet sheet = writebook.getSheet(0);

                for (int j = 0; j < objList.size(); j++) {
                    Inventory inventory = (Inventory) objList.get(j);
                    List<String> list = new ArrayList<>();
                    String curId, oriId;
                    int curNum, oriNum;
                    curId = inventory.getCurId();
                    oriId = inventory.getOriId();
                    curNum = inventory.getCurNumber();
                    oriNum = inventory.getOriNumber();

                    list.add(curId);
                    if (curId.equals(oriId)) {
                        int rest = curNum - oriNum;
                        if (rest >= 0)
                            list.add("+" + rest);
                        else list.add(rest + "");
                    } else {  //id不同
                        list.add("+" + curNum);
                    }

                    for (int i = 0; i < list.size(); i++) {
                        sheet.addCell(new Label(i, j + 1, list.get(i), arial12format));
                        if (list.get(i).length() <= 4) {
                            //设置列宽
                            sheet.setColumnView(i, list.get(i).length() + 8);
                        } else {
                            //设置列宽
                            sheet.setColumnView(i, list.get(i).length() + 5);
                        }
                    }
                    //设置行高
                    sheet.setRowView(j + 1, 350);
                }

                writebook.write();
                Toast.makeText(c, "导出Excel成功", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (writebook != null) {
                    try {
                        writebook.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        }
    }


    public static <T> ArrayList<Product> readObjListFromExcel(String path, Context c) {
        ArrayList<Product> list = new ArrayList<>();
        Factory factory = new ClothesFactory();
            try {
                jxl.Workbook writebook = null;
            InputStream is = new FileInputStream(path);
            writebook = Workbook.getWorkbook(is);

            int sheetSize = writebook.getNumberOfSheets();
            Sheet[] sheets=writebook.getSheets();
            for(int i=0;i<sheets.length;i++) {
                Sheet sheet =sheets[i];
                // Sheet sheet = writebook.getSheet(0);
                int row_total = sheet.getRows();

                for (int j = 1; j < row_total; j++) {
                    Cell[] cells = sheet.getRow(j);
                    list.add(factory.produce(cells[0].getContents(), Integer.parseInt(cells[1].getContents())));
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }


    public ArrayList<Order> readOrderExcel(String path) {// ,File file
        Map<String,Order> orderlist = new HashMap<>();
        Factory factory = new ClothesFactory();
        try {
            jxl.Workbook writebook = null;
            InputStream is = new FileInputStream(path);
            writebook = Workbook.getWorkbook(is);

            int sheetSize = writebook.getNumberOfSheets();
            Sheet[] sheets=writebook.getSheets();
            for(int i=0;i<sheets.length;i++) {
                Sheet sheet =sheets[i];
                // Sheet sheet = writebook.getSheet(0);
                int row_total = sheet.getRows();
                String olr_orderId=null;
                Order order=null;
                for (int j = 1; j < row_total; j++) {
                    Cell[] cells = sheet.getRow(j);
                    String orderId = cells[0].getContents();
                    String id = cells[1].getContents();
                    String number =cells[2].getContents();

                    if(orderId==null || orderId.equals("")){//该订单id已加入orderlist
                        orderlist.get(olr_orderId).getOderList().add(factory.produce(id,Integer.parseInt(number)));
                    }else{ //该订单id还未加入orderlist
                        ArrayList<Product> clothes=new ArrayList<>();
                        clothes.add(factory.produce(id,Integer.parseInt(number)));
                        order=new ClothesOrder(orderId,clothes);
                        orderlist.put(orderId,order);
                        olr_orderId = orderId;
                    }

                    int m=0;
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }

        ArrayList<Order> orders = new ArrayList<>();

        for (Order value : orderlist.values()) {
            orders.add(value);
        }

        return orders;

        /*
        InputStream is = new FileInputStream(path);
        Workbook wb = Workbook.getWorkbook(is);// 从文件流中取得Excel工作区对象
        Sheet sheet = wb.getSheet(0);

        //System.out.println(file.getName());
        String name = sheet.getName();
        int rowNumber = sheet.getRows();
        int columnNumber = sheet.getColumns();

        Range[] rangeCell = sheet.getMergedCells();

        for (int i = 0; i < sheet.getRows(); i++) {
            for (int j = 0; j < sheet.getColumns(); j++) {
                String str = null;
                str = sheet.getCell(j, i).getContents();
                for (Range r : rangeCell) {
                    if (i > r.getTopLeft().getRow()
                            && i <= r.getBottomRight().getRow()
                            && j >= r.getTopLeft().getColumn()
                            && j <= r.getBottomRight().getColumn()) {
                        str = sheet.getCell(r.getTopLeft().getColumn(),
                                r.getTopLeft().getRow()).getContents();
                    }
                }
               int m=0;
            }
           // System.out.println();
        }
        wb.close();

         */
    }




}

