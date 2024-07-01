package com.qa.opencart.tests;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.errors.AppError;

public class ProductInfoPageTest extends BaseTest {

	@BeforeClass
	public void productPageSetup() {
		accpage = loginpage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
	}

	@DataProvider
	public Object[][] getProductSearchData() {
		return new Object[][] { { "macbook", "MacBook Pro" }, { "iMac", "iMac" },
				{ "samsung", "Samsung Galaxy Tab 10.1" }, { "iPod", "iPod Classic" } };
	}

	@Test(dataProvider = "getProductSearchData", priority = 1)
	public void productHeaderTest(String searchKey, String productName) {
		searchresultpage = accpage.doSearch(searchKey);
		prodinfopage = searchresultpage.selectProduct(productName);
		String actProductHeader = prodinfopage.getProductHeader();
		Assert.assertEquals(actProductHeader, productName, AppError.HEADER_NOT_MATCHED);
	}

	@DataProvider
	public Object[][] getImgSearchData() {
		return new Object[][] { { "macbook", "MacBook Pro", 4 }, { "iMac", "iMac", 3 },
				{ "samsung", "Samsung Galaxy Tab 10.1", 7 }, { "iPod", "iPod Classic", 4 } };
	}

	@Test(dataProvider = "getImgSearchData", priority = 2)
	public void imgCountTest(String searchKey, String productName, int expectedImgCount) {
		searchresultpage = accpage.doSearch(searchKey);
		prodinfopage = searchresultpage.selectProduct(productName);
		int actImgCount = prodinfopage.getImgCount();

		Assert.assertEquals(actImgCount, expectedImgCount, AppError.IMG_COUNT_NOT_MATCHED);
	}

	@DataProvider
	public Object[][] addToCartData() {
		return new Object[][] { { "macbook", "MacBook Pro", 4 }, { "iMac", "iMac", 3 },
				{ "samsung", "Samsung Galaxy Tab 10.1", 7 }, { "iPod", "iPod Classic", 4 } };
	}

	@Test(dataProvider = "addToCartData", priority = 3)
	public void addToCartTest(String searchKey, String productName, int qty) {
		searchresultpage = accpage.doSearch(searchKey);
		prodinfopage = searchresultpage.selectProduct(productName);
		shopcartpage = prodinfopage.addToCart(qty);
		String actProductName = shopcartpage.getProductName(productName);
		Assert.assertEquals(actProductName, productName, AppError.PRODUCT_NOT_MATCHED);

	}
	
	@Test
	public void ProductInfoTest() {
	searchresultpage =	accpage.doSearch("macbook");
	prodinfopage=searchresultpage.selectProduct("MacBook Air");
	Map<String, String> productInfo = prodinfopage.getProductInfo();
	System.out.println("=========Complete Product Information========");
	System.out.println(productInfo);
	sAssert.assertEquals(productInfo.get("Brand"), "Apple1");
	sAssert.assertEquals(productInfo.get("Product Code"), "Product 1711");
	sAssert.assertEquals(productInfo.get("Product Price"), "$1,202.00");
	sAssert.assertAll();
	System.out.println("Test completed"); 
	// will only be executed in case the test is pass if test is fail this will not print
	//Soft assertion executes all statements till assert all even though few or all of them are getting failed
	//Hard assertion will terminate once single fail is encountered
	}
}
