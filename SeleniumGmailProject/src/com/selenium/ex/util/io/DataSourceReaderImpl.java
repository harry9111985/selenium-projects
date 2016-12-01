package com.selenium.ex.util.io;

/**
 * @author Harish Subramaniam
 * 
 * Abstract implementation class of the DataSourceReader Interface. 
 *
 */
public abstract class DataSourceReaderImpl implements DataSourceReader {

	@Override
	public abstract DataSet readData(String fileName) throws Exception;

	

}
