package com.selenium.ex.util.io;

/**
 * @author Harish Subramaniam
 * 
 * Interface to Read data from a Data Source
 */
public interface DataSourceReader {

	public DataSet readData(String fileName) throws Exception;
	
}
