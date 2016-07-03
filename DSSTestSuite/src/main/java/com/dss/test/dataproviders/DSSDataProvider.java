package com.dss.test.dataproviders;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.apache.poi.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.DataProvider;

import com.dss.test.utilities.DSSUtilities;

/**
 * ------- DSSDataProvider ------- 
 * Author: QA-DART 
 * Created on: 20-May-2016
 * History of Changes: Data Provider for Subscription Tests
 */
public class DSSDataProvider {
	@DataProvider(name="TestDataProvider")
	public static Object[][] AssignExcelToGlobalVariable() throws IOException
	{
			String filePath = System.getProperty("user.dir") + File.separator + "src/main/java" + File.separator + "com/dss/test/dataproviders" + File.separator + "DataProvider.xlsx";
		       FileInputStream inputStream = new FileInputStream(new File(filePath));
			Map<String, String> ExcelData = new HashMap<String, String>();
			
			XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
			XSSFSheet sheet = workbook.getSheet("OS");
			int row=sheet.getLastRowNum()+1;
			System.out.println(row);
			for(int i= 0;i<row;i++)
			{
				ExcelData.put(sheet.getRow(i).getCell(0).getStringCellValue(), sheet.getRow(i).getCell(1).getStringCellValue());
				
					
			}
			
			return new Object[][] {{ExcelData}};
	}
	}
	
	

