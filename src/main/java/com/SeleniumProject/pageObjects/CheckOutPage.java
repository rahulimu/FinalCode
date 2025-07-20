package com.SeleniumProject.pageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

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
	
	public void selectCountry(String countryName)
	{
		Actions a=new Actions(driver);
		a.sendKeys(countryText, countryName).build().perform();
		waitForElementToAppear(results);
		for(WebElement country:selectCountry)
		{
			if(country.getText().equals(countryName))
			{
				country.click();
				break;
			}
		}
	}
	public ConfirmationPage submitOrder()
	{
		submit.click();
		return new ConfirmationPage(driver);
	}
	
}
