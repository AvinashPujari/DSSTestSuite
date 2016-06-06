package com.dss.test.dss.pageobject;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * ------- OSentinelCheckoutPageObject ------- Author: QA-DART Created on:
 * 18-May-2016 History of Changes: Page Object for OS Checkout Page
 */
public class OSentinelCheckoutPageObject extends CheckoutPageObject {

	private WebDriver driver;

	public OSentinelCheckoutPageObject(WebDriver driver) {

		super(driver);

	}

	@Override
	public void selectPackage(WebDriver driver, String subscription) {
		// TODO Auto-generated method stub

		List<WebElement> dititalPlusOffers = driver.findElements(By.xpath("html/body/div[1]/div[3]/div[1]/div[2]//h3"));
		for (WebElement offer : dititalPlusOffers) {
			if (offer.getText().equalsIgnoreCase(subscription))
				offer.click();
		}

	}

}
