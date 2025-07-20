package com.SeleniumProject.tests;

import java.time.Duration;
import java.util.List;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class E2E {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		
		String productName="ZARA COAT 3";
		WebDriverManager.chromedriver().setup();
		WebDriver driver=new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		driver.manage().window().maximize();
		driver.get("https://rahulshettyacademy.com/client");
		driver.findElement(By.id("userEmail")).sendKeys("rahulsinhaimuv@gmail.com");
		driver.findElement(By.id("userPassword")).sendKeys("Rahul@1234");
		driver.findElement(By.id("login")).click();
		WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(15));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class,'mb-3')]")));
		List<WebElement> products=driver.findElements(By.xpath("//div[contains(@class,'mb-3')]"));
		WebElement prod=products.stream().filter(product->product.findElement(By.xpath(".//b"))
				.getText().equals(productName)).findFirst()
		.orElse(null);
		prod.findElement(By.xpath(".//div[@class='card-body']/button[2]")).click();
		
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='toast-container']")));
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".ng-animating")));
		
		driver.findElement(By.xpath("//button[@routerlink='/dashboard/cart']")).click();
		
		List<WebElement> cartProducts=driver.findElements(By.xpath("//div[@class='cartSection']/h3"));
		Boolean match=cartProducts.stream().anyMatch(cartProduct->cartProduct.getText().equalsIgnoreCase(productName));
		Assert.assertTrue(match);
		
		WebElement checkoutbutton=driver.findElement(By.xpath("//li[@class='totalRow']/button"));
		JavascriptExecutor js=(JavascriptExecutor)driver;
		js.executeScript("arguments[0].scrollIntoView()", checkoutbutton);
		Thread.sleep(2000);
		checkoutbutton.click();
		
		Actions a=new Actions(driver);
        a.sendKeys(driver.findElement(By.xpath("//input[@placeholder='Select Country']")),"India").build().perform();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(@class,'ta-item')]")));
        List<WebElement> options=driver.findElements(By.xpath("//button[contains(@class,'ta-item')]"));
        for(WebElement option:options)
        {
        	if(option.getText().equalsIgnoreCase("india"))
        	{
        		option.click();
        		break;
        	}
        }
        
        driver.findElement(By.xpath("//a[contains(@class,'action__submit')]")).click();
        String message=driver.findElement(By.xpath("//h1[@class='hero-primary']")).getText();
        Assert.assertTrue(message.equalsIgnoreCase("Thankyou for the order."));
        
	}

}
