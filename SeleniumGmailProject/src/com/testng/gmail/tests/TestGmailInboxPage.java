package com.testng.gmail.tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.gmail.pageobjects.GmailInboxPage;
import com.gmail.pageobjects.GmailLoginPage;
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
		loginPage = GmailLoginPage.newInstance(driver, dataRow.getAttributeValue("Email"), dataRow.getAttributeValue("Password"));
		loginPage.performLoginSuccess();
	    inboxPage = GmailInboxPage.newInstance(driver);
		boolean isMailComposed = inboxPage.performComposeEmail("Test Email from Selenium ",dataRow.getAttributeValue("Email"));
	   	if(isMailComposed){
			Assert.assertTrue(inboxPage.verifyEmailComposed("Test Email from Selenium ",dataRow.getAttributeValue("Email")));
	   	}
	}
	
	@AfterMethod(alwaysRun=true)
	public void cleanUp(){
		driver.quit();
		driver = null;
	}
	
}
