package com.SeleniumProject.pageObjects;

import java.time.Duration;

import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.SeleniumProject.AbstractComponents.AbstractComponents;

public class LandingPage extends AbstractComponents{
	WebDriver driver;
	
	public LandingPage(WebDriver driver)
	{
		super(driver);
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(id="userEmail")
	WebElement userEmail;
	
	@FindBy(id="userPassword")
	WebElement userPassword;
	
	@FindBy(id="login")
	WebElement submit;
	
	@FindBy(xpath="//div[contains(@class,'flyInOut')]")
	WebElement errorMessage;
	
	public ProductCatalogue loginApplication(String email, String password) {
	    userEmail.sendKeys(email);
	    userPassword.sendKeys(password);

	    // Scroll the submit button into view (helps on CI/Jenkins)
	    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", submit);

	    try {
	        // Wait explicitly until the button is clickable
	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	        wait.until(ExpectedConditions.elementToBeClickable(submit));

	        // Try to click
	        submit.click();
	    } catch (ElementClickInterceptedException e) {
	        // Fallback to JavaScript click if regular click fails
	        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", submit);
	    }

	    return new ProductCatalogue(driver);
	}

	
	public void goTo()
	{
		driver.get("https://rahulshettyacademy.com/client");
	}
	
	public String getErrorMessage()
	{
		waitForElement(errorMessage);
		return errorMessage.getText();
	}

}
