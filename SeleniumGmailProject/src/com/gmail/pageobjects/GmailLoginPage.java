package com.gmail.pageobjects;


import java.util.List;
import java.util.function.Consumer;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.google.common.base.Function;
import com.selenium.ex.util.ConditionUtil;




public final class GmailLoginPage {

	//Driver and Web Element References
	
	WebDriver driver;
	
	@FindBy(id = "Email")
	private WebElement loginEmail;
	
	
	@FindBy(id = "next")
	private WebElement nextBtn;
	
	
	private String loginEmailStr;
	
	private String loginPasswdStr;
	
	//Start Constructor - Login Page
	
	private GmailLoginPage(WebDriver driver,String loginEmail,String loginPassword){
		
		driver.get("https://accounts.google.com/ServiceLogin?service=mail&passive=true&rm=false&continue=https://mail.google.com/mail/&ss=1&scc=1&ltmpl=default&ltmplcache=2&emr=1&osid=1");
		PageFactory.initElements(driver, this);
		this.driver = driver;		
		this.loginEmailStr = loginEmail;
		this.loginPasswdStr = loginPassword;
	}
	
	public static GmailLoginPage newInstance(WebDriver driver,String loginEmail,String loginPassword){
		return new GmailLoginPage(driver,loginEmail,loginPassword);
	}
	
	//End Constructor - Login Page
	
	
	//<-----START of REUSABLE CONDITIONS AND PROCESS FUNCTIONS----->
	//All Functions which are required for performing login page checks and process
    
	Function<WebDriver,Boolean> passwordPageCondn = (WebDriver driver) -> 	driver.findElement(By.id("Passwd"))!=null;
	
	Consumer<WebDriver> processLogin = new Consumer<WebDriver>() {

		@Override
		public void accept(WebDriver driver) {
		       WebElement loginPassword = driver.findElement(By.id("Passwd"));
			   loginPassword.sendKeys(loginPasswdStr);
			   WebElement signInBtn = driver.findElement(By.id("signIn"));
			   signInBtn.click();
			
		}
	
	 }; 
	 
	Function<WebDriver,Boolean> isLoginPageLoaded = (WebDriver driver) -> driver.getCurrentUrl()!=null && driver.getCurrentUrl().contains("inbox");
	     
	Function<WebDriver,Boolean> verifyLoginFailure = new Function<WebDriver,Boolean>() {

		@Override
		public Boolean apply(WebDriver driver) {
			List<WebElement> passwordElements = driver.findElements(By.xpath("//*[starts-with(@id, 'errormsg_')]"));
			if(passwordElements.get(1).getAttribute("id").endsWith("_Passwd")){
					System.out.println(passwordElements.get(1).getText());
					return "Wrong password. Try again.".equals(passwordElements.get(1).getText());
			}
			return false;
		}
		
	};

	//<-----END OF REUSABLE CONDITION AND PROCESS FUNCTIONS----->
        
	//<---- START -->Page Object Methods used by Tests to perform Test Executions
	public boolean performLoginSuccess(){
		this.loginEmail.sendKeys(loginEmailStr);
		this.nextBtn.click();
		try{
	       if(!ConditionUtil.isWebDriverCondnMetIfElse(driver,passwordPageCondn ,processLogin,null))
	    	   return false;
		   
	       if(!ConditionUtil.isWebDriverCondnPassed(driver, isLoginPageLoaded))
	    	   return false;
           
 		}catch(Exception e){
			return false;
		}
		return true;
	}
	
	public boolean performLoginFail(){
		this.loginEmail.sendKeys(loginEmailStr);
		this.nextBtn.click();
		try{
	       if(!ConditionUtil.isWebDriverCondnMetIfElse(driver,passwordPageCondn ,processLogin,null))
	    	   return false;
		   
	       if(!ConditionUtil.isWebDriverCondnFailed(driver, isLoginPageLoaded,verifyLoginFailure))
	    	   return false;
           
 		}catch(Exception e){
			return false;
		}
		return true;
	}
	
	//<---- END -->Page Object Methods used by Tests to perform Test Executions
			 
	 

	
	
}
