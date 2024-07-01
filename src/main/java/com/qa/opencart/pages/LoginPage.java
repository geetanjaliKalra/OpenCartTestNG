package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;
import com.qa.opencart.utils.TimeUtil;

public class LoginPage {

	private WebDriver driver;
	private ElementUtil eutil;

	// 1.Page objects - using by locator

	private By email = By.id("input-email");
	private By pwd = By.id("input-password");
	private By loginBtn = By.xpath("//input[@value='Login']");
	private By forgetPwdLink = By.linkText("Forgotten Password");
	private By registerPage=By.linkText("Register");
	
	//constructor
	
	public LoginPage(WebDriver driver) {
		
		this.driver=driver;
		eutil=new ElementUtil(driver);
	}

	// 2.Page actions using methods

	public String getPageTitle() {
		String title=eutil.waitForTitleToBe(AppConstants.LOGIN_PAGE_TITLE, TimeUtil.DEFAULT_TIME);
		System.out.println("-----Title of page is------" + title);
		return title;
	}

	public String getPageURL() {

		String url=eutil.waitForURLContains(AppConstants.LOGIN_PAGE_FRACTION_URL, TimeUtil.DEFAULT_TIME);
		System.out.println("-----URL of page is------" + url);
		return url;
	}

	public boolean isForgetPwdLinkPresent() {
		return eutil.doIsDisplayed(forgetPwdLink);
	}
	
	public AccountsPage doLogin(String username,String pwd) {
		eutil.doSendKeys(email, username, TimeUtil.DEFAULT_MEDIUM_TIME);
		eutil.doSendKeys(this.pwd, pwd);
		eutil.doClick(loginBtn);
					
		return new AccountsPage(driver);
	}
	
	public RegistrationPage navigateToRegisterPage() {
		eutil.doClick(registerPage, TimeUtil.DEFAULT_TIME);
		return new RegistrationPage(driver);
	}

}
