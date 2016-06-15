package com.dss.test.dss.pageobject;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

/**
 * ------- OSentinelHomepagePageObject ------- Author: QA-DART Created on:
 * 18-May-2016 History of Changes: Page Object for OS Homepage
 */
public class OSentinelHomepagePageObject extends LandingPageObject {

	private WebDriver driver;

	public OSentinelHomepagePageObject(WebDriver driver) {

		this.driver = driver;

	}

	public OSentinelSubscriptionPageObject goToSubscriptionsFromHomepage() {

		driver.findElement(By.xpath("/html/body/header/div[1]/div[1]/a[2]")).click();

		return new OSentinelSubscriptionPageObject(driver);

	}

	public String getThankYouPanelMessage() throws InterruptedException {

		Thread.sleep(5000);
		String ThankYouMsg = null;

		if (driver.findElement(By.xpath("html/body/header/div[2]/ul/li[1]/a")).isDisplayed()) {

			while (true) {

				driver.navigate().refresh();
				if (driver.findElement(By.xpath("//*[@id='reg-overlay']/div")) != null) {

					ThankYouMsg = driver.findElement(By.xpath("//*[@id='reg-overlay']/div/div[2]/p")).getText();

					driver.findElement(By.xpath("//*[@id='reg-overlay']/div/div[2]/div[2]/div")).click();

					break;
				}
			}

		} else {

			ThankYouMsg = driver.findElement(By.xpath("//*[@id='reg-overlay']/div/div[2]/p")).getText();

			driver.findElement(By.xpath("//*[@id='reg-overlay']/div/div[2]/div[2]/div")).click();
		}

		return ThankYouMsg;

	}

	
	public boolean isUserLoggedIn(String Emailid) throws InterruptedException
	{
		boolean userLoggedIn=false;
		
		
		String emailid="";
		String userName ="";
		int start=Emailid.indexOf('@');
		String UserName=Emailid.substring(0,start);
		Thread.sleep(4000);
		if (driver.findElement(By.xpath("html/body/header/div[1]/div[1]/a[4]")) != null) {
			
			Actions action = new Actions(driver);
			action.moveToElement(driver.findElement(By.xpath("html/body/header/div[1]/div[1]/a[4]")))
					.moveToElement(driver.findElement(By.xpath(
							"//div[@class='trb_nh_dss']/ul[@data-reg-role='minimodal']/li/a[@data-reg-text='Profile']")));
			action.click().build().perform();
			Thread.sleep(2000);
			emailid=driver.findElement(By.xpath("//*[@id='content']/div/div[2]/div/div/div[1]/div[2]")).getText();
			userName=driver.findElement(By.xpath("//*[@id='content']/div/div[2]/div/div/div[3]/div[2]")).getText();
			driver.navigate().back();
			if(emailid.equalsIgnoreCase(Emailid)&&userName.equalsIgnoreCase(UserName)){
				userLoggedIn = true;
			}
		} 
		return userLoggedIn;
	}
	
	@Override
	public boolean isSubscriptionPopUpDisplayed() throws InterruptedException {

		int noOfArticles;
		boolean isSubsPageDisplayed = false;
		List<WebElement> articles = driver.findElements(By.className(("trb_outfit_relatedListTitle_a")));
		noOfArticles = articles.size();
		System.out.println("No of articles displayed: " + noOfArticles);

		for (int i = 0; i <= noOfArticles; i++) {
			System.out.println("Value of i...: " + i);

			articles.get(i).click();
			Thread.sleep(2000);

			if (driver.findElement(By.className("trb_nh_wt")).isDisplayed()) {

				driver.navigate().to("http://www.latimes.com");
				Thread.sleep(2000);
				articles = driver.findElements(By.className(("trb_outfit_relatedListTitle_a")));

				System.out.println("Navigating back... ");

			} else {

				System.out.println("Subscription Pop-Up Displayed...");

				isSubsPageDisplayed = true;
				break;
			}

		}
		return isSubsPageDisplayed;
	}

}
