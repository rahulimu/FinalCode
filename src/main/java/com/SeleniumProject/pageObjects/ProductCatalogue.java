package com.SeleniumProject.pageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.SeleniumProject.AbstractComponents.AbstractComponents;

public class ProductCatalogue extends AbstractComponents {
	WebDriver driver;

	public ProductCatalogue(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//div[contains(@class,'mb-3')]")
	List<WebElement> products;
	
	@FindBy(css=".ng-animating")
	WebElement spinner;

	By productsBy = By.xpath("//div[contains(@class,'mb-3')]");
	By addToCart = By.xpath(".//div[@class='card-body']/button[2]");
	By toastMessage=By.xpath("//div[@id='toast-container']");

	public List<WebElement> getProductList() {
		waitForElementToAppear(productsBy);
		return products;
	}

	public WebElement getProductByName(String productName) {
		WebElement prod = getProductList().stream()
				.filter(product -> product.findElement(By.xpath(".//b")).getText().equals(productName)).findFirst()
				.orElse(null);
		//prod.findElement(By.xpath(".//div[@class='card-body']/button[2]")).click();
		return prod;
	}

	public void addProductToCart(String productName) {
		//waitForElementToDisapppear(spinner);
		WebElement prod = getProductByName(productName);
		//waitForElementToDisapppear(spinner);
		prod.findElement(addToCart).click();
		waitForElementToAppear(toastMessage);
		waitForElementToDisapppear(spinner);
		
	}

}
