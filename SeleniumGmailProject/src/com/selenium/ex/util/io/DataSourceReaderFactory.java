package com.selenium.ex.util.io;

public final class DataSourceReaderFactory {

	public static final DataSourceReader getDataSrcReaderType(String dataSourceReaderType){
		
		if("excel".equals(dataSourceReaderType))
			return ExcelDataSourceReader.newInstance();
		else
			return null;
	}
}
