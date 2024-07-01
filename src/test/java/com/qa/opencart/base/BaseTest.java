package com.qa.opencart.base;

import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.asserts.SoftAssert;

import com.qa.opencart.factory.DriverFactory;
import com.qa.opencart.pages.AccountsPage;
import com.qa.opencart.pages.LoginPage;
import com.qa.opencart.pages.ProductInfoPage;
import com.qa.opencart.pages.RegistrationPage;
import com.qa.opencart.pages.SearchResultsPage;
import com.qa.opencart.pages.ShoppingCartPage;

public class BaseTest {
	DriverFactory df;
	WebDriver driver;
	protected LoginPage loginpage;
	protected AccountsPage accpage;
	protected Properties prop;
	protected SearchResultsPage searchresultpage;
	protected ProductInfoPage prodinfopage;
	protected ShoppingCartPage shopcartpage;
	protected RegistrationPage regPage;
	protected SoftAssert sAssert;
	
	@Parameters({"browser"})
	@BeforeTest
	public void setup(@Optional String browsername) {
		df = new DriverFactory();
		prop = df.initProp();
		if(browsername!=null) {
			prop.setProperty("browser", browsername);
		}
		driver = df.initDriver(prop);
		loginpage = new LoginPage(driver);
		sAssert=new SoftAssert();

	}

	
	  @AfterTest public void tearDown() {
	  
	  driver.quit(); }
	 

}
