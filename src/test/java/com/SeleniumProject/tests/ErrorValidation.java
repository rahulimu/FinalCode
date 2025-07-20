package com.SeleniumProject.tests;

import org.testng.annotations.Test;
import org.testng.Assert;
import org.testng.AssertJUnit;
import java.io.IOException;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import com.SeleniumProject.TestComponents.BaseTest;
import com.SeleniumProject.pageObjects.CartPage;
import com.SeleniumProject.pageObjects.CheckOutPage;
import com.SeleniumProject.pageObjects.ConfirmationPage;
import com.SeleniumProject.pageObjects.LandingPage;
import com.SeleniumProject.pageObjects.ProductCatalogue;

import io.github.bonigarcia.wdm.WebDriverManager;

public class ErrorValidation extends BaseTest {

	//@Test(groups= {"ErrorHandling"})
	@Test
	public void errorValidation() throws IOException {
		// TODO Auto-generated method stub
        landingPage.loginApplication("rahulsinha123imuv@gmail.com", "Rahul@1234");
		Assert.assertEquals("Incorrect email or password", landingPage.getErrorMessage());

	}
	
	@Test
	public void productValidation()
	{
		String productName = "ZARA COAT 3";
		ProductCatalogue productCatalogue = landingPage.loginApplication("rahuldk.rahul39@gmail.com", "Rahul@1234");
		productCatalogue.addProductToCart(productName);
		CartPage cartPage = productCatalogue.goToCart();
		Boolean match = cartPage.verifyProductDisplay("ZARA COAT 3");
		Assert.assertTrue(match);
		//System.out.println(match);
	}

}
