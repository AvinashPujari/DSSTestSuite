package com.dss.test.dss.subscription;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.security.Credentials;
import org.openqa.selenium.security.UserAndPassword;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.dss.test.dss.subscription.*;

import com.dss.test.dataproviders.DSSDataProvider;
import com.dss.test.dss.pageobject.OSentinelCheckoutPageObject;
import com.dss.test.dss.pageobject.OSentinelHomepagePageObject;
import com.dss.test.dss.pageobject.OSentinelSubscriptionPageObject;
import com.dss.test.properties.DSSProperties;
import com.dss.test.utilities.DSSUtilities;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import junit.framework.Assert;

/**
 * ------- SubscriptionTests ------- Author: QA-DART Created on: 17-May-2016
 * History of Changes: File created for holding TestNG tests
 * Test changes for POC II
 */

public class SubscriptionTest {

	private WebDriver driver;

	private ExtentTest logger;
	private OSentinelHomepagePageObject OSHomePage;
	private OSentinelSubscriptionPageObject OSSubscriptionPage;
	private OSentinelCheckoutPageObject OSCheckoutPage;
	private DSSUtilities util;
	
	

	private ExtentReports report = new ExtentReports(DSSProperties.ExtentReportPath);

	 	@BeforeMethod (alwaysRun = true)
		@Parameters("browser")
		public void beforeTest(String browser) throws InterruptedException, IOException
		{

		  if(browser.equalsIgnoreCase("firefox"))
		  {	  
		   driver = new FirefoxDriver();
		  }
		  
		  else if(browser.equalsIgnoreCase("chrome"))
		  {
			  System.setProperty("webdriver.chrome.driver","C:\\AllJarFiles\\chromedriver.exe");
			  driver = new ChromeDriver();
		  }
		  
		  else if(browser.equalsIgnoreCase("internet explorer"))
		  {
			  System.setProperty("webdriver.ie.driver","C:\\AllJarFiles\\IEDriverServer.exe");
			  driver = new InternetExplorerDriver();
		  }
		  
		  driver.manage().window().maximize();
		  driver.get(DSSProperties.URL);
		  
		  driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		  OSHomePage = new OSentinelHomepagePageObject(driver);
		  OSCheckoutPage = new OSentinelCheckoutPageObject(driver);
		  util = new DSSUtilities();
		  
		  
		}
	
	
	
	
	@Test(dataProvider = "TestDataProvider", dataProviderClass = DSSDataProvider.class, enabled = false)
	public void BuyPrintPlusDigitalSubscriptionWithinAreaZIPWithSSOR(Map<String,String> map) throws Exception {

		logger = report.startTest("Subscribe Print Plus Digital Subscription Within Area ZIP With SSOR user");

		String thankYouMessage;
		boolean accountMenuIcon = false; 
		OSSubscriptionPage = OSHomePage.goToSubscriptionsFromHomepage();
		logger.log(LogStatus.INFO, "Subscription Page is displayed");
	
		OSSubscriptionPage.addPrintDigitalPlusAccessWithinArea(map.get("Within Area Zip"));
		logger.log(LogStatus.INFO, "Entered Within Area ZIP");
	 	OSCheckoutPage.enterDigitalAccessSSOR(map.get("SSOR User"));
		logger.log(LogStatus.INFO, "Entered SSOR email");
		OSCheckoutPage.payWithCreditCard(map.get("Credit Card Holder Name"), map.get("Credit Card No"), map.get("Month"), map.get("Year"));
		logger.log(LogStatus.INFO, "Entered Credit Card Details");

		OSCheckoutPage.enterAddressWhenBillingAndDeliveryInformationSame(map.get("First Name"), map.get("Last Name"), map.get("Valid address1"),
				map.get("Valid address2"), map.get("Within Area Zip"), map.get("City"), map.get("State"), map.get("Contact No"));
		logger.log(LogStatus.INFO, "Entered Billing Address");

		OSCheckoutPage.placeOrder();
		logger.log(LogStatus.INFO, "Order Placed");
		OSCheckoutPage.navigateToHomepageStory(map.get("SSOR User"), map.get("Password"));

		logger.log(LogStatus.INFO, "Navigating to Home Page by Continue to LogIn");

		thankYouMessage = OSHomePage.getThankYouPanelMessage();
		Assert.assertEquals(DSSProperties.ActualThankYouMessage, thankYouMessage);
		logger.log(LogStatus.INFO, "Thank You Panel is displayed");
		accountMenuIcon = OSHomePage.isUserLoggedIn(map.get("SSOR User"));
		Assert.assertTrue(accountMenuIcon);
		logger.log(LogStatus.INFO, "User logged In");
		logger.log(LogStatus.PASS, "Test Completed Successfully!!");

	}

	@Test(dataProvider = "TestDataProvider", dataProviderClass = DSSDataProvider.class, enabled = false)
	public void BuyPrintPlusDigitalSubscriptionWithinAreaZIPWithNonSSOR(Map<String,String> map) throws InterruptedException {
	
		logger = report.startTest("Subscribe Print Plus Digital Subscription Within Area ZIP With Non-SSOR user");
		String thankYouMessage;
		boolean accountMenuIcon = false; 

		String email = util.generateEmailid(map.get("Market Name"));

		OSSubscriptionPage = OSHomePage.goToSubscriptionsFromHomepage();
		logger.log(LogStatus.INFO, "SubsCription Page is displayed");
		OSSubscriptionPage.addPrintDigitalPlusAccessWithinArea(map.get("Within Area Zip"));
		logger.log(LogStatus.INFO, "Entered Within Area ZIP");
		OSCheckoutPage.enterDigitalAccessNonSSOR(email, map.get("Password"), map.get("Password"));
		logger.log(LogStatus.INFO, "Entered Non-SSOR email and set password");
		OSCheckoutPage.payWithMyBankAccount(map.get("Bank Name"), map.get("Account No"), map.get("Routing No"));
		logger.log(LogStatus.INFO, "Entered Bank Account Details");

		OSCheckoutPage.enterAddressWhenBillingAndDeliveryInformationSame(map.get("First Name"), map.get("Last Name"), map.get("Valid address1"),
				map.get("Valid address2"), map.get("Within Area Zip"), map.get("City"), map.get("State"), map.get("Contact No"));
		logger.log(LogStatus.INFO, "Entered Billing Address");

		OSCheckoutPage.placeOrder();
		logger.log(LogStatus.INFO, "Order Placed");
		OSCheckoutPage.navigateToHomepageStory(email, map.get("Password"));
		logger.log(LogStatus.INFO, "Navigating to Home Page by Continue reading");

		thankYouMessage = OSHomePage.getThankYouPanelMessage();
		Assert.assertEquals(DSSProperties.ActualThankYouMessage, thankYouMessage);
		logger.log(LogStatus.INFO, "Thank You Panel is displayed");
		accountMenuIcon = OSHomePage.isUserLoggedIn(email);
		Assert.assertTrue(accountMenuIcon);
		logger.log(LogStatus.INFO, "User logged In");
		logger.log(LogStatus.PASS, "Test Completed Successfully!!");

	}

	@Test(dataProvider = "TestDataProvider", dataProviderClass = DSSDataProvider.class, enabled = false)
	public void BuyPrintPlusDigitalSubscriptionOutSideAreaZIPWithSSOR(Map<String,String> map) throws InterruptedException {

		logger = report.startTest("Subscribe Print Plus Digital Subscription Outside Area ZIP With SSOR user");

		String thankYouMessage;
		boolean accountMenuIcon = false; 

		String OutsideAreaZipValidationMag;

		OSSubscriptionPage = OSHomePage.goToSubscriptionsFromHomepage();
		logger.log(LogStatus.INFO, "SubsCription Page is displayed");
		OutsideAreaZipValidationMag = OSSubscriptionPage.availableOptionsForOutsideAreaZip(map.get("Out of area Zip"));
		Assert.assertEquals(DSSProperties.OutsideAreaZipValidationActualMesssage, OutsideAreaZipValidationMag);
		logger.log(LogStatus.INFO, "Verifying the out of are ZIP error message");
		logger.log(LogStatus.PASS, "Out of area zip error message is displayed. Hence validation passed!!");

		OSSubscriptionPage.proceedWithTryDigital();
		logger.log(LogStatus.INFO, "Proceeding with Try Digital");

		OSCheckoutPage.enterDigitalAccessSSOR(map.get("SSOR User"));
		logger.log(LogStatus.INFO, "Entered SSOR email");
		OSCheckoutPage.payWithMyBankAccount(map.get("Bank Name"), map.get("Account No"), map.get("Routing No"));
		logger.log(LogStatus.INFO, "Entered Bank Account Details");

		OSCheckoutPage.enterAddressWhenBillingAndDeliveryInformationSame(map.get("First Name"), map.get("Last Name"), map.get("Valid address1"),
				map.get("Valid address2"), map.get("Within Area Zip"), map.get("City"), map.get("State"), map.get("Contact No"));
		logger.log(LogStatus.INFO, "Entered Billing Address");

		OSCheckoutPage.placeOrder();
		logger.log(LogStatus.INFO, "Order Placed");
		OSCheckoutPage.navigateToHomepageStory(map.get("SSOR User"), map.get("Password"));
		logger.log(LogStatus.INFO, "Navigating to Home Page by Continue reading");

		thankYouMessage = OSHomePage.getThankYouPanelMessage();
		Assert.assertEquals(DSSProperties.ActualThankYouMessage, thankYouMessage);
		logger.log(LogStatus.INFO, "Thank You Panel is displayed");
		accountMenuIcon = OSHomePage.isUserLoggedIn(map.get("SSOR User"));
		Assert.assertTrue(accountMenuIcon);
		logger.log(LogStatus.INFO, "User logged In");
		logger.log(LogStatus.PASS, "Test Completed Successfully!!");

	}

	@Test(dataProvider = "TestDataProvider", dataProviderClass = DSSDataProvider.class, enabled = false)
	public void BuyPrintPlusDigitalSubscriptionOutSideAreaZIPWithNonSSOR(Map<String,String> map) throws InterruptedException {

		logger = report.startTest("Subscribe Print Plus Digital Subscription Outside Area ZIP With Non-SSOR user");

		String thankYouMessage;
		boolean accountMenuIcon = false; 
		String OutsideAreaZipValidationMag;

		String email = util.generateEmailid(map.get("Market Name"));

		OSSubscriptionPage = OSHomePage.goToSubscriptionsFromHomepage();
		logger.log(LogStatus.INFO, "SubsCription Page is displayed");
		OutsideAreaZipValidationMag = OSSubscriptionPage.availableOptionsForOutsideAreaZip(map.get("Out of area Zip"));
		Assert.assertEquals(DSSProperties.OutsideAreaZipValidationActualMesssage, OutsideAreaZipValidationMag);
		logger.log(LogStatus.INFO, "Verfying the out of are ZIP error message");
		logger.log(LogStatus.PASS, "Out of area zip error message is displayed. Hence validation passed!!");

		OSSubscriptionPage.proceedWithTryDigital();
		logger.log(LogStatus.INFO, "Proceeding with Try Digital");
		OSCheckoutPage.enterDigitalAccessNonSSOR(email, map.get("Password"), map.get("Password"));
		OSCheckoutPage.payWithCreditCard(map.get("Credit Card Holder Name"), map.get("Credit Card No"), map.get("Month"), map.get("Year"));
		logger.log(LogStatus.INFO, "Entered Credit Card Details");

		OSCheckoutPage.enterAddressWhenBillingAndDeliveryInformationSame(map.get("First Name"), map.get("Last Name"), map.get("Valid address1"),
				map.get("Valid address2"), map.get("Within Area Zip"), map.get("City"), map.get("State"), map.get("Contact No"));
		logger.log(LogStatus.INFO, "Entered Billing Address");

		OSCheckoutPage.placeOrder();
		logger.log(LogStatus.INFO, "Order Placed");
		OSCheckoutPage.navigateToHomepageStory(email, map.get("Password"));

		logger.log(LogStatus.INFO, "Navigating to Home Page by Continue to LogIn");

		thankYouMessage = OSHomePage.getThankYouPanelMessage();
		Assert.assertEquals(DSSProperties.ActualThankYouMessage, thankYouMessage);
		logger.log(LogStatus.INFO, "Thank You Panel is displayed");
		accountMenuIcon = OSHomePage.isUserLoggedIn(email);
		Assert.assertTrue(accountMenuIcon);
		logger.log(LogStatus.INFO, "User logged In");
		logger.log(LogStatus.PASS, "Test Completed Successfully!!");

	}

	@Test(dataProvider = "TestDataProvider", dataProviderClass = DSSDataProvider.class, enabled = false)
	public void BuydigitalPlusSubscriptionWithSSOR(Map<String,String> map)
			throws InterruptedException {

		logger = report.startTest("Subscribe DigitalPlus Subscription With SSOR user");

		String thankYouMessage;
		boolean accountMenuIcon = false; 
		
		OSSubscriptionPage = OSHomePage.goToSubscriptionsFromHomepage();
		logger.log(LogStatus.INFO, "SubsCription Page is displayed");
		OSSubscriptionPage.addDigitalPlusSubscription();
		logger.log(LogStatus.INFO, "Added DigitalPlus subscription");
		OSCheckoutPage.selectPackage(driver, DSSProperties.DIGITAL_SAVER);
		logger.log(LogStatus.INFO, "Digital package selected");
		OSCheckoutPage.enterDigitalAccessSSOR(map.get("SSOR User"));
		logger.log(LogStatus.INFO, "Entered SSOR email");
		OSCheckoutPage.payWithCreditCard(map.get("Credit Card Holder Name"), map.get("Credit Card No"), map.get("Month"), map.get("Year"));
		logger.log(LogStatus.INFO, "Entered Credit Card Details");

		OSCheckoutPage.enterAddressWhenBillingAndDeliveryInformationSame(map.get("First Name"), map.get("Last Name"), map.get("Valid address1"),
				map.get("Valid address2"), map.get("Within Area Zip"), map.get("City"), map.get("State"), map.get("Contact No"));
		logger.log(LogStatus.INFO, "Entered Billing Address");

		OSCheckoutPage.placeOrder();
		logger.log(LogStatus.INFO, "Order Placed");
		OSCheckoutPage.navigateToHomepageStory(map.get("SSOR User"), map.get("Password"));

		logger.log(LogStatus.INFO, "Navigating to Home Page by Continue to LogIn");

		thankYouMessage = OSHomePage.getThankYouPanelMessage();
		Assert.assertEquals(DSSProperties.ActualThankYouMessage, thankYouMessage);
		
		logger.log(LogStatus.INFO, "Thank You Panel is displayed");
		accountMenuIcon = OSHomePage.isUserLoggedIn(map.get("SSOR User"));
		Assert.assertTrue(accountMenuIcon);
		logger.log(LogStatus.INFO, "User logged In");
		logger.log(LogStatus.PASS, "Test Completed Successfully!!");

	}

	@Test(dataProvider = "TestDataProvider", dataProviderClass = DSSDataProvider.class, enabled = true)
	public void BuydigitalPlusSubscriptionWithNonSSOR(Map<String,String> map) throws InterruptedException {

		logger = report.startTest("Subscribe DigitalPlus Subscription With Non-SSOR user");

		String thankYouMessage;
		boolean accountMenuIcon = false; 

		String email = util.generateEmailid(map.get("Market Name"));

		OSSubscriptionPage = OSHomePage.goToSubscriptionsFromHomepage();
		logger.log(LogStatus.INFO, "SubsCription Page is displayed");
		OSSubscriptionPage.addDigitalPlusSubscription();
		logger.log(LogStatus.INFO, "Added DigitalPlus subscription");
		OSCheckoutPage.selectPackage(driver, DSSProperties.DIGITAL);
		logger.log(LogStatus.INFO, "Digital package selected");
		OSCheckoutPage.enterDigitalAccessNonSSOR(email, map.get("Password"), map.get("Password"));
		logger.log(LogStatus.INFO, "Entered Non-SSOR email and set password");
		OSCheckoutPage.payWithCreditCard(map.get("Credit Card Holder Name"), map.get("Credit Card No"), map.get("Month"), map.get("Year"));
		logger.log(LogStatus.INFO, "Entered Credit Card Details");

		OSCheckoutPage.enterAddressWhenBillingAndDeliveryInformationSame(map.get("First Name"), map.get("Last Name"), map.get("Valid address1"),
				map.get("Valid address2"), map.get("Within Area Zip"), map.get("City"), map.get("State"), map.get("Contact No"));
		logger.log(LogStatus.INFO, "Entered Billing Address");

		OSCheckoutPage.placeOrder();
		logger.log(LogStatus.INFO, "Order Placed");
		OSCheckoutPage.navigateToHomepageStory(email, map.get("Password"));

		logger.log(LogStatus.INFO, "Navigating to Home Page by Continue to LogIn");

		thankYouMessage = OSHomePage.getThankYouPanelMessage();
		Assert.assertEquals(DSSProperties.ActualThankYouMessage, thankYouMessage);
		logger.log(LogStatus.INFO, "Thank You Panel is displayed");
		accountMenuIcon = OSHomePage.isUserLoggedIn(email);
		Assert.assertTrue(accountMenuIcon);
		logger.log(LogStatus.INFO, "User logged In");
		logger.log(LogStatus.PASS, "Test Completed Successfully!!");

	}

	@AfterMethod
	public void afterTest(ITestResult result) {

		if (result.getStatus() == ITestResult.FAILURE) {

			String screenshot_path = util.captureScrenshot(driver, result.getName());
			String image = logger.addScreenCapture(screenshot_path);
			logger.log(LogStatus.FAIL, "Test Verification", image);

		}

		report.endTest(logger);
		report.flush();
		driver.quit();

	}

}
