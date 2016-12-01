package com.selenium.ex.util.io;

/**
 * @author Harish Subramaniam
 * 
 * An interface to represent a row of data (for example : Excel Sheet Row/CSV Row/Database row)
 */
public interface DataRow {

	public String getAttributeValue(String rowName);
	
	public void addAttribute(String name,String value);
}
