package com.fxl.template.user.pdf;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class GenerateCourseListPDF {
	
	/**
	 * 获取中文字体
	 * @param fontSize 字体大小
	 * @param fontStyle 字体样式(正常、加粗)
	 * @return Font
	 */
	private static Font getChineseFont(Integer fontSize, Integer fontStyle){
		try {
			BaseFont chinese = BaseFont.createFont( "c://windows//fonts//simsun.ttc,1" , BaseFont.IDENTITY_H,  BaseFont.EMBEDDED);
			//BaseFont chinese = BaseFont.createFont("/data/font/simsun.ttc,1", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
			Font fontChinese = new Font(chinese, fontSize, fontStyle);
			return fontChinese;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 设置页码
	 * @param document
	 * @param cb 
	 * @param text
	 * @throws DocumentException
	 * @throws IOException
	 */
	public static void setFooterPage(Document document, PdfContentByte cb, String text) throws DocumentException, IOException{
		cb.saveState();
        cb.beginText();
        cb.setFontAndSize(BaseFont.createFont( "c://windows//fonts//simsun.ttc,1" , BaseFont.IDENTITY_H,  BaseFont.EMBEDDED), 8);
        //cb.setFontAndSize(BaseFont.createFont("/data/font/simsun.ttc,1", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED), 8);
        cb.showTextAligned(PdfContentByte.ALIGN_CENTER, text, 300, 10, 0);
        cb.endText();
        cb.restoreState();
	}
	
	/**
	 * 这是PDF文件Title
	 * @param title
	 * @return
	 */
	private static Paragraph setTitle(String title){
		Paragraph titleParagraph = new Paragraph(title, getChineseFont(14, Font.BOLD));
		titleParagraph.setAlignment(1);
		return titleParagraph;
	}
	/**
	 * 设置二级标题
	 * @param title
	 * @return
	 */
	private static Paragraph setSecendLevelTitle(String title){
		Paragraph secendLevelTitle = new Paragraph(title, getChineseFont(13, Font.NORMAL));
		secendLevelTitle.setAlignment(1);
		secendLevelTitle.setSpacingBefore(10);
		secendLevelTitle.setSpacingAfter(10);
		return secendLevelTitle;
	}
	
	/**
	 * 内容页Table嵌套
	 * @return
	 * @throws DocumentException
	 * @throws IOException 
	 * @throws MalformedURLException 
	 */
	private static PdfPTable setContentInfo(String detail, Integer index) throws DocumentException, MalformedURLException, IOException{
		PdfPTable contentTable = new PdfPTable(2);
		/*contentTable.setSpacingBefore(10); //段前间距
		contentTable.setWidthPercentage(100); //表格宽度100%  */
		
		/*float[] widths = {0.1f, 0.1f};
		widths[0] = 70f;
		widths[1] = 30f;
		contentTable.setWidths(widths);*/
		
		/*Paragraph ele = new Paragraph(detail);
		PdfPCell contentCell = new PdfPCell(ele);*/
		//contentCell.setColspan(3); //该元素跨3列 
		
		contentTable.addCell(detail);
		contentTable.addCell(index+"");
		
		return contentTable;
	}
	
	
	
	public static String generatePDF(String tabName){
		Document document = new Document(PageSize.A4);
		PdfWriter writer = null;
		String filePath = "F://test.pdf";
		try {
			FileOutputStream outputStream = new  FileOutputStream(filePath);
			writer = PdfWriter.getInstance(document, outputStream);
			document.setMargins(10, 10, 10, 10); //setMargins
			document.open();
			
			PdfContentByte cb = writer.getDirectContent(); //getDirectContent
			setFooterPage(document, cb, "第1页  共1页");
			/** 标题 **/
			Paragraph title = setTitle("宝安妇幼保健院");
			document.add(title);
			/** 二级标题 **/
			Paragraph secendLevelTitle = setSecendLevelTitle("2016年2月19日课程表(星期六)");
			document.add(secendLevelTitle);
			
			List<String> list = new ArrayList<String>();
			list.add("哎哎哎");
			list.add("bbb");
			list.add("ccc");
			list.add("ddd");
			list.add("eee");
			list.add("fff");
			PdfPTable table = new PdfPTable(2); //两列
			
			Paragraph tabEle;
			PdfPCell tabTitleCell;
			
			tabEle = new Paragraph("内容", getChineseFont(10, Font.NORMAL));
			tabTitleCell = new PdfPCell(tabEle);
			tabTitleCell.setHorizontalAlignment(Element.ALIGN_CENTER);  //水平居中的cell
			tabTitleCell.setPadding(10f);
			table.addCell(tabEle);
			
			tabEle = new Paragraph("下标", getChineseFont(10, Font.NORMAL));
			tabTitleCell = new PdfPCell(tabEle);
			tabTitleCell.setHorizontalAlignment(Element.ALIGN_CENTER);  //水平居中的cell
			table.addCell(tabEle);
			
			
			for(int i=0;i<list.size();i++){
				/*if(i != 0 && i % 3 == 0){
					//创建页码 
					PdfContentByte cbSecond = writer.getDirectContent();
					setFooterPage(document, cbSecond, "第"+i/3+1+"页");
				}*/
				//document.add(setContentInfo(list.get(i), i+1)); //添加表格
				String str = list.get(i);
				PdfPCell cell = new PdfPCell(new Paragraph(str, getChineseFont(10, Font.NORMAL)));
				table.addCell(cell);
				table.addCell(i+1+"");
			}
			document.add(table);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(document != null){
				document.close();
			}
		}
		System.out.println(filePath);
		return filePath;
	}
	
	
	public static void main(String[] args) {
		
	}
}
