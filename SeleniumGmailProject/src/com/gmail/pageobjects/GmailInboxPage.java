package com.gmail.pageobjects;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.google.common.base.Function;


public class GmailInboxPage {

	private WebDriver driver;
	
	private String testInputEmail;
	private String testInputSubject;
	private LocalDateTime testInputDate;
	
	@FindBy(xpath ="//div[@aria-label='Account Information']")
	private WebElement accountInformation;
	
	@FindBy(xpath = "//a[@href='#inbox']")
	private WebElement gmailInbox;
	
	private GmailInboxPage(WebDriver driver) throws Exception{
		this.driver = driver;
		PageFactory.initElements(driver, this);
		
	}
	
	public static GmailInboxPage newInstance(WebDriver driver) {
		try{
			return new GmailInboxPage(driver);
		}catch(Exception e){
			return null;
		}
	}
	
	private Function<WebDriver,WebElement> retrieveComposeButton = new Function<WebDriver,WebElement> () {

		@Override
		public WebElement apply(WebDriver driver) {
			List<WebElement> webElements = driver.findElements(By.xpath("//div[@role='button']"));
			return webElements.stream().filter(p -> "COMPOSE".equals(p.getText())).collect(Collectors.toList()).get(0);
			
		}
	};
	
	private Function<WebDriver,WebElement> retrieveInboxLink = new Function<WebDriver,WebElement>() {

		@Override
		public WebElement apply(WebDriver driver) {
		    List<WebElement> links = driver.findElements(By.xpath("//a[@href='https://mail.google.com/mail/u/0/#inbox']"));
		    for (WebElement link : links) {
				if(link.getAttribute("title")!=null && link.getAttribute("title").contains("Inbox (")){
					return link;
				}
			}
		    return null;
		}
		
	};
	
		
	private BiFunction<WebDriver,String,WebElement> retrieveTab = new BiFunction<WebDriver,String,WebElement>() {

		@Override
		public WebElement apply(WebDriver driver,String tabName) {
			return  driver.findElements(By.xpath("//tr[@role='tablist']")).stream().
					filter(p -> p.findElement(By.xpath("//div[contains(@aria-label,'"+ tabName +"')]"))!=null)
					.map(p -> p.findElement(By.xpath("//div[contains(@aria-label,'"+ tabName +"')]")))
					.collect(Collectors.toList())
			        .get(0);
						
		}
		
	};
	
	
	private Function<WebDriver,WebElement> retrieveEmail = new Function<WebDriver,WebElement> () {

		@Override
		public WebElement apply(WebDriver driver) {
			List<WebElement> emailRows = driver.findElement(By.className("UI"))
											   .findElement(By.xpath("//table[@id=':3g']"))
											   .findElements(By.xpath("//td[@class='yX xY ']"));
			for (WebElement emailRow : emailRows) {
				if(emailRow!=null){
					String [] emailContent = emailRow.getAttribute("innerHTML").split(",");
					if(emailContent[1].contains(testInputEmail) && 
							emailContent[2].replaceAll(" ","").contains(testInputSubject.replaceAll(" ","")) && 
							isMailReceivedTimeEqual(emailContent[3]))
						return emailRow;
				}
			}
		     return null;	
		}

		private boolean isMailReceivedTimeEqual(String time) {
			String testInputDateStr = new StringBuffer().append((testInputDate.getHour() > 12 ? testInputDate.getHour() - 12 : testInputDate.getHour()))
					                                    .append(":")
					                                    .append(testInputDate.getMinute())
					                                    .append(" "+ (testInputDate.getHour() < 12 ? "am" : "pm")).toString();
			
			String testInputDateStrMtMinus1 = new StringBuffer().append((testInputDate.getHour() > 12 ? testInputDate.getHour() - 12 : testInputDate.getHour()))
                    .append(":")
                    .append(testInputDate.getMinute()-1)
                    .append(" "+ (testInputDate.getHour() < 12 ? "am" : "pm")).toString();
			return time!=null && (time.contains(testInputDateStr) || time.contains(testInputDateStrMtMinus1));
		}
	};
	
	

	
	
	private Function<WebDriver,WebElement> getNewMessageDialog = new Function<WebDriver,WebElement>() {

		@Override
		public WebElement apply(WebDriver driver) {
			return driver.findElement(By.xpath("//div[@class = 'nH Hd']"));
		}
		
	};

	

	
	public boolean isLoginSuccessful(String userName,String email){
		
		WebElement userNameElmt = accountInformation.findElement(By.className("gb_qb")).findElement(By.className("gb_ub"));
		WebElement emailElmt = accountInformation.findElement(By.className("gb_qb")).findElement(By.className("gb_vb"));
		if(userNameElmt!=null && emailElmt!=null){
			return userName.equals(userNameElmt.getAttribute("innerHTML")) 
					&& email.equals(emailElmt.getAttribute("innerHTML")) 
					&& gmailInbox!=null 
					&& "Gmail".equals(gmailInbox.getAttribute("title")); 
		}else {
			return false;
		}
		
		
	}
	
	
	public LocalDateTime performComposeEmail(String subject,String to){
		WebElement composeButton = retrieveComposeButton.apply(driver);
		if(composeButton!=null){
			composeButton.click();
		    WebElement newMessageDialog = getNewMessageDialog.apply(driver);
		    WebElement subjectBox = newMessageDialog.findElement(By.name("subjectbox"));
		    subjectBox.sendKeys(subject);
		    WebElement recipientsButton = newMessageDialog.findElement(By.xpath("//div[@class='oL aDm']"));
		    recipientsButton.click();
		    WebElement toBox = newMessageDialog .findElement(By.xpath("//textarea[@aria-label='To']")); 
		    toBox.sendKeys(to);
		    WebElement sendButton = newMessageDialog.findElement(By.id(":oc"));
		    sendButton.click();
		    
		}
		//Record the time being sent.
		return LocalDateTime.now();
	}

	public boolean verifyEmailComposed(String subject, String to,LocalDateTime dateTimeEmailSent) {
		//a) Click on Inbox ( Retrieve Inbox Link - Function)
		//b) Click on Primary ( Retrieve Primary Tab - Function , Click on it)
		//c) Retrieve the email using subject and to and the time being sent - Function ( Retrieve Email Table using Xpath - Function  , Retrieve email row using xpath ,subject,to)
		//d) If the email is found retrieve true else false
		this.testInputEmail = to;
		this.testInputSubject = subject;
		this.testInputDate = dateTimeEmailSent;
		WebElement inboxLink = retrieveInboxLink.apply(driver);
	    inboxLink.click();
	    WebElement primaryTab = retrieveTab.apply(driver,"Primary");
	    primaryTab.click();
	    //Sleeping for 1 sec so that new email can be loaded in the gmail account.
	    try{
	    	Thread.sleep(1000);
	    }catch(InterruptedException e){
	    	//Do nothing
	    }
	    WebElement emailRow = retrieveEmail.apply(driver);
	    if(emailRow!=null){
	    	return true;
	    }
		return false;
	}

	
}
