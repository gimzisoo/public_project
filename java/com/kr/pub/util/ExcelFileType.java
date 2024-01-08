package com.kr.pub.util;

import java.io.FileInputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * 
 * 엑셀파일을 읽어서 Workbook 객체에 리턴한다.
 * XLS와 XLSX 확장자를 비교한다.
 * 
 * @param filePath
 * @return
 * 
 */
public class ExcelFileType {

	public static Workbook getWorkbook(String filePath) {
		 /*
         * FileInputStream은 파일의 경로에 있는 파일을
         * 읽어서 Byte로 가져온다.
         * 
         * 파일이 존재하지 않는다면은
         * RuntimeException이 발생된다.
         */
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(filePath);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(),e);
		}
		
		Workbook wb = null;
		 /*
         * 파일의 확장자를 체크해서 .XLS 라면 HSSFWorkbook에
         * .XLSX라면 XSSFWorkbook에 각각 초기화 한다.
         */
		if(filePath.toUpperCase().endsWith(".XLS")) {
			try {
				wb = new HSSFWorkbook(fis);
			} catch (Exception e) {
				throw new RuntimeException(e.getMessage(), e);
			}
		}
		else if(filePath.toUpperCase().endsWith(".XLSX")){
			try {
				wb = new XSSFWorkbook(fis);
			} catch (Exception e) {
				throw new RuntimeException(e.getMessage(), e);
			}
		}
		if(fis != null) {
			try {
				fis.close();
			} catch (Exception e) {
				System.out.println("예외상황발생");
			}
		}
		return wb;
	}
}
