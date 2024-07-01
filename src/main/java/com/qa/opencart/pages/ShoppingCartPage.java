package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.utils.ElementUtil;

public class ShoppingCartPage {

	WebDriver driver;
	ElementUtil eutil;

	public ShoppingCartPage(WebDriver driver) {
		this.driver = driver;
		eutil = new ElementUtil(driver);
	}

	
	public String getProductName(String productName) {
		String elementsText = eutil.getElementText(By.linkText(productName));
		System.out.println("===product added to cart is==="+elementsText);
		return elementsText;
	}
}
