package com.qa.opencart.constants;

import java.util.Arrays;
import java.util.List;

public class AppConstants {
	
	private AppConstants() {}  //This is to restrict the user to create the object of this class.
	public static final String CONFIG_FILE_PATH="./src/test/resources/config/config.properties";
	public static final String QA_FILE_PATH="./src/test/resources/config/qa.properties";
	public static final String DEV_FILE_PATH="./src/test/resources/config/dev.properties";
	//public static final String CONFIG_FILE_PATH="./src/test/resources/config/config.properties";
	public static final String LOGIN_PAGE_TITLE="Account Login";
	public static final String ACCOUNT_PAGE_TITLE="My Account";
	public static final String LOGIN_PAGE_FRACTION_URL = "route=account/login";
	public static final String ACCOUNT_PAGE_FRACTION_URL = "route=account/account";
	public static final List<String> ACCOUNT_HEADER_LIST = Arrays.asList("My Account","My Orders","My Affiliate Account","Newsletter");
	public static final CharSequence USER_REGISTRATION_MSG = "Your Account Has Been Created!";
	public static final String RESGISTER_SHEET_NAME = "register";
	

}
