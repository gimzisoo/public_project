//package com.kr.pub.util;
//
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
//import org.apache.ibatis.session.ResultContext;
//import org.apache.ibatis.session.ResultHandler;
//import org.apache.poi.hssf.util.HSSFColor.HSSFColorPredefined;
//import org.apache.poi.ss.usermodel.BorderStyle;
//import org.apache.poi.ss.usermodel.CellStyle;
//import org.apache.poi.ss.usermodel.FillPatternType;
//import org.apache.poi.ss.usermodel.HorizontalAlignment;
//import org.apache.poi.xssf.streaming.SXSSFCell;
//import org.apache.poi.xssf.streaming.SXSSFRow;
//import org.apache.poi.xssf.streaming.SXSSFSheet;
//import org.apache.poi.xssf.streaming.SXSSFWorkbook;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import com.kr.pub.dto.StatusDTO;
//
//import jakarta.servlet.ServletOutputStream;
//import jakarta.servlet.http.HttpServletResponse;
//
//public class ResultRowDataHandler implements ResultHandler<StatusDTO>{
//	private Logger logger = LoggerFactory.getLogger(getClass());
//	
//	private HttpServletResponse response;
//	private SXSSFWorkbook workbook;
//	private SXSSFSheet sheet;
//	private boolean isStarted = false;
//	
//	public ResultRowDataHandler(HttpServletResponse response) {
//		this.response = response;
//		
//		workbook = new SXSSFWorkbook(10000);
//		sheet = workbook.createSheet("item");
//		
//		SXSSFRow row = null;
//		SXSSFCell cell = null;
//		
//		String[] columns = {"품목코드","품목명","품목유형","입고일","현재재고","입고단가"};
//		
//		CellStyle headStyle = workbook.createCellStyle();
//	      headStyle.setBorderTop(BorderStyle.MEDIUM);
//	      headStyle.setBorderBottom(BorderStyle.MEDIUM);
//	      headStyle.setBorderLeft(BorderStyle.MEDIUM);
//	      headStyle.setBorderRight(BorderStyle.MEDIUM);
//        
//	      headStyle.setFillForegroundColor(HSSFColorPredefined.GREY_25_PERCENT.getIndex());
//	      headStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
//	      headStyle.setAlignment(HorizontalAlignment.CENTER);
//
//	        row = sheet.createRow(0);
//	        
//	        for (int i = 0; i <= 5; i++) {
//	            cell = row.createCell(i);
//	            cell.setCellStyle(headStyle);
//	            cell.setCellValue(columns[i]);
//	        }
//	}
//	
//	@Override
//	public void handleResult(ResultContext<? extends StatusDTO> resultContext) {
//		
//		StatusDTO excelDTO = resultContext.getResultObject();
//
//		if(!isStarted) { 
//			open(resultContext.getResultObject());
//			isStarted = true;
//		}
//		
//		SXSSFRow row =null;
//		SXSSFCell cell = null;		
//		
//		row = sheet.createRow(resultContext.getResultCount());
//		
//		cell = row.createCell(0);
//		cell.setCellValue(excelDTO.getItemId());
//		
//		cell = row.createCell(1);
//		cell.setCellValue(excelDTO.getItemName());
//		
//		cell = row.createCell(2);
//		cell.setCellValue(excelDTO.getType());
//		
//		cell = row.createCell(3);
//		cell.setCellValue(excelDTO.getStoreDate());
//		
//		cell = row.createCell(4);
//		cell.setCellValue(excelDTO.getStock());
//		
//		cell = row.createCell(5);
//		cell.setCellValue(excelDTO.getPrice());
//	}
//	
//	public void open(StatusDTO resultObject) {
//
//      String fileName = "item" + new SimpleDateFormat("yyyyMMdd").format(new Date()) + ".xlsx";
//
//      response.setHeader("Set-Cookie", "fileDownload=true;path=/");
//      response.setHeader("Content-Disposition", String.format("attachment;filename=\"" + fileName + "\""));
//
//      System.out.println("Open method executed");
//	}
//
//	
//	public void close() {
//		try {
//			workbook.write(response.getOutputStream());
//			workbook.dispose();
//			
//			 System.out.println("Close method executed");
//		} catch (Exception e) {
//			 logger.error("Exception : {}", e);
//	         response.setHeader("Set-Cookie", "fileDownload=false;path=/");
//	         response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
//	         response.setHeader("Content-Type", "text/html;charset=utf-8");
//			ServletOutputStream out = null;
//			try {
//				out = response.getOutputStream();
//				byte[] data = new String ("fail Download.......").getBytes();
//				out.write(data, 0, data.length);
//			} catch (Exception ex) {
//				logger.error("Exception : {}",ex);
//			}
//		}finally {
//			if(workbook != null) {
//					try {workbook.close();}catch(Exception ex) {}
//			}
//		}
//	}
//}