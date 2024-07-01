package com.qa.opencart.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelUtil {
	
	private static final String EXCEL_FILE_PATH="./src/test/resources/testdata/TestData.xlsx";
	private static Workbook book;
	private static Sheet sheet;
	
	public static Object[][] getTestData(String sheetName) {
		Object[][] data=null;
		
		
		try {
			FileInputStream ip= new FileInputStream(EXCEL_FILE_PATH);
			 book = WorkbookFactory.create(ip);
			sheet= book.getSheet(sheetName.trim());
			int rowSize= sheet.getLastRowNum();
			int colSize=sheet.getRow(0).getLastCellNum();
			data = new Object[rowSize][colSize];
			
			for(int i=0;i<rowSize;i++) {
				for(int j=0;j<colSize;j++) {
					
					data[i][j]=sheet.getRow(i+1).getCell(j).toString();
					//i+1 is taken t avoid the 1st row which is the column header
				}
			}
		
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;
	}
}
