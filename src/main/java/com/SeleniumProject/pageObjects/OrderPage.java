package com.SeleniumProject.pageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.SeleniumProject.AbstractComponents.AbstractComponents;

public class OrderPage extends AbstractComponents{
	WebDriver driver;
	
	public OrderPage(WebDriver driver)
	{
		super(driver);
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}

    @FindBy(xpath="//tr/td[2]")
    List<WebElement> productNames;
	
	public boolean verifyOrderDisplay(String productName)
	{
		Boolean match=productNames.stream().anyMatch(product->product.getText().equalsIgnoreCase(productName));
		return match;
	}
	
	
}
