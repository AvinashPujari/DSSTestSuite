package com.dss.test.dss.subscription;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

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

public class SubscriptionTests {

	private WebDriver driver;

	private ExtentTest logger;
	private OSentinelHomepagePageObject OSHomePage;
	private OSentinelSubscriptionPageObject OSSubscriptionPage;
	private OSentinelCheckoutPageObject OSCheckoutPage;
	private DSSUtilities util;

	private ExtentReports report = new ExtentReports(DSSProperties.ExtentReportPath);

	@Parameters({ "browser", "version" })
	@BeforeMethod(alwaysRun = true)
	public void beforeTest(String browser, String version) throws MalformedURLException {

		DesiredCapabilities caps = new DesiredCapabilities();

		if (browser.equalsIgnoreCase("firefox")) {

			caps.setBrowserName("firefox");
		}

		if (browser.equalsIgnoreCase("chrome")) {

			caps.setBrowserName("chrome");

		}

		if (browser.equalsIgnoreCase("internet explorer")) {

			caps.setBrowserName("internet explorer");

		}

		caps.setPlatform(Platform.WINDOWS);
		caps.setVersion(version);

		driver = new RemoteWebDriver(new URL(DSSProperties.hubUrl), caps);

		driver.manage().window().maximize();
		driver.get(DSSProperties.URL);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		OSHomePage = new OSentinelHomepagePageObject(driver);
		OSCheckoutPage = new OSentinelCheckoutPageObject(driver);
		util = new DSSUtilities();

	}

	@Test(dataProvider = "dssDataProviderWithInAreaZIPSSOR", dataProviderClass = DSSDataProvider.class, enabled = true)
	public void BuyPrintPlusDigitalSubscriptionWithinAreaZIPWithSSOR(String withInAreaZIP, String email, String CCName,
			String CCNumber, String CCMonth, String CCYear, String userFirstName, String userLastName,
			String userAddress1, String userAddress2, String UserZIP, String UserCity, String UserState,
			String userPhonenmum, String pass) throws InterruptedException {

		logger = report.startTest("Subscribe Print Plus Digital Subscription Within Area ZIP With SSOR user");

		String thankYouMessage;

		OSSubscriptionPage = OSHomePage.goToSubscriptionsFromHomepage();
		logger.log(LogStatus.INFO, "SubsCription Page is displayed");
		OSSubscriptionPage.addPrintDigitalPlusAccessWithinArea(withInAreaZIP);
		logger.log(LogStatus.INFO, "Entered Within Area ZIP");
		OSCheckoutPage.enterDigitalAccessSSOR(email);
		logger.log(LogStatus.INFO, "Entered SSOR email");
		OSCheckoutPage.payWithCreditCard(CCName, CCNumber, CCMonth, CCYear);
		logger.log(LogStatus.INFO, "Entered Credit Card Details");

		OSCheckoutPage.enterAddressWhenBillingAndDeliveryInformationSame(userFirstName, userLastName, userAddress1,
				userAddress2, UserZIP, UserCity, UserState, userPhonenmum);
		logger.log(LogStatus.INFO, "Entered Billing Address");
		OSCheckoutPage.placeOrder();
		logger.log(LogStatus.INFO, "Order Placed");
		OSCheckoutPage.navigateToHomepageStory(email, pass);

		logger.log(LogStatus.INFO, "Navigating to Home Page by Continue to LogIn");

		thankYouMessage = OSHomePage.getThankYouPanelMessage();
		Assert.assertEquals(DSSProperties.ActualThankYouMessage, thankYouMessage);
		logger.log(LogStatus.INFO, "Thank You Panel is displayed");
		logger.log(LogStatus.PASS, "Test Completed Successfully!!");

	}

	@Test(dataProvider = "dssDataProviderWithInAreaZIPNonSSOR", dataProviderClass = DSSDataProvider.class, enabled = true)
	public void BuyPrintPlusDigitalSubscriptionWithinAreaZIPWithNonSSOR(String withInAreaZIP, String pass,
			String BankName, String BankAccountNumber, String BankRoutingNumber, String userFirstName,
			String userLastName, String userAddress1, String userAddress2, String UserZIP, String UserCity,
			String UserState, String userPhonenmum) throws InterruptedException {

		logger = report.startTest("Subscribe Print Plus Digital Subscription Within Area ZIP With Non-SSOR user");
		String thankYouMessage;

		Random random = new Random();
		int randomnum = random.nextInt();

		String email = "jan" + randomnum + "@gmail.com";

		OSSubscriptionPage = OSHomePage.goToSubscriptionsFromHomepage();
		logger.log(LogStatus.INFO, "SubsCription Page is displayed");
		OSSubscriptionPage.addPrintDigitalPlusAccessWithinArea(withInAreaZIP);
		logger.log(LogStatus.INFO, "Entered Within Area ZIP");
		OSCheckoutPage.enterDigitalAccessNonSSOR(email, pass, pass);
		logger.log(LogStatus.INFO, "Entered Non-SSOR email and set password");
		OSCheckoutPage.payWithMyBankAccount(BankName, BankAccountNumber, BankRoutingNumber);
		logger.log(LogStatus.INFO, "Entered Bank Account Details");

		OSCheckoutPage.enterAddressWhenBillingAndDeliveryInformationSame(userFirstName, userLastName, userAddress1,
				userAddress2, UserZIP, UserCity, UserState, userPhonenmum);
		logger.log(LogStatus.INFO, "Entered Billing Address");

		OSCheckoutPage.placeOrder();
		logger.log(LogStatus.INFO, "Order Placed");
		OSCheckoutPage.navigateToHomepageStory(email, pass);
		logger.log(LogStatus.INFO, "Navigating to Home Page by Continue reading");

		thankYouMessage = OSHomePage.getThankYouPanelMessage();
		Assert.assertEquals(DSSProperties.ActualThankYouMessage, thankYouMessage);
		logger.log(LogStatus.INFO, "Thank You Panel is displayed");
		logger.log(LogStatus.PASS, "Test Completed Successfully!!");

	}

	@Test(dataProvider = "dssDataProviderOutsideAreaZIPSSOR", dataProviderClass = DSSDataProvider.class, enabled = true)
	public void BuyPrintPlusDigitalSubscriptionOutSideAreaZIPWithSSOR(String outSideAreaZIP, String email,
			String BankName, String BankAccountNumber, String BankRoutingNumber, String userFirstName,
			String userLastName, String userAddress1, String userAddress2, String UserZIP, String UserCity,
			String UserState, String userPhonenmum, String pass) throws InterruptedException {

		logger = report.startTest("Subscribe Print Plus Digital Subscription Outside Area ZIP With SSOR user");

		String thankYouMessage;

		String OutsideAreaZipValidationMag;

		OSSubscriptionPage = OSHomePage.goToSubscriptionsFromHomepage();
		logger.log(LogStatus.INFO, "SubsCription Page is displayed");
		OutsideAreaZipValidationMag = OSSubscriptionPage.availableOptionsForOutsideAreaZip(outSideAreaZIP);
		Assert.assertEquals(DSSProperties.OutsideAreaZipValidationActualMesssage, OutsideAreaZipValidationMag);
		logger.log(LogStatus.INFO, "Verfying the out of are ZIP error message");
		logger.log(LogStatus.PASS, "Out of area zip error message is displayed. Hence validation passed!!");

		OSSubscriptionPage.proceedWithTryDigital();
		logger.log(LogStatus.INFO, "Proceeding with Try Digital");

		OSCheckoutPage.enterDigitalAccessSSOR(email);
		logger.log(LogStatus.INFO, "Entered SSOR email");
		OSCheckoutPage.payWithMyBankAccount(BankName, BankAccountNumber, BankRoutingNumber);
		logger.log(LogStatus.INFO, "Entered Bank Account Details");

		OSCheckoutPage.enterAddressWhenBillingAndDeliveryInformationSame(userFirstName, userLastName, userAddress1,
				userAddress2, UserZIP, UserCity, UserState, userPhonenmum);
		logger.log(LogStatus.INFO, "Entered Billing Address");
		OSCheckoutPage.placeOrder();
		logger.log(LogStatus.INFO, "Order Placed");
		OSCheckoutPage.navigateToHomepageStory(email, pass);
		logger.log(LogStatus.INFO, "Navigating to Home Page by Continue to LogIn");

		thankYouMessage = OSHomePage.getThankYouPanelMessage();
		Assert.assertEquals(DSSProperties.ActualThankYouMessage, thankYouMessage);
		logger.log(LogStatus.INFO, "Thank You Panel is displayed");
		logger.log(LogStatus.PASS, "Test Completed Successfully!!");

	}

	@Test(dataProvider = "dssDataProviderOutsideAreaZIPNonSSOR", dataProviderClass = DSSDataProvider.class, enabled = true)
	public void BuyPrintPlusDigitalSubscriptionOutSideAreaZIPWithNonSSOR(String outSideAreaZIP, String pass,
			String CCName, String CCNumber, String CCMonth, String CCYear, String userFirstName, String userLastName,
			String userAddress1, String userAddress2, String UserZIP, String UserCity, String UserState,
			String userPhonenmum) throws InterruptedException {

		logger = report.startTest("Subscribe Print Plus Digital Subscription Outside Area ZIP With Non-SSOR user");

		String thankYouMessage;

		String OutsideAreaZipValidationMag;

		Random random = new Random();
		int randomnum = random.nextInt();

		String email = "jan" + randomnum + "@gmail.com";

		OSSubscriptionPage = OSHomePage.goToSubscriptionsFromHomepage();
		logger.log(LogStatus.INFO, "SubsCription Page is displayed");
		OutsideAreaZipValidationMag = OSSubscriptionPage.availableOptionsForOutsideAreaZip(outSideAreaZIP);
		Assert.assertEquals(DSSProperties.OutsideAreaZipValidationActualMesssage, OutsideAreaZipValidationMag);
		logger.log(LogStatus.INFO, "Verfying the out of are ZIP error message");
		logger.log(LogStatus.PASS, "Out of area zip error message is displayed. Hence validation passed!!");

		OSSubscriptionPage.proceedWithTryDigital();
		logger.log(LogStatus.INFO, "Proceeding with Try Digital");
		OSCheckoutPage.enterDigitalAccessNonSSOR(email, pass, pass);
		logger.log(LogStatus.INFO, "Entered Non-SSOR email and set password");
		OSCheckoutPage.payWithCreditCard(CCName, CCNumber, CCMonth, CCYear);
		logger.log(LogStatus.INFO, "Entered Credit Card Details");

		OSCheckoutPage.enterAddressWhenBillingAndDeliveryInformationSame(userFirstName, userLastName, userAddress1,
				userAddress2, UserZIP, UserCity, UserState, userPhonenmum);
		logger.log(LogStatus.INFO, "Entered Billing Address");
		OSCheckoutPage.placeOrder();
		logger.log(LogStatus.INFO, "Order Placed");
		OSCheckoutPage.navigateToHomepageStory(email, pass);
		logger.log(LogStatus.INFO, "Navigating to Home Page by Continue reading");

		thankYouMessage = OSHomePage.getThankYouPanelMessage();
		Assert.assertEquals(DSSProperties.ActualThankYouMessage, thankYouMessage);
		logger.log(LogStatus.INFO, "Thank You Panel is displayed");
		logger.log(LogStatus.PASS, "Test Completed Successfully!!");

	}

	@Test(dataProvider = "dssDataProviderDigitalPlusSSOR", dataProviderClass = DSSDataProvider.class, enabled = true)
	public void BuydigitalPlusSubscriptionWithSSOR(String subscription, String email, String CCName, String CCNumber,
			String CCMonth, String CCYear, String userFirstName, String userLastName, String userAddress1,
			String userAddress2, String UserZIP, String UserCity, String UserState, String userPhonenmum, String pass)
			throws InterruptedException {

		logger = report.startTest("Subscribe DigitalPlus Subscription With SSOR user");

		String thankYouMessage;

		OSSubscriptionPage = OSHomePage.goToSubscriptionsFromHomepage();
		logger.log(LogStatus.INFO, "SubsCription Page is displayed");
		OSSubscriptionPage.addDigitalPlusSubscription();
		logger.log(LogStatus.INFO, "Added DigitalPlus subscription");
		OSCheckoutPage.selectPackage(driver, subscription);
		logger.log(LogStatus.INFO, "Digital package selected");
		OSCheckoutPage.enterDigitalAccessSSOR(email);
		logger.log(LogStatus.INFO, "Entered SSOR email");
		OSCheckoutPage.payWithCreditCard(CCName, CCNumber, CCMonth, CCYear);
		logger.log(LogStatus.INFO, "Entered Credit Card Details");

		OSCheckoutPage.enterAddressWhenBillingAndDeliveryInformationSame(userFirstName, userLastName, userAddress1,
				userAddress2, UserZIP, UserCity, UserState, userPhonenmum);
		logger.log(LogStatus.INFO, "Entered Billing Address");
		OSCheckoutPage.placeOrder();
		logger.log(LogStatus.INFO, "Order Placed");
		OSCheckoutPage.navigateToHomepageStory(email, pass);
		logger.log(LogStatus.INFO, "Navigating to Home Page by Continue to LogIn");

		thankYouMessage = OSHomePage.getThankYouPanelMessage();
		Assert.assertEquals(DSSProperties.ActualThankYouMessage, thankYouMessage);
		logger.log(LogStatus.INFO, "Thank You Panel is displayed");
		logger.log(LogStatus.PASS, "Test Completed Successfully!!");

	}

	@Test(dataProvider = "dssDataProviderDigitalPlusNonSSOR", dataProviderClass = DSSDataProvider.class, enabled = true)
	public void BuydigitalPlusSubscriptionWithNonSSOR(String subscription, String pass, String BankName,
			String BankAccountNumber, String BankRoutingNumber, String userFirstName, String userLastName,
			String userAddress1, String userAddress2, String UserZIP, String UserCity, String UserState,
			String userPhonenmum) throws InterruptedException {

		logger = report.startTest("Subscribe DigitalPlus Subscription With Non-SSOR user");

		String thankYouMessage;

		Random random = new Random();
		int randomnum = random.nextInt();

		String email = "jan" + randomnum + "@gmail.com";

		OSSubscriptionPage = OSHomePage.goToSubscriptionsFromHomepage();
		logger.log(LogStatus.INFO, "SubsCription Page is displayed");
		OSSubscriptionPage.addDigitalPlusSubscription();
		logger.log(LogStatus.INFO, "Added DigitalPlus subscription");
		OSCheckoutPage.selectPackage(driver, subscription);
		logger.log(LogStatus.INFO, "Digital package selected");
		OSCheckoutPage.enterDigitalAccessNonSSOR(email, pass, pass);
		logger.log(LogStatus.INFO, "Entered Non-SSOR email and set password");
		OSCheckoutPage.payWithMyBankAccount(BankName, BankAccountNumber, BankRoutingNumber);
		logger.log(LogStatus.INFO, "Entered Bank Account Details");

		OSCheckoutPage.enterAddressWhenBillingAndDeliveryInformationSame(userFirstName, userLastName, userAddress1,
				userAddress2, UserZIP, UserCity, UserState, userPhonenmum);
		logger.log(LogStatus.INFO, "Entered Billing Address");

		OSCheckoutPage.placeOrder();
		logger.log(LogStatus.INFO, "Order Placed");
		OSCheckoutPage.navigateToHomepageStory(email, pass);
		logger.log(LogStatus.INFO, "Navigating to Home Page by Continue reading");

		thankYouMessage = OSHomePage.getThankYouPanelMessage();
		Assert.assertEquals(DSSProperties.ActualThankYouMessage, thankYouMessage);
		logger.log(LogStatus.INFO, "Thank You Panel is displayed");
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
