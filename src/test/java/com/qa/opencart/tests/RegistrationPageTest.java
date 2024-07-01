package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.errors.AppError;
import com.qa.opencart.utils.ExcelUtil;
import com.qa.opencart.utils.StringUtils;

public class RegistrationPageTest extends BaseTest{
	
	@BeforeClass
	public void regSetup() {
		
		regPage=loginpage.navigateToRegisterPage();
	}
	
	@DataProvider
	public Object[][] getUserRegisterationData() {
		
		return new Object[][] {
			
			{"megha","yadav","123456789","abc@1234","No"},
			{"auto","mation","123456789","abc@1234","No"}
			
		};
	}
	
	@DataProvider
	public Object[][] getUserRegisterationDataFromExcel() {
		
		return ExcelUtil.getTestData(AppConstants.RESGISTER_SHEET_NAME);
	}
	
	@Test(dataProvider = "getUserRegisterationDataFromExcel")
	public void registrationTest(String firstname,String lastname,String phno,String pwd,String subscribtion) {
		boolean isActUserCreated = regPage.createUser(firstname, lastname, StringUtils.getEmailId(), phno, pwd,subscribtion);
		Assert.assertTrue(isActUserCreated,AppError.USER_REGISTRATION_NOT_DONE);
	}

}
