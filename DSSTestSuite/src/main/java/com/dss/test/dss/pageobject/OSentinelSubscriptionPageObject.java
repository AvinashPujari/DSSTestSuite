package com.dss.test.dss.pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * ------- OSentinelSubscriptionPageObject ------- 
 * Author: QA-DART 
 * Created on: 18-May-2016
 * History of Changes: Page Object of OS Subscription Page
 */
public class OSentinelSubscriptionPageObject {

    private WebDriver driver;

    public OSentinelSubscriptionPageObject(WebDriver driver) {

	this.driver = driver;

    }

    // Digital Only
    public OSentinelCheckoutPageObject addDigitalPlusSubscription() {

	driver.findElement(By.linkText("Get Access Now")).click();

	return new OSentinelCheckoutPageObject(driver);

    }

    // Within Florida: {Print + digitalPLUS Access}
    public OSentinelCheckoutPageObject addPrintDigitalPlusAccessWithinArea(String WithInAreaZIP) {
    	
    	driver.findElement(By.id("exampleInputEmail1")).sendKeys(WithInAreaZIP);
    	driver.findElement(By.linkText("See Available Options")).click();
    	
    	WebDriverWait wait = new WebDriverWait(driver, 5);
    	wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.id("data-call-action-p1-o1"))));   	
    	
    	driver.findElement(By.id("data-call-action-p1-o1")).click();

	return new OSentinelCheckoutPageObject(driver);

    }

    // Out of Florida: {Print + digitalPLUS Access}
    public OSentinelCheckoutPageObject proceedWithTryDigital() {

	WebDriverWait wait;
	
	wait = new WebDriverWait(driver, 2);
	wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.linkText("Try Digital")));
	
	driver.findElement(By.linkText("Try Digital")).click();

	return new OSentinelCheckoutPageObject(driver);

    }
    
    
    public String availableOptionsForOutsideAreaZip(String outsideZip) throws InterruptedException{
    	
    	String OutsideAreaZIPValidationMsg;
    	
    	driver.findElement(By.id("exampleInputEmail1")).sendKeys(outsideZip);
    	driver.findElement(By.linkText("See Available Options")).click();
    	
    	Thread.sleep(2000);
    	
    	OutsideAreaZIPValidationMsg = driver.findElement(By.xpath("//*[@id='templateContainer']/div/div/div[2]/div[1]/div[2]/div[1]/div[2]/div/div[1]/div[2]/div[2]/div[2]/div[1]/p")).getText();
    	
    	return OutsideAreaZIPValidationMsg;
    	
    }

}
