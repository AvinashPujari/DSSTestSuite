package com.dss.test.dss.pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import com.dss.test.properties.DSSProperties;

/**
 * ------- CheckoutPageObject ------- Author: QA-DART Created on: 17-May-2016
 * History of Changes: Parent Class for DSS Checkout Page
 */
public abstract class CheckoutPageObject {

	private WebDriver driver;

	public CheckoutPageObject(WebDriver driver) {

		this.driver = driver;
	}

	public abstract void selectPackage(WebDriver driver, String subscription);

	private void login(String email, String password) {

		driver.findElement(By.xpath("//*[@id='reg-overlay']/div/div[2]/form/fieldset[1]/input")).sendKeys(email);
		driver.findElement(By.xpath("//*[@id='reg-overlay']/div/div[2]/form/fieldset[2]/input")).sendKeys(password);
		driver.findElement(By.xpath("//*[@id='reg-overlay']/div/div[2]/form/button")).click();

	}

	public void enterDigitalAccessSSOR(String SSOREmail) throws InterruptedException {

		driver.findElement(By.cssSelector("#digitalAccess > div:nth-of-type(2) > div > div:nth-of-type(1) > input"))
				.clear();
		driver.findElement(By.cssSelector("#digitalAccess > div:nth-of-type(2) > div > div:nth-of-type(1) > input"))
				.sendKeys(SSOREmail);

		Thread.sleep(2000);

		driver.findElement(By.xpath("//*[@id='digitalAccess']/div[2]/div/a")).click();

	}

	public void enterDigitalAccessNonSSOR(String NonSSOREmail, String password, String Confpassword) {

		driver.findElement(By.cssSelector("#digitalAccess > div:nth-of-type(2) > div > div:nth-of-type(1) > input"))
				.clear();
		driver.findElement(By.cssSelector("#digitalAccess > div:nth-of-type(2) > div > div:nth-of-type(1) > input"))
				.sendKeys(NonSSOREmail);

		driver.findElement(By.xpath("//*[@id='digitalAccess']/div[2]/div/div[2]/input")).clear();
		driver.findElement(By.xpath("//*[@id='digitalAccess']/div[2]/div/div[2]/input")).sendKeys(password);

		driver.findElement(By.xpath("//*[@id='digitalAccess']/div[2]/div/div[3]/input")).clear();
		driver.findElement(By.xpath("//*[@id='digitalAccess']/div[2]/div/div[3]/input")).sendKeys(Confpassword);

		driver.findElement(By.xpath("//*[@id='digitalAccess']/div[2]/div/a")).click();

	}

	public void payWithCreditCard(String name, String cardNumber, String Month, String Year)
			throws InterruptedException {

		driver.findElement(
				By.cssSelector("#payment > div:nth-of-type(2) > div > div:nth-of-type(1) > div:nth-of-type(1) > input"))
				.clear();
		driver.findElement(
				By.cssSelector("#payment > div:nth-of-type(2) > div > div:nth-of-type(1) > div:nth-of-type(1) > input"))
				.sendKeys(name);
		driver.findElement(
				By.cssSelector("#payment > div:nth-of-type(2) > div > div:nth-of-type(1) > div:nth-of-type(2) > input"))
				.clear();
		driver.findElement(
				By.cssSelector("#payment > div:nth-of-type(2) > div > div:nth-of-type(1) > div:nth-of-type(2) > input"))
				.sendKeys(cardNumber);

		Select monthDDN = new Select(driver.findElement(By.cssSelector(
				"#payment > div:nth-of-type(2) > div > div:nth-of-type(1) > div:nth-of-type(3) > select:nth-of-type(1)")));
		Select yearDDN = new Select(driver.findElement(By.cssSelector(
				"#payment > div:nth-of-type(2) > div > div:nth-of-type(1) > div:nth-of-type(3) > select:nth-of-type(2)")));

		monthDDN.selectByVisibleText(Month);
		yearDDN.selectByVisibleText(Year);

		Thread.sleep(1000);

		driver.findElement(By.xpath("//*[@id='payment']/div[2]/div/a")).click();

	}

	public void payWithMyBankAccount(String BankName, String AccountNumber, String RoutingNumber)
			throws InterruptedException {

		Thread.sleep(1000);

		driver.findElement(By.linkText("Pay with my Bank Account")).click();

		driver.findElement(
				By.cssSelector("#payment > div:nth-of-type(2) > div > div:nth-of-type(2) > div:nth-of-type(1) > input"))
				.clear();
		driver.findElement(
				By.cssSelector("#payment > div:nth-of-type(2) > div > div:nth-of-type(2) > div:nth-of-type(1) > input"))
				.sendKeys(BankName);

		driver.findElement(
				By.cssSelector("#payment > div:nth-of-type(2) > div > div:nth-of-type(2) > div:nth-of-type(2) > input"))
				.clear();
		driver.findElement(
				By.cssSelector("#payment > div:nth-of-type(2) > div > div:nth-of-type(2) > div:nth-of-type(2) > input"))
				.sendKeys(AccountNumber);

		driver.findElement(
				By.cssSelector("#payment > div:nth-of-type(2) > div > div:nth-of-type(2) > div:nth-of-type(3) > input"))
				.clear();
		driver.findElement(
				By.cssSelector("#payment > div:nth-of-type(2) > div > div:nth-of-type(2) > div:nth-of-type(3) > input"))
				.sendKeys(RoutingNumber);

		driver.findElement(By.xpath("//*[@id='payment']/div[2]/div/a")).click();

	}

	public void enterAddressWhenBillingAndDeliveryInformationSame(String firstName, String lastName, String address1,
			String address2, String zip, String city, String state, String phone) throws InterruptedException {

		Thread.sleep(1000);

		Select stateDDN = new Select(driver
				.findElement(By.xpath("//*[@id='address']/div[2]/div/div[1]/div[2]/div[4]/div[2]/div[2]/select")));

		driver.findElement(By.xpath("//*[@id='address']/div[2]/div/div[1]/div[2]/div[1]/input")).sendKeys(firstName);
		driver.findElement(By.xpath("//*[@id='address']/div[2]/div/div[1]/div[2]/div[2]/input")).sendKeys(lastName);
		driver.findElement(By.xpath("//*[@id='address']/div[2]/div/div[1]/div[2]/div[3]/input")).sendKeys(address1);
		driver.findElement(By.xpath("//*[@id='address']/div[2]/div/div[1]/div[2]/input")).sendKeys(address2);

		driver.findElement(By.xpath("//*[@id='address']/div[2]/div/div[1]/div[2]/div[4]/div[1]/input")).clear();
		driver.findElement(By.xpath("//*[@id='address']/div[2]/div/div[1]/div[2]/div[4]/div[1]/input")).sendKeys(zip);

		Thread.sleep(1000);

		driver.findElement(By.xpath("//*[@id='address']/div[2]/div/div[1]/div[2]/div[4]/div[2]/div[1]/input")).clear();
		driver.findElement(By.xpath("//*[@id='address']/div[2]/div/div[1]/div[2]/div[4]/div[2]/div[1]/input"))
				.sendKeys(city);

		stateDDN.selectByVisibleText(state);

		Thread.sleep(1000);

		driver.findElement(By.xpath("//*[@id='address']/div[2]/div/div[1]/div[2]/div[5]/input")).clear();
		driver.findElement(By.xpath("//*[@id='address']/div[2]/div/div[1]/div[2]/div[5]/input")).sendKeys(phone);

		Thread.sleep(1000);

		driver.findElement(By.xpath("//*[@id='address']/div[2]/div/a")).click();

	}

	public void enterAddressWehnBillingAndDeliveryInformationDifferent(String firstName, String lastName,
			String address1, String address2, String zip, String city, String state, String phone) {

		Select stateDDN = new Select(driver
				.findElement(By.xpath("//*[@id='address']/div[2]/div/div[1]/div[2]/div[4]/div[2]/div[2]/select")));

		driver.findElement(By.xpath("//*[@id='address']/div[2]/div/div[1]/div[2]/div[1]/input")).sendKeys(firstName);
		driver.findElement(By.xpath("//*[@id='address']/div[2]/div/div[1]/div[2]/div[2]/input")).sendKeys(lastName);
		driver.findElement(By.xpath("//*[@id='address']/div[2]/div/div[1]/div[2]/div[3]/input")).sendKeys(address1);
		driver.findElement(By.xpath("//*[@id='address']/div[2]/div/div[1]/div[2]/input")).sendKeys(address2);

		driver.findElement(By.xpath("//*[@id='address']/div[2]/div/div[1]/div[2]/div[4]/div[1]/input")).clear();
		driver.findElement(By.xpath("//*[@id='address']/div[2]/div/div[1]/div[2]/div[4]/div[1]/input")).sendKeys(zip);

		driver.findElement(By.xpath("//*[@id='address']/div[2]/div/div[1]/div[2]/div[4]/div[2]/div[1]/input")).clear();
		driver.findElement(By.xpath("//*[@id='address']/div[2]/div/div[1]/div[2]/div[4]/div[2]/div[1]/input"))
				.sendKeys(city);

		stateDDN.selectByVisibleText(state);

		driver.findElement(By.xpath("//*[@id='address']/div[2]/div/div[1]/div[2]/div[5]/input")).clear();
		driver.findElement(By.xpath("//*[@id='address']/div[2]/div/div[1]/div[2]/div[5]/input")).sendKeys(phone);

		driver.findElement(By.xpath("//*[@id='address']/div[2]/div/div[2]/span/input")).click();

		driver.findElement(By.xpath("//*[@id='address']/div[2]/div/div[1]/div[2]/div[1]/input")).sendKeys(firstName);
		driver.findElement(By.xpath("//*[@id='address']/div[2]/div/div[1]/div[2]/div[2]/input")).sendKeys(lastName);
		driver.findElement(By.xpath("//*[@id='address']/div[2]/div/div[1]/div[2]/div[3]/input")).sendKeys(address1);
		driver.findElement(By.xpath("//*[@id='address']/div[2]/div/div[1]/div[2]/input")).sendKeys(address2);

		driver.findElement(By.xpath("//*[@id='address']/div[2]/div/div[1]/div[2]/div[4]/div[1]/input")).clear();
		driver.findElement(By.xpath("//*[@id='address']/div[2]/div/div[1]/div[2]/div[4]/div[1]/input")).sendKeys(zip);

		driver.findElement(By.xpath("//*[@id='address']/div[2]/div/div[1]/div[2]/div[4]/div[2]/div[1]/input")).clear();
		driver.findElement(By.xpath("//*[@id='address']/div[2]/div/div[1]/div[2]/div[4]/div[2]/div[1]/input"))
				.sendKeys(city);

		stateDDN.selectByVisibleText(state);

		driver.findElement(By.xpath("//*[@id='address']/div[2]/div/div[1]/div[2]/div[5]/input")).clear();
		driver.findElement(By.xpath("//*[@id='address']/div[2]/div/div[1]/div[2]/div[5]/input")).sendKeys(phone);

		driver.findElement(By.xpath("//*[@id='address']/div[2]/div/a")).click();

	}

	public void placeOrder() throws InterruptedException {

		Thread.sleep(1000);
		WebElement placeOrderbtn = driver.findElement(By.xpath("//*[@id='placeOrder']/div[2]/div/div[4]/a[1]/span"));
		Actions actions = new Actions(driver);
		actions.moveToElement(placeOrderbtn).click().build().perform();

		Thread.sleep(5000);

		actions.moveToElement(placeOrderbtn).click().build().perform();

	}

	public void editPaymentInfo() {

	}

	public void editAddress() {

	}

	// This will work for SSOR and Non-SSOR users: For Non-SSOR users pass "",""
	// for email, password
	public void navigateToHomepageStory(String email, String password) throws InterruptedException {

		Thread.sleep(3000);

		WebElement logInToContinueBtn = driver.findElement(By.xpath("//*[@id='confirm']/a[2]"));
		WebElement continueReadingBtn = driver.findElement(By.xpath("//*[@id='confirm']/a[1]"));

		// SSOR:
		if ((logInToContinueBtn != null) && (logInToContinueBtn.getText().equals(DSSProperties.LOGIN_TO_CONTINUE))) {

			Thread.sleep(1000);

			logInToContinueBtn.click();

			String checkoutPageHandle = driver.getWindowHandle();

			for (String WindowHandle : driver.getWindowHandles())

			{
				driver.switchTo().window(WindowHandle);

			}

			login(email, password);

			driver.switchTo().window(checkoutPageHandle);
			// Non-SSOR:
			// Log In To Continue
		}

		if ((continueReadingBtn != null) && (continueReadingBtn.getText().equals(DSSProperties.CONTINUE_READING))) {

			Thread.sleep(2000);
			continueReadingBtn.click();
		}

	}

}
