package com.test.util;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFHyperlink;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class ExcelUtil
{
    private HSSFWorkbook workbook = null;
    private HSSFCellStyle headStyle = null;
    private HSSFCellStyle bodyStyle = null;
    private HSSFSheet sheet = null;
    private String[] header = null;
    
    public ExcelUtil(String filePath)
    {
        // TODO 读取excel
    }
    
    public ExcelUtil(String[] head)
    {
        // 创建新的工作簿
        workbook = new HSSFWorkbook();
        // 在工作簿中创建一工作表
        sheet = workbook.createSheet("sheet1");
        // 设置标题样式
        setHeadCellStyle();
        // 设置内容样式
        setBodyCellStyle();
        this.initHeader(head);
    }
    
    public void addRow(ExcelEntity entity, int rowNum) throws Exception
    {
        
        HSSFRow row = this.sheet.createRow(rowNum);
        
        if(this.header == null)
        {
            return;
        }
        
        for(int i = 0; i < header.length; i++)
        {
            HSSFCell cell = row.createCell(i);
            cell.setCellStyle(bodyStyle);
            Field field = null;
            String fieldVal = "";
            if(header[i].contains("-"))
            {
                fieldVal = header[i].split("[-]")[1].split("[|]")[0].trim();
                fieldVal = "exportPhaseDtos";
            }
            else
            {
                fieldVal = header[i].split("[|]")[0].trim();
            }
            try
            {
                field = entity.getClass().getSuperclass().getDeclaredField(fieldVal);
            }
            catch(Exception e)
            {
                field = entity.getClass().getDeclaredField(fieldVal);
            }
            field.setAccessible(true);
            if(field == null || field.get(entity) == null)
            {
                cell.setCellValue("");
                continue;
            }
            if(field.get(entity) instanceof Integer || field.get(entity) instanceof Float)
            {
                cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                cell.setCellValue(Float.valueOf(field.get(entity).toString()));
            }
            if(field.get(entity) instanceof List)
            {
                fieldVal = header[i].split("[-]")[1].split("[|]")[0].trim();
                
                @SuppressWarnings("unchecked")
                List<Object> list = (List<Object>) field.get(entity);
                for(Object obj : list)
                {
                    // XXX 待考证
//                    Field fieldout = entity.getClass().getDeclaredField("apply_id");
//                    Field fieldin1 = obj.getClass().getDeclaredField("apply_id");
//                    Field fieldin2 = obj.getClass().getDeclaredField("phase_number");
//                    String phase_number = header[i].split("[-]")[0].trim();
//                    if(fieldout.get(entity).equals(fieldin1.get(obj)) && phase_number.equals(fieldin2.get(obj).toString()))
//                    {
//                        field = obj.getClass().getDeclaredField(fieldVal);
//                        if(field.get(obj) instanceof Integer || field.get(obj) instanceof Float)
//                        {
//                            cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
//                            cell.setCellValue(Float.valueOf(field.get(obj).toString()));
//                        }
//                        else
//                        {
//                            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
//                            cell.setCellValue(field.get(obj).toString());
//                        }
//                    }
                }
            }
            else
            {
                String v = field.get(entity).toString();
                if(v.startsWith("http://")){
                	 cell.setCellValue("详情");
                	 HSSFHyperlink link = new HSSFHyperlink(HSSFHyperlink.LINK_URL);
                	 link.setAddress(v);
                	 cell.setHyperlink(link);// 设定单元格的链接
                }else{
                	 cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                	 cell.setCellValue(v);
                }
            }
        }
    }
    
    public HSSFRow addRow(int rowNum)
    {
        return this.sheet.createRow(rowNum);
    }
    
    public void autoSizeColumn()
    {
        if(this.header == null)
        {
            return;
        }
        for(int i = 0; i < header.length; i++)
        {
            this.sheet.autoSizeColumn(i);
        }
    }
    
    // 初始化报表的头信息
    private void initHeader(String[] head)
    {
        HSSFRow row = sheet.createRow(0);
        this.header = head;
        for(int i = 0; i < header.length; i++)
        {
            HSSFCell cell = row.createCell(i);
            // cell.setCellStyle(headStyle);
            cell.setCellType(HSSFCell.CELL_TYPE_STRING); // 设置列类型
            cell.setCellValue(header[i].split("[|]").length > 1 ? header[i].split("[|]")[1] : header[i].split("[|]")[0]); // 设置列值
        }
    }
    
    // 设置BODY样式
    private void setBodyCellStyle()
    {
        bodyStyle = workbook.createCellStyle();
        // 设置字体
        HSSFFont bodyFont = workbook.createFont();
        bodyFont.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
        // bodyFont.setFontName("宋体");
        bodyFont.setFontHeightInPoints(HSSFCellStyle.BORDER_DASH_DOT);
        bodyStyle = workbook.createCellStyle();
        bodyStyle.setFont(bodyFont);
        // 设置上边线为实线
        bodyStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        // 设置右边线为实线
        bodyStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        // 设置下边线为实线
        bodyStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        // 设置左边线为实线
        bodyStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        // 设置左右居中
        bodyStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        // 设置上下居中
        bodyStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
    }
    
    // 设置HEAD样式
    private void setHeadCellStyle()
    {
        // 设置单元格样式
        headStyle = workbook.createCellStyle();
        // 设置上下居中
        headStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        // 设置左右居中
        headStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        // 设置下边线为实线
        headStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        // 设置右边线为实现
        headStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        // 设置自动换行
        headStyle.setWrapText(true);
        // 设置字体
        HSSFFont font = workbook.createFont();
        // 设置字体大小
        font.setFontHeightInPoints((short) 14);
        // 将设置好的字体加入样式
        headStyle.setFont(font);
    }
    
    public void write(OutputStream out) throws Exception
    {
        workbook.write(out);
    }
    
    public void write(String fileName) throws Exception
    {
        FileOutputStream output = new FileOutputStream(fileName);
        workbook.write(output);
        output.flush();
        output.close();
    }
    
}
