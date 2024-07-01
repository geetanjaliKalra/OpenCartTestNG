package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;
import com.qa.opencart.utils.TimeUtil;

public class RegistrationPage {

	WebDriver driver;
	ElementUtil eutil;

	public RegistrationPage(WebDriver driver) {
		this.driver = driver;
		eutil = new ElementUtil(driver);

	}

	private By firstname = By.xpath("//input[@id='input-firstname' and @name='firstname']");
	private By lastname = By.xpath("//input[@placeholder='Last Name']");
	private By email = By.xpath("//input[@name='email']");
	private By phone = By.xpath("//input[@id='input-telephone' or @name='telephone']");
	private By password = By.xpath("//input[@name='password' and @type='password']");
	private By confirmPassword = By.xpath("//input[@name='confirm' and @type='password']");
	private By yesnewsLetter = By.xpath("(//label[@class='radio-inline'])[position()=1]/input[@type='radio']");
	private By nonewsLetter = By.xpath("(//label[@class='radio-inline'])[position()=2]/input[@type='radio']");
	private By agree = By.xpath("//input[@value='1' and @name='agree']");
	private By continueBtn = By.xpath("//input[@value='Continue' and @type='submit']");
	private By successMsg = By.cssSelector("div#content h1");
	private By logout = By.linkText("Logout");
	private By register = By.linkText("Register");

	public boolean createUser(String firstname, String lastname, String email, String phone, String password,
			String subscribe) {

		eutil.doSendKeys(this.firstname, firstname, TimeUtil.DEFAULT_TIME);
		eutil.doSendKeys(this.lastname, lastname);
		eutil.doSendKeys(this.email, email);
		eutil.doSendKeys(this.phone, phone);
		eutil.doSendKeys(this.password, password);
		eutil.doSendKeys(this.confirmPassword, password);
		if (subscribe.equalsIgnoreCase("Yes")) {
			eutil.doClick(yesnewsLetter);
		} 
		eutil.doClick(agree,TimeUtil.DEFAULT_TIME);
		eutil.doClick(continueBtn);
		String successMesg = eutil.waitForElementVisible(successMsg, TimeUtil.DEFAULT_TIME).getText();
		System.out.println("email id is "+email);
		System.out.println(successMesg);
		if (successMesg.contains(AppConstants.USER_REGISTRATION_MSG)) {
			eutil.doClick(logout);
			eutil.doClick(register);
			return true;
		} else
			return false;

	}

}
