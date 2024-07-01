package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.errors.AppError;
import com.qa.opencart.listeners.AnnotationTransformer;
import com.qa.opencart.listeners.ExtentReportListener;
import com.qa.opencart.listeners.TestAllureListener;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Issue;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;


@Listeners({ExtentReportListener.class,TestAllureListener.class,AnnotationTransformer.class})

//retry logic doesnot works with annotations
public class LoginPageTest extends BaseTest {
	@Epic("Epic 100: Design open cart application work flow")
	@Story("Design login page for open cart app")
	@Issue("TC-01")
	@Owner("Geetanjali")
	@Description("Checking the login page title")
	@Severity(SeverityLevel.MINOR)
	@Feature("login page feature")
	@Test(priority=1)
	public void loginPageTitleTest() {
		String actTitle = loginpage.getPageTitle();
		Assert.assertEquals(actTitle,AppConstants.LOGIN_PAGE_TITLE,AppError.TITLE_NOT_FOUND);
	}
	@Description("Checking the login page URL")
	@Severity(SeverityLevel.MINOR)
	@Test(priority=2)
	public void loginPageURLTest() {
		String actURL = loginpage.getPageURL();
		Assert.assertTrue(actURL.contains(AppConstants.LOGIN_PAGE_FRACTION_URL),AppError.URL_NOT_FOUND);
	}
	@Description("Checking the presence of forget password link")
	@Severity(SeverityLevel.NORMAL)
	@Test(priority=3)
	public void forgetPwdLinkTest() {
		Assert.assertTrue(loginpage.isForgetPwdLinkPresent(),AppError.ELEMENT_NOT_FOUND);
	}
	@Severity(SeverityLevel.CRITICAL)
	@Description("Checking the login functionality")
	@Test(priority=4)
	public void loginTest() {
		accpage = loginpage.doLogin(prop.getProperty("username"),prop.getProperty("password"));
		String actTitle  = accpage.getAccountPageTitle();
		Assert.assertEquals(actTitle, AppConstants.ACCOUNT_PAGE_TITLE,AppError.TITLE_NOT_FOUND);
	}

}
