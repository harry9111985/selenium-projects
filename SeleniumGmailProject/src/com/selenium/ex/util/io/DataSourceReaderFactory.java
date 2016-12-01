package com.selenium.ex.util.io;

/**
 * 
 * @author Harish Subramaniam
 *
 * Factory class to get DataSourceReader Instance 
 */
public final class DataSourceReaderFactory {

	/**
	 * 
	 * @param dataSourceReaderType - The type of source provided to get the relevant 
	 *                               DataSourceReader from the Factory to fetch data. For example "excel","csv","db" etc.. 
	 * @return - Returns an instance of the DataSourceReader
	 */
	public static final DataSourceReader getDataSrcReaderType(String dataSourceReaderType){
		
		if("excel".equals(dataSourceReaderType))
			return ExcelDataSourceReader.newInstance();
		else
			return null;
	}
}
