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

public class TestGmailInboxPage {

	
	WebDriver driver;
	GmailLoginPage loginPage;
	GmailInboxPage inboxPage;
	
	@BeforeClass(alwaysRun=true)
	public void setUp(){
		System.setProperty("webdriver.chrome.driver", "C:\\Harish Software\\selenium\\chromedriver_win32\\chromedriver.exe");
	}
	
	@BeforeMethod(alwaysRun=true)
	public void loginSuccessfullyToGmail(){
		driver = new ChromeDriver();
		loginPage = GmailLoginPage.newInstance(driver, "harry9.11.1985@gmail.com", "Aussie@2016");
		loginPage.performLoginSuccess();
	    inboxPage = GmailInboxPage.newInstance(driver);
	}
	
	@Test(groups={"functest"})
	public void testComposeEmail(){
		boolean isMailComposed = inboxPage.performComposeEmail("Test Email from Selenium ","harry9.11.1985@gmail.com");
	   	if(isMailComposed){
			Assert.assertTrue(inboxPage.verifyEmailComposed("Test Email from Selenium ","harry9.11.1985@gmail.com"));
	   	}
	}
	
	@AfterMethod(alwaysRun=true)
	public void cleanUp(){
		driver.quit();
		driver = null;
	}
	
}
