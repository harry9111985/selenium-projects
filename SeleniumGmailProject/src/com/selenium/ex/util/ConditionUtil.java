package com.selenium.ex.util;

import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import org.openqa.selenium.Alert;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.base.Function;


public class ConditionUtil {

	private ConditionUtil() throws Exception {
		throw new Exception();
	}
	
	/*
	 * Utility function to check if the web driver condition passed
	 */
	public static boolean isWebDriverCondnPassed(WebDriver driver,Function<WebDriver,Boolean> condition){
		
		try{
			Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(10, TimeUnit.SECONDS).
					pollingEvery(3, TimeUnit.SECONDS);
			
			boolean flag = wait.until(new Function<WebDriver,Boolean>(){
				
				@Override
				public Boolean apply(WebDriver webDriver) {
					System.out.println("Waiting : "+ webDriver.getCurrentUrl());
					return condition.apply(webDriver); 
				}
				
			});
			
			if(!flag){
				checkAlert(driver);
				return false;
			}else{
				return true;
			}
		
		}catch(TimeoutException e){
			System.err.println("Condition execution Failure : Timeout");
			checkAlert(driver);
			return false;
		}
			
		
	}
	
	/*
	 * Web driver condition to check if the condition failed.
	 * If failed then perform failure checks to confirm failure for that condition.
	 * Used for negative tests.
	 */
	public static boolean isWebDriverCondnFailed(WebDriver driver,Function<WebDriver,Boolean> condition,Function<WebDriver,Boolean> failureChecks){
		
		try{
			Wait<WebDriver> wait = new WebDriverWait(driver,2000);
	
			boolean flag = wait.until(new Function<WebDriver,Boolean>(){
				
				@Override
				public Boolean apply(WebDriver webDriver) {
					return !condition.apply(webDriver); 
				}
				
			});
			
			if(flag){
				return failureChecks.apply(driver);
			}else{
				return false;
			}
		
		}catch(TimeoutException e){
			System.err.println("Condition execution Failure : Timeout");
			checkAlert(driver);
			return false;
		}
			
		
	}
	
	/*
	 * If the webdriver condition passes then it will perform a success operation
	 * else it will perform failure operation.
	 */
	public static boolean isWebDriverCondnMetIfElse(WebDriver driver,Function<WebDriver,Boolean> condition,Consumer<WebDriver> success,Consumer<WebDriver> failure){
		try{
			
			Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(10, TimeUnit.SECONDS).
					pollingEvery(3, TimeUnit.SECONDS).ignoring(Throwable.class);
			boolean condnMet = wait.until(new Function<WebDriver,Boolean>(){
				
				@Override
				public Boolean apply(WebDriver webDriver) {
					return condition.apply(webDriver); 
				}
				
			});
			
			if(condnMet){
				success.accept(driver);
				return true;
			}else {
				checkAlert(driver);
				if(failure!=null){
					failure.accept(driver);
				}
				return false;
			}
		}catch(TimeoutException e){
			System.err.println("Condition execution Failure : Timeout");
			checkAlert(driver);
			return false;
		}
		
	}
	
	private static void checkAlert(WebDriver driver) {
	    try {
	        WebDriverWait wait = new WebDriverWait(driver, 2);
	        wait.until(ExpectedConditions.alertIsPresent());
	        ExpectedCondition<WebDriver> condition = ExpectedConditions.frameToBeAvailableAndSwitchToIt(3);
	        condition.apply(driver);
	        Alert alert = driver.switchTo().alert();
	        alert.accept();
	    } catch (Exception e) {
	        
	    }
	}
	
}
