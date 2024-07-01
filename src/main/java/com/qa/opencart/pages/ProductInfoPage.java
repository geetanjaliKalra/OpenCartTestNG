package com.qa.opencart.pages;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.utils.ElementUtil;
import com.qa.opencart.utils.TimeUtil;

public class ProductInfoPage  {
	
	WebDriver driver;
	ElementUtil eutil;
	
	public ProductInfoPage(WebDriver driver) {
		this.driver=driver;
		eutil=new ElementUtil(driver);
		
	}
	private By header= By.cssSelector("div#content h1");
	private By img= By.cssSelector("ul.thumbnails img");
	private By qtyField=By.id("input-quantity");
	private By addToCartBtn=By.id("button-cart");
	private By shoppingCartLink=By.linkText("shopping cart");
	private By productMetaData= By.xpath("(//div[@id='content']//ul[@class='list-unstyled'])[1]/li");
	private By productPriceData= By.xpath("(//div[@id='content']//ul[@class='list-unstyled'])[2]/li");
	private Map<String,String> productMap;
	
	
	public String getProductHeader() {
		String headerTitle = eutil.getElementText(header);
		System.out.println("==Header title is=== "+ headerTitle);
		return headerTitle;
	}
	
	public int getImgCount() {
		List<WebElement> imagesList = eutil.waitForVisibilityAllElements(img, TimeUtil.DEFAULT_TIME);
		int imgCount=imagesList.size();
		System.out.println("==Image count is==="+imgCount);
		return imgCount;
		
	}
	
	public ShoppingCartPage addToCart(int qty) {
		
		eutil.doSendKeys(qtyField,String.valueOf(qty));
		eutil.doClick(addToCartBtn,TimeUtil.DEFAULT_MEDIUM_TIME);
		eutil.doClick(shoppingCartLink,TimeUtil.DEFAULT_LONG_TIME);
		
		return new ShoppingCartPage(driver);
	}
	
	private void getProductMetaData() {
		
		List<WebElement> metaList = eutil.getElements(productMetaData);
		for(WebElement e:metaList) {
			String metaData=e.getText();
			String[] splitedMetaData = metaData.split(":");
			String metaKey = splitedMetaData[0].trim();
			String metaValue = splitedMetaData[1].trim();
			productMap.put(metaKey, metaValue);
		}
	} 
	
	private void getProductPriceData(){
		
		List<WebElement> priceList = eutil.getElements(productPriceData);
		String productPrice= priceList.get(0).getText();
		String exTaxPrice=priceList.get(1).getText().split(":")[1].trim();
		productMap.put("Product Price", productPrice);
		productMap.put("exTaxPrice", exTaxPrice);
				
	}
	
	public Map<String, String> getProductInfo() {
		//productMap=new HashMap<String,String>();
		//productMap=new LinkedHashMap<String,String>();  // maintains the insertion order
		productMap=new TreeMap<String,String>(); // maintains the alphabetical order or keys
		productMap.put("Product Name", getProductHeader());
		productMap.put("Imagescount", String.valueOf(getImgCount()));
		getProductMetaData();
		getProductPriceData();
	
		return productMap;
	}
}
