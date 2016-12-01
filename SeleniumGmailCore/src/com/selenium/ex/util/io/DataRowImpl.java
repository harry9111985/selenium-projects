package com.selenium.ex.util.io;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Harish Subramaniam
 * 
 * Implementation of a Data Row. 
 * 
 * Data Row Implementation is represented as a Map containing
 * Key as the header/table column name and 
 * Value as a String Representation of Column Value
 * 
 */
public final class DataRowImpl implements DataRow {

	private final Map<String,String> attributeKeyValue;
	
	private DataRowImpl(){
		this.attributeKeyValue = new HashMap<String,String>();
	}
	
	
	final static DataRow newInstance(){
		return new DataRowImpl();
	}
	
	@Override
	public final void addAttribute(String name,String value){
		if(name!=null){
			attributeKeyValue.put(name, value);
		}
	}
	
	@Override
	public final String getAttributeValue(String attributeName) {
		return attributeKeyValue.get(attributeName);
	}

}
