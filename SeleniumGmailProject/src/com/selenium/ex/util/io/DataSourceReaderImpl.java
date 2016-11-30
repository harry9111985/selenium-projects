package com.selenium.ex.util.io;


public abstract class DataSourceReaderImpl implements DataSourceReader {

	@Override
	public abstract DataSet readData(String fileName) throws Exception;

	

}
