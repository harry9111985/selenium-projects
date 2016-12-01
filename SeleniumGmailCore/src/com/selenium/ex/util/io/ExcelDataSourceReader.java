package com.selenium.ex.util.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;

/**
 * @author Harish Subramaniam
 *
 * This class implements the DataSourceReader capabilities to read data 
 * from an excel document. It uses an Apache POI APIs to read the excel
 * and it stores the data as a collection of DataRow's (DataSet).
 */
public class ExcelDataSourceReader extends DataSourceReaderImpl {

	private ExcelDataSourceReader(){
		
	}
	
	
	public static ExcelDataSourceReader newInstance(){
		return new ExcelDataSourceReader();
	}
	
	@Override
	public DataSet readData(String fileName) throws Exception {
		if(fileName == null || (fileName!=null && !(fileName.endsWith(".xls") || fileName.endsWith(".xlsx")))){
			throw new Exception("Invalid FileName : " + fileName);
		}
		
        File excelFile = new File("src/resources/"+ fileName);
        
        if(!excelFile.exists()) {
        	throw new Exception("Excel File with File Name : " + fileName + " doesn't exist");
        }
        
        ExcelDataSet dataSet;
        
        try(InputStream fileStream = new FileInputStream(excelFile); HSSFWorkbook excelWorkBook = new HSSFWorkbook(fileStream)){
	        
            int noOfSheets = excelWorkBook.getNumberOfSheets();
	        dataSet = ExcelDataSet.getInstance();
	        
	        for(int i=0;i<=noOfSheets-1;i++){
	        	HSSFSheet sheet =  excelWorkBook.getSheetAt(i);
	        	List<DataRow> sheetData = readSheet(sheet);
	        	dataSet.addNewSheetData(sheetData, sheet.getSheetName());
	        }
        }catch(IOException e){
        	throw e;
        }		
        return dataSet;
	}
	
	
	private List<DataRow> readSheet(HSSFSheet sheet) {
		
		List<DataRow> sheetRows = new ArrayList<DataRow>();
		
		List<String> header = new ArrayList<String>();
		int firstRow = sheet.getFirstRowNum();
		int lastRow = sheet.getLastRowNum();
		System.out.println("Reading " + lastRow + " Rows in sheet : " + sheet.getSheetName());
		
		readHeader(sheet,firstRow,header);
		firstRow++;
		
		while(firstRow <= lastRow){
			
			HSSFRow row = sheet.getRow(firstRow);
			DataRow dataRow = DataRowImpl.newInstance();
	
			for (Cell cell : row) {
				dataRow.addAttribute(header.get(cell.getColumnIndex()), cell.getStringCellValue());
			}	
		    sheetRows.add(dataRow);
			firstRow++;
		}
		return sheetRows;
	}

	private void readHeader(HSSFSheet sheet, int firstRow, List<String> header) {
		HSSFRow headerRow = sheet.getRow(firstRow);
		for (Cell cell : headerRow) {
			header.add(cell.getStringCellValue());
		}
		
	}


}
