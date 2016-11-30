package com.testng.gmail.tests.dataproviders;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.annotations.DataProvider;

import com.selenium.ex.util.io.DataRow;
import com.selenium.ex.util.io.DataSourceReader;
import com.selenium.ex.util.io.DataSourceReaderFactory;
import com.selenium.ex.util.io.ExcelDataSet;

public class GmailDataProviderSource {

	private static Map<String,DataSourceReader> readers;
	private static ExcelDataSet loginData;
	
	static {
		readers = new HashMap<String,DataSourceReader>();
		readers.put("excel",DataSourceReaderFactory.getDataSrcReaderType("excel"));
		try{
			loginData = (ExcelDataSet)readers.get("excel").readData("login_data.xls");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@DataProvider(name="loginSuccessData")
	public static Object [] [] getLoginSuccessData() {
		Object[][] dataToTestArr;
		try{
			List<DataRow> dataToTest = loginData.getSheetData("login_success");
			dataToTestArr = new Object [dataToTest.size()][1];
			int index = 0;
			for (DataRow dataRow : dataToTest) {
                   dataToTestArr[index][0] = dataRow;
                   index++;
			}
			
			return dataToTestArr;
		}catch(Exception e){
			System.err.println(e.getMessage());
			return new Object [][] {};
		}
	}
	
	
	@DataProvider(name="loginFailureData")
	public static Object [] [] getLoginFailureData() {
		Object[][] dataToTestArr;
		try{
			List<DataRow> dataToTest = loginData.getSheetData("login_failure");
			dataToTestArr = new Object [dataToTest.size()][1];
			int index = 0;
			for (DataRow dataRow : dataToTest) {
                   dataToTestArr[index][0] = dataRow;
                   index++;
			}
			
			return dataToTestArr;
		}catch(Exception e){
			System.err.println(e.getMessage());
			return new Object [][] {};
		}
	}
	
}
