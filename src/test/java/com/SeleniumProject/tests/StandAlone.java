package com.SeleniumProject.tests;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.SeleniumProject.TestComponents.BaseTest;
import com.SeleniumProject.pageObjects.CartPage;
import com.SeleniumProject.pageObjects.CheckOutPage;
import com.SeleniumProject.pageObjects.ConfirmationPage;
import com.SeleniumProject.pageObjects.OrderPage;
import com.SeleniumProject.pageObjects.ProductCatalogue;

public class StandAlone extends BaseTest {
	String productName = "ZARA COAT 3";
	@Test(dataProvider="getData",groups="Purchase")
	public void submitOrder(HashMap<String,String> input) throws IOException {
		// TODO Auto-generated method stub

		String countryName = "India";
		ProductCatalogue productCatalogue = landingPage.loginApplication(input.get("email"), input.get("password"));
		productCatalogue.addProductToCart(input.get("product"));
		CartPage cartPage = productCatalogue.goToCart();
		Boolean match = cartPage.verifyProductDisplay(input.get("product"));
		Assert.assertTrue(match);
		CheckOutPage checkoutpage = cartPage.goToCheckOutPage();
		checkoutpage.selectCountry(countryName);
		ConfirmationPage confirmationPage = checkoutpage.submitOrder();
		String message = confirmationPage.verifyConfirmationMessage();
		Assert.assertTrue(message.equalsIgnoreCase("Thankyou for the order."));

	}
	
	@Test(dependsOnMethods= {"submitOrder"})
	public void orderHistory()
	{
		ProductCatalogue productCatalogue = landingPage.loginApplication("rahulsinhaimuv@gmail.com", "Rahul@1234");
		OrderPage orderPage=productCatalogue.goToOrderPage();
		Boolean match=orderPage.verifyOrderDisplay(productName);
		Assert.assertTrue(match);
	}
	
	@DataProvider
	public Object[][] getData() throws IOException
	{
		//HashMap<String,String> map=new HashMap<String,String>();
		//map.put("email", "rahulsinhaimuv@gmail.com");
		//map.put("password", "Rahul@1234");
		//map.put("product", "ZARA COAT 3");
		
		//HashMap<String,String> map1=new HashMap<String,String>();
		//map1.put("email", "rahuldk.rahul39@gmail.com");
		//map1.put("password", "Rahul@1234");
		//map1.put("product","ADIDAS ORIGINAL");
		
		List<HashMap<String,String>> data= getJsonDataToMap(System.getProperty("user.dir")
				+"/src/test/java/com/SeleniumProject/TestData/PurchaseOrder.json");
		return new Object[][] {{data.get(0)},{data.get(1)}};
		
		
		//return new Object[][] {{map},{map1}};
		//return new Object[][] {{"rahulsinhaimuv@gmail.com","Rahul@1234","ZARA COAT 3"},{"rahuldk.rahul39@gmail.com","Rahul@1234","ADIDAS ORIGINAL"}};
	}
	

}
