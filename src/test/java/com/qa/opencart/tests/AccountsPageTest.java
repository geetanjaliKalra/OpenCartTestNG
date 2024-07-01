package com.qa.opencart.tests;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.errors.AppError;
import com.qa.opencart.pages.SearchResultsPage;

public class AccountsPageTest extends BaseTest {

	@BeforeClass
	public void accSetup() {
		accpage = loginpage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
	}

	@Test
	public void accPageTitleTest() {
		String actTitle = accpage.getAccountPageTitle();
		Assert.assertEquals(actTitle, AppConstants.ACCOUNT_PAGE_TITLE, AppError.TITLE_NOT_FOUND);
	}

	@Test
	public void accPageURLTest() {
		String actURL = accpage.getAccountPageURL();
		Assert.assertTrue(actURL.contains(AppConstants.ACCOUNT_PAGE_FRACTION_URL), AppError.URL_NOT_FOUND);
	}

	@Test
	public void accPageSearchBtnTest() {
		Assert.assertTrue(accpage.isSearchExist(), AppError.ELEMENT_NOT_FOUND);
	}

	@Test
	public void accPageHeadersTest() {
		List<String> actualheadersList = accpage.getHeadersList();
		Assert.assertTrue(actualheadersList.containsAll(AppConstants.ACCOUNT_HEADER_LIST),
				"---Headers not matched------");

	}

	@DataProvider
	public Object[][] getSearchData() {
		return new Object[][] { { "macbook", 3 }, { "iMac", 1 }, { "samsung", 2 }, { "Mobile", 0 } };

	}

	@Test(dataProvider = "getSearchData")
	public void searchTest(String searchKey, int expectedCount) {
		SearchResultsPage searchResult = accpage.doSearch(searchKey);
		Assert.assertEquals(searchResult.searchCount(), expectedCount, AppError.SEARCH_RESULTCOUNT_MISMATCHED);
	}
}
