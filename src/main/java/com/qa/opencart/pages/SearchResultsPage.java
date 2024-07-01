package com.qa.opencart.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.utils.ElementUtil;
import com.qa.opencart.utils.TimeUtil;

public class SearchResultsPage {
	
	WebDriver driver;
	ElementUtil eutil;

	public SearchResultsPage(WebDriver driver) {
		this.driver = driver;
		eutil=new ElementUtil(driver);
	}
	
	private By searchResults=By.cssSelector("div.product-thumb");
	
	public int searchCount() {
		List<WebElement> searchedList = eutil.waitForVisibilityAllElements(searchResults, TimeUtil.DEFAULT_TIME);
		int resultCount=searchedList.size();
		System.out.println("====search count is==="+resultCount);
		return resultCount;
	}
	
	public ProductInfoPage selectProduct(String productName) {
		eutil.doClick(By.linkText(productName),TimeUtil.DEFAULT_TIME);
		return new ProductInfoPage(driver);
	}

}
