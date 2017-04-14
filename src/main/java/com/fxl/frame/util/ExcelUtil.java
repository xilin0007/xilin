/*package com.fxl.frame.util;

import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.NumberFormats;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WriteException;

*//**
 * jxl Excel类
 * ClassName: ExcelUtil 
 * @Description: TODO
 * @author fangxilin
 * @date 2016-8-03
 *//*

public class ExcelUtil {
	*//** 
     * 表头单元格样式的设定 
     *//*  
    public static WritableCellFormat getHeaderCellStyle(){  
          
         
         * WritableFont.createFont("宋体")：设置字体为宋体 
         * 10：设置字体大小 
         * WritableFont.BOLD:设置字体加粗（BOLD：加粗     NO_BOLD：不加粗） 
         * false：设置非斜体 
         * UnderlineStyle.NO_UNDERLINE：没有下划线 
           
        WritableFont font = new WritableFont(WritableFont.createFont("宋体"),  
                                             10,   
                                             WritableFont.BOLD,   
                                             false,
                                             UnderlineStyle.NO_UNDERLINE);  
        WritableCellFormat headerFormat = new WritableCellFormat(NumberFormats.TEXT);  
        try {
        	//设置自动换行
        	headerFormat.setWrap(true);//是否自动换行  
            //添加字体设置  
            headerFormat.setFont(font);  
            //设置单元格背景色：表头为黄色 
            headerFormat.setBackground(Colour.YELLOW);
            //设置表头表格边框样式  
            //整个表格线为粗线、黑色  
            headerFormat.setBorder(Border.ALL, BorderLineStyle.THICK, Colour.BLACK);  
            //表头内容水平居中显示
            headerFormat.setAlignment(Alignment.CENTRE);  
            //设置垂直居中;  
            headerFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
        } catch (WriteException e) {  
            System.out.println("表头单元格样式设置失败！");  
        }  
        return headerFormat;  
    }
    
    *//** 
     * 表体单元格样式的设定 
     *//*  
    public static WritableCellFormat getBodyCellStyle(){  
          
         
         * WritableFont.createFont("宋体")：设置字体为宋体 
         * 10：设置字体大小 
         * WritableFont.NO_BOLD:设置字体非加粗（BOLD：加粗     NO_BOLD：不加粗） 
         * false：设置非斜体 
         * UnderlineStyle.NO_UNDERLINE：没有下划线 
           
        WritableFont font = new WritableFont(WritableFont.createFont("宋体"),  
                                             10,   
                                             WritableFont.NO_BOLD,   
                                             false,  
                                             UnderlineStyle.NO_UNDERLINE);  
          
        WritableCellFormat bodyFormat = new WritableCellFormat(font);  
        try {  
            //设置单元格背景色：表体为白色  
            bodyFormat.setBackground(Colour.WHITE);  
            //设置表头表格边框样式  
            //整个表格线为细线、黑色  
            bodyFormat.setBorder(Border.ALL, BorderLineStyle.THIN, Colour.BLACK);
            //表头内容水平居中显示
            bodyFormat.setAlignment(Alignment.CENTRE);  
            //设置垂直居中;  
            bodyFormat.setVerticalAlignment(VerticalAlignment.CENTRE);    
        } catch (WriteException e) {  
            System.out.println("表体单元格样式设置失败！");  
        }  
        return bodyFormat;  
    }
}
*/