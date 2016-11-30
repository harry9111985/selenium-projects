package com.selenium.ex.util.io;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class ExcelDataSet implements DataSet {

	private final Map<String,List<DataRow>> excelDataBySheet;
	private final List<String> excelSheetNamesByIndex;
	
	public final List<DataRow> getSheetData(String sheetName){
		return excelDataBySheet!=null ? excelDataBySheet.get(sheetName) : new ArrayList<DataRow>();
	}
	
	private ExcelDataSet() {
		excelDataBySheet = new HashMap<String,List<DataRow>>();
		excelSheetNamesByIndex = new ArrayList<String>();
	}
	
	 static ExcelDataSet getInstance(){
		return new ExcelDataSet();
		
	}
	
	final void addNewSheetData(List<DataRow> sheetData, String sheetName){
	     excelDataBySheet.put(sheetName,sheetData);
	     excelSheetNamesByIndex.add(sheetName);
	}
	
	public final List<DataRow> getSheetDataByIndex(int index){
		return excelDataBySheet.get(excelSheetNamesByIndex.get(index));
	}
	
	
	
}
