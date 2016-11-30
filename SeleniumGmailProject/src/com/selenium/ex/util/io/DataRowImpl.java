package com.selenium.ex.util.io;

import java.util.HashMap;
import java.util.Map;

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
