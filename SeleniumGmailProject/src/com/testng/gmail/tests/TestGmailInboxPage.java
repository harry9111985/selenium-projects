package com.testng.gmail.tests;

import java.time.LocalDateTime;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.gmail.pageobjects.GmailInboxPage;
import com.gmail.pageobjects.GmailLoginPage;
import com.selenium.ex.util.constants.GmailPageConstants;
import com.selenium.ex.util.io.DataRow;
import com.testng.gmail.tests.dataproviders.GmailDataProviderSource;

public class TestGmailInboxPage {

	
	WebDriver driver;
	GmailLoginPage loginPage;
	GmailInboxPage inboxPage;
	
	
	@BeforeMethod(alwaysRun=true)
	public void loginSuccessfullyToGmail(){
		driver = new ChromeDriver();
	}
	
	@Test(groups = {"functest"} ,dataProvider="loginSuccessData" , dataProviderClass=GmailDataProviderSource.class)
	public void testComposeEmail(DataRow dataRow){
		loginPage = GmailLoginPage.newInstance(driver, dataRow.getAttributeValue(GmailPageConstants.GmailInput.EMAIL), 
												dataRow.getAttributeValue(GmailPageConstants.GmailInput.PASSWORD));
		loginPage.performLoginSuccess();
	    inboxPage = GmailInboxPage.newInstance(driver);
		
	    LocalDateTime timeEmailSent = inboxPage.performComposeEmail("Test Email from Selenium ",
								   dataRow.getAttributeValue(GmailPageConstants.GmailInput.EMAIL));
	   	if(timeEmailSent!=null){
			Assert.assertTrue(inboxPage.verifyEmailComposed("Test Email from Selenium ",
					           dataRow.getAttributeValue(GmailPageConstants.GmailInput.EMAIL),timeEmailSent));
	   	}
	}
	
	@AfterMethod(alwaysRun=true)
	public void cleanUp(){
		driver.quit();
		driver = null;
	}
	
}
