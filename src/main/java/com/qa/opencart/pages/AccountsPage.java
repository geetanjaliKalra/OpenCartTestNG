package com.qa.opencart.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;
import com.qa.opencart.utils.TimeUtil;

public class AccountsPage {
	private WebDriver driver;
	private ElementUtil eutil;

	public AccountsPage(WebDriver driver) {

		this.driver = driver;
		eutil= new ElementUtil(driver);
	}

	private By header = By.cssSelector("div#content h2");
	private By logoutlink = By.linkText("Logout");
	private By search = By.name("search");
	private By searchbtn = By.cssSelector("div#search button");

	public String getAccountPageTitle() {
		String title = eutil.waitForTitleToBe(AppConstants.ACCOUNT_PAGE_TITLE, TimeUtil.DEFAULT_MEDIUM_TIME);
		System.out.println("-----Title of page is------" + title);
		return title;
	}

	public String getAccountPageURL() {

		String url = eutil.waitForURLContains(AppConstants.ACCOUNT_PAGE_FRACTION_URL, TimeUtil.DEFAULT_TIME);
		System.out.println("-----URL of page is------" + url);
		return url;
	}

	public boolean isLogoutLinkExist() {

		return eutil.doIsDisplayed(logoutlink);
	}

	public boolean isSearchExist() {

		return eutil.doIsDisplayed(search);
	}

	public List<String> getHeadersList() {

		List<WebElement> actualHeaders = eutil.waitForVisibilityAllElements(header, TimeUtil.DEFAULT_TIME);
		List<String> actualHeaderList = new ArrayList<String>();
		for (WebElement e : actualHeaders) {
			String header = e.getText();
			actualHeaderList.add(header);
		}
		return actualHeaderList;
	}

	public SearchResultsPage doSearch(String searchKey) {
		System.out.println("-----Searching for---" + searchKey);

		if (isSearchExist()) {
		
			eutil.doSendKeys(search, searchKey, TimeUtil.DEFAULT_TIME);
			eutil.doClick(searchbtn);
			return new SearchResultsPage(driver);
		} else {
			System.out.println("--Search field id not present--");
			return null;
		}
	}
}
