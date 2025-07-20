package com.SeleniumProject.pageObjects;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

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
	    WebElement prod = getProductByName(productName);
	    WebElement button = prod.findElement(addToCart);

	    // Scroll into view
	    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", button);

	    // Optional: Small sleep to let scroll complete smoothly
	    try {
	        Thread.sleep(500);
	    } catch (InterruptedException e) {
	        e.printStackTrace();
	    }

	    // Wait for clickability
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	    wait.until(ExpectedConditions.elementToBeClickable(button));

	    try {
	        // Try normal click
	        button.click();
	    } catch (org.openqa.selenium.ElementClickInterceptedException e) {
	        // Fallback: JavaScript click
	        System.out.println("⚠️ Click intercepted, using JS click for product: " + productName);
	        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", button);
	    }

	    waitForElementToAppear(toastMessage);
	    waitForElementToDisapppear(spinner);
	}

}
