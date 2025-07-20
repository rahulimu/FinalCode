package com.SeleniumProject.pageObjects;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.SeleniumProject.AbstractComponents.AbstractComponents;

public class CheckOutPage extends AbstractComponents{
	WebDriver driver;
	
	public CheckOutPage(WebDriver driver)
	{
		super(driver);
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath="//input[@placeholder='Select Country']")
	WebElement countryText;
	
	@FindBy(xpath="//button[contains(@class,'ta-item')]")
	List<WebElement> selectCountry;
	
	@FindBy(xpath="//a[contains(@class,'action__submit')]")
	WebElement submit;
	
	By results=By.xpath("//button[contains(@class,'ta-item')]");
	
	public void selectCountry(String countryName) {
	    countryText.clear();
	    countryText.sendKeys(countryName);

	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
	    wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(results));

	    System.out.println("✅ Found " + selectCountry.size() + " country options.");

	    for (WebElement country : selectCountry) {
	        System.out.println("🔍 Checking country: " + country.getText());
	        if (country.getText().equalsIgnoreCase(countryName)) {
	            try {
	                wait.until(ExpectedConditions.elementToBeClickable(country));
	                country.click();
	                System.out.println("✅ Selected country: " + countryName);
	            } catch (org.openqa.selenium.ElementClickInterceptedException e) {
	                System.out.println("⚠️ ElementClickInterceptedException: using JS click as fallback.");
	                ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", country);
	            }
	            return;
	        }
	    }

	    System.out.println("❌ Country not found in suggestions: " + countryName);
	}

	public ConfirmationPage submitOrder()
	{
		submit.click();
		return new ConfirmationPage(driver);
	}
	
}
