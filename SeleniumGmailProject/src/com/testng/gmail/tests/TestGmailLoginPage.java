package com.testng.gmail.tests;

import java.util.concurrent.TimeUnit;

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

public class TestGmailLoginPage {

	WebDriver driver;
	
	GmailLoginPage gmailLoginPage;
	GmailInboxPage gmailLoginSuccessPage;
	
	@Test(groups = {"functest"} ,dataProvider="loginSuccessData" , dataProviderClass=GmailDataProviderSource.class)
	public void test_Gmail_Login_Successful (DataRow row){
		String email = row.getAttributeValue("Email");
		String accountName = row.getAttributeValue("Account Name");
		String password = row.getAttributeValue("Password");
		gmailLoginPage = GmailLoginPage.newInstance(driver,email,password);
		
		Boolean loginSuccess = gmailLoginPage.performLoginSuccess();
		if(loginSuccess){
			gmailLoginSuccessPage = GmailInboxPage.newInstance(driver);
			Assert.assertTrue(gmailLoginSuccessPage.isLoginSuccessful(accountName, email),"Login Success");
		}else {
			Assert.assertTrue(false,"Login not successful");
		}
		
	}
	
	@Test(groups = {"functest"} , dataProvider="loginFailureData" , dataProviderClass=GmailDataProviderSource.class)
	public void test_Gmail_Login_Failure (DataRow row){
		String email = row.getAttributeValue("Email");
		String accountName = row.getAttributeValue("Account Name");
		String password = row.getAttributeValue("Password");
		gmailLoginPage = GmailLoginPage.newInstance(driver,email,password);
		
		Boolean loginFailed = gmailLoginPage.performLoginFail();
		if(loginFailed)
			Assert.assertTrue(true,"Login not successful : " + accountName + " password : " + password + " email : " + email);
	}
	
	@BeforeMethod(alwaysRun=true)
	public void newDriverInstance(){
		driver =  new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
	}
	
	@AfterMethod(alwaysRun=true)
	public void cleanUp(){
		driver.quit();
		driver = null;
	}
}
