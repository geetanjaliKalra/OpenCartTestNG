package com.qa.opencart.utils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.qa.opencart.exceptions.ElementException;
import com.qa.opencart.factory.DriverFactory;

public class ElementUtil {

	private WebDriver driver;
	private JavaScriptUtil jsUtil;

//  Constructor is created so that while creating object of this class same driver can be passed and session can be maintained
	public ElementUtil(WebDriver driver) {

		this.driver = driver;
		jsUtil = new JavaScriptUtil(driver);
	}

	// Customized method for send keys
	public void doSendKeys(By locator, String value) {
		nullCheck(value);
		doClear(locator);
		getElement(locator).sendKeys(value);
	}

	public void doClick(By locator) {

		getElement(locator).click();
	}

	public void doClear(By locator) {

		getElement(locator).clear();
	}

	private void highlightElement(WebElement element) {

		if (Boolean.parseBoolean(DriverFactory.highlight))
			jsUtil.flash(element);
	}

	//// Customized method for finding the element
	public WebElement getElement(By locator) {
		try {
			WebElement element = driver.findElement(locator);
			highlightElement(element);
			return element;
		} catch (NoSuchElementException e) {
			
			System.out.println("Element is not present with the locator "+locator);
			e.printStackTrace();
			return null;
		}
		

	}

	////// Customized method for checking if value is null or not
	public void nullCheck(String value) {
		if (value == null)
			throw new ElementException("VALUE IS NULL" + value);
	}

	// Customized method for finding the elements
	public List<WebElement> getElements(By locator) {
		return driver.findElements(locator);

	}

	////// Customized method for finding how many elements are found
	public int getElementsSize(By locator) {
		return getElements(locator).size();
	}

	////// Customized method for finding the text of the element
	public List<String> getElementsText(By locator) {
		List<WebElement> elements = getElements(locator);
		List<String> elementText = new ArrayList<String>();
		for (WebElement e : elements) {
			String text = e.getText();
			if (text != null && text.length() != 0) {
				elementText.add(text);

			}

		}
		return elementText;
	}

	public String getElementText(By locator) {
		return getElement(locator).getText();
	}

	public boolean doIsDisplayed(By locator) {

		try {
			// System.out.println("Element with locator "+locator+" is displayed");
			return getElement(locator).isDisplayed();
		} catch (NoSuchElementException e) {
			System.out.println("Element with locator  " + locator + " is not displayed");
			return false;

		}
	}

	public boolean isElementDisplayed(By locator) {
		int elementCount = getElements(locator).size();
		System.out.println(elementCount);
		if (elementCount == 1) {

			return true;
		} else {
			System.out.println("Multiple or 0 element is displayed");
		}
		return false;
	}

	public boolean isElementDisplayed(By locator, int expectedCount) {
		int actCount = getElements(locator).size();
		if (actCount == expectedCount) {
			System.out.println("Element is displayed " + locator + " with count " + expectedCount);
			return true;
		} else {
			System.out.println("Multiple or 0 element is displayed");
		}
		return false;
	}
////Customized method for finding the elements that have text

	public int countLinksWithText(By Locator) {
		int count = 0;
		List<WebElement> link = getElements(Locator);
		for (WebElement e : link) {
			String text = e.getText();
			if (text != null && text.length() != 0) {
				count++;
			}
		}
		System.out.println("searched locator with text are ");
		return count;
	}

////Customized method for finding the elements that have don't have text
	public int countLinksWithoutText(By Locator) {
		int count = 0;
		List<WebElement> link = getElements(Locator);
		for (WebElement e : link) {
			String text = e.getText();
			if (text == null || text.length() == 0) {
				count++;
			}
		}
		System.out.println("searched locator without text are ");
		return count;
	}

	// custom method to find attribute of the searched elements
	public List<String> getAttributeList(By Locator, String attribute) {
		List<WebElement> elements = getElements(Locator);
		List<String> attVal = new ArrayList<String>();
		for (WebElement e : elements) {
			String attrValue = e.getAttribute(attribute);
			if (attrValue != null && attrValue.length() != 0) {
				attVal.add(attrValue);
			}
		}
		return attVal;
	}

	public List<String> getAlDropdownOptions(By locator) {

		Select select = new Select(getElement(locator));
		List<WebElement> options = select.getOptions();
		List<String> dropdownOptions = new ArrayList<String>();
		for (WebElement e : options) {
			String option = e.getText();
			dropdownOptions.add(option);
		}

		return dropdownOptions;
	}

	// Finding count of the options in the dropdown

	public int getDropdownOptionsCount(By locator) {

		Select select = new Select(getElement(locator));
		return (select.getOptions().size());
	}

	// Select element from drpdown

	public void doSelectByIndex(By locator, int index) {

		Select select = new Select(getElement(locator));
		select.selectByIndex(index);
	}

	public void doSelectByVisibleText(By locator, String text) {

		Select select = new Select(getElement(locator));
		select.selectByVisibleText(text);
	}

	public void doSelectByValue(By locator, String text) {

		Select select = new Select(getElement(locator));
		select.selectByVisibleText(text);
	}

	// Searching from the search list

	public void search(By searchfield, String Value, By searchList, String searchValue) {
		doSendKeys(searchfield, Value);
		List<WebElement> elements = getElements(searchList);
		for (WebElement e : elements) {
			String text = e.getText();
			System.out.println(text);
			if (text.equals(searchValue)) {
				e.click();
				break;
			}

		}

	}

	/*
	 * Actions utilities
	 */

	public Actions getActionObj() {
		Actions act = new Actions(driver);
		return act;
	}

	public void hanleParentSubMenu(By parentMenuLocator, By childMenuLocator) throws InterruptedException {

		getActionObj().moveToElement(getElement(parentMenuLocator)).perform();
		Thread.sleep(2000);
		doClick(childMenuLocator);

	}

	public void doDragDrop(By sourceLocator, By targetLocator) {

		getActionObj().clickAndHold(getElement(sourceLocator)).moveToElement(getElement(targetLocator)).release()
				.build().perform();

		// direct method dragAndDrop can also be used,below is just for reference no
		// need to referesh if used alone
		driver.navigate().refresh();
		getActionObj().dragAndDrop(getElement(targetLocator), getElement(sourceLocator)).perform();

	}

	public void doActionsSendKeys(By locator, String value) {

		getActionObj().sendKeys(getElement(locator), value).perform();
	}

	public void doActionsClick(By locator) {

		getActionObj().click(getElement(locator)).perform();
	}

	/**
	 * this method is used to enter in text field with pause time
	 * 
	 * @param locator
	 * @param value
	 * @param pauseTime
	 */
	public void doSendKeysWithPause(By locator, String value, long pauseTime) {

		char ch[] = value.toCharArray();
		for (char c : ch) {
			getActionObj().sendKeys(getElement(locator), String.valueOf(c)).pause(pauseTime).perform();
		}

	}

	/**
	 * this method is used to enter in text field with default pause time which is
	 * 200ms
	 * 
	 * @param locator
	 * @param value
	 */

	public void doSendKeysWithPause(By locator, String value) {

		char ch[] = value.toCharArray();
		for (char c : ch) {
			getActionObj().sendKeys(getElement(locator), String.valueOf(c)).pause(200).perform();
		}

	}

	/**
	 * this method is used select the sub menus where main menu is clicked first
	 ** 
	 * @param level1
	 * @param level2
	 * @param level3
	 * @param level4
	 * @throws InterruptedException
	 */
	public void level4MenuHandlingUsingClick(By level1, By level2, By level3, By level4) throws InterruptedException {

		getElement(level1).click();
		Thread.sleep(2000);
		getActionObj().moveToElement(getElement(level2)).perform();
		Thread.sleep(2000);
		getActionObj().moveToElement(getElement(level3)).perform();
		Thread.sleep(2000);
		getElement(level4).click();

	}

	/**
	 * this method is used select the sub menus where main menu is mouse hovered
	 * first
	 * 
	 * @param level1
	 * @param level2
	 * @param level3
	 * @param level4
	 * @throws InterruptedException
	 */

	public void level4MenuHandlingUsingHover(By level1, By level2, By level3, By level4) throws InterruptedException {

		getActionObj().moveToElement(getElement(level1)).perform();
		Thread.sleep(2000);
		getActionObj().moveToElement(getElement(level2)).perform();
		Thread.sleep(2000);
		getActionObj().moveToElement(getElement(level3)).perform();
		Thread.sleep(2000);
		getElement(level4).click();

	}

	/**
	 * *********************************Waits*******************
	 */

	/**
	 * An expectation for checking that an element is present on the DOM of a page.
	 * This does not necessarily mean that the element is visible.
	 * 
	 * @param locator
	 * @param timeout
	 * @return
	 */

	public WebElement waitForElementPresent(By locator, int timeout) {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
		highlightElement(element);
		return element;
	}

	/**
	 * visibilityOfElementLocated An expectation for checking that an element is
	 * present on the DOM of a page and visible. Visibility means that the element
	 * is not only displayed but also has a height and width that isgreater than 0.
	 * 
	 * @param locator
	 * @param timeout
	 * @return
	 */
	public WebElement waitForElementVisible(By locator, int timeout) {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		highlightElement(element);
		return element;
	}

	public WebElement waitForElementVisible(By locator, int timeOut, int intervalTime) {

		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(timeOut))
				.pollingEvery(Duration.ofSeconds(intervalTime)).ignoring(NoSuchElementException.class)
				.withMessage("===element is not found===");

		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));

	}

	public void doClick(By locator, int timeout) {

		waitForElementVisible(locator, timeout).click();
	}

	public void doSendKeys(By locator, String value, int timeout) {
		nullCheck(value);
		doClear(locator);
		waitForElementVisible(locator, timeout).sendKeys(value);
	}

	/**
	 * An expectation for checking an element is visible and enabled such that you
	 * can click it.
	 * 
	 * @param locator
	 * @param timeout
	 */
	public void clickWhenReady(By locator, int timeout) {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
	}

	/**
	 * An expectation for checking that the title contains a case-sensitive
	 * substring
	 * 
	 * @param titleFraction
	 * @param timeOut
	 * @return
	 */
	public String waitForTitleContains(String titleFraction, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));

		try {
			if (wait.until(ExpectedConditions.titleContains(titleFraction))) {
				return driver.getTitle();
			}
		} catch (TimeoutException e) {
			System.out.println("title not found");
		}
		return driver.getTitle();
	}

	/**
	 * title the expected title, which must be an exact match
	 * 
	 * @param titleVal
	 * @param timeOut
	 * @return
	 */
	public String waitForTitleToBe(String titleVal, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));

		try {
			if (wait.until(ExpectedConditions.titleIs(titleVal))) {
				return driver.getTitle();
			}
		} catch (TimeoutException e) {
			System.out.println("title not found");
		}
		return driver.getTitle();
	}

	public String waitForURLContains(String urlFraction, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));

		try {
			if (wait.until(ExpectedConditions.urlContains(urlFraction))) {
				return driver.getCurrentUrl();
			}
		} catch (TimeoutException e) {
			System.out.println("URL not found");
		}
		return driver.getCurrentUrl();
	}

	/**
	 * It waits for alert and driver itself switches to it
	 * 
	 * @param timeout
	 * @return
	 */
	public Alert waitForJSAlert(int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		return wait.until(ExpectedConditions.alertIsPresent());
	}

	public Alert waitForJSAlert(int timeOut, int intervalTime) {

		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(timeOut))
				.pollingEvery(Duration.ofSeconds(intervalTime)).ignoring(NoAlertPresentException.class)
				.withMessage("===alert is not found===");
		return wait.until(ExpectedConditions.alertIsPresent());
	}

	public String getAlertText(int timeout) {
		Alert alert = waitForJSAlert(5);
		String text = alert.getText();
		alert.accept();
		return text;
	}

	public void acceptAlert(int timeout) {
		Alert alert = waitForJSAlert(5);
		alert.accept();
	}

	public void dismissAlert(int timeout) {
		Alert alert = waitForJSAlert(5);
		alert.dismiss();
	}

	public void sendKeysOnAlert(int timeout, String value) {
		Alert alert = waitForJSAlert(5);
		alert.sendKeys(value);
		alert.accept();
	}

	/******* Wait for Frame ********/
	/**
	 * An expectation for checking whether the given frame is available to switch
	 * to.
	 * 
	 * If the frame is available it switches the given driver to the specified
	 * frame.
	 * 
	 * @param framelocator - passed as by locator
	 * @param timeout
	 */

	public void waitForFrame(By framelocator, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(framelocator));
	}

	public void waitForFrameByLocator(By frameLocator, int timeOut, int intervalTime) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(timeOut))
				.pollingEvery(Duration.ofSeconds(intervalTime)).ignoring(NoSuchFrameException.class)
				.withMessage("===frame is not found===");

		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameLocator));

	}

	/**
	 * Wait for page is loaded or not using java script
	 * 
	 * @param timeOut
	 */
	public void isPageLoaded(int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		String flag = wait.until(ExpectedConditions.jsReturnsValue("return document.readyState === 'complete'"))
				.toString();// "true"

		if (Boolean.parseBoolean(flag)) {
			System.out.println("page is completely loaded");
		} else {
			throw new RuntimeException("page is not loaded");
		}
	}

	/**
	 * 
	 * @param frameIndex - passed as frame index 1,2,3
	 * @param timeout
	 */
	public void waitForFrame(int frameIndex, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameIndex));
	}

	/**
	 * 
	 * @param frameIdOrName passed as String frame id or frame name.
	 * @param timeout
	 */
	public void waitForFrame(String frameIdOrName, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameIdOrName));
	}

	/**
	 * 
	 * @param frame   - passed as WebElement
	 * @param timeout
	 */
	public void waitForFrame(WebElement frame, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frame));
	}

	/**
	 * An expectation for checking that there is at least one element present on a
	 * web page
	 * 
	 * @param locator
	 * @param timeout
	 * @return
	 */
	public List<WebElement> waitForPresenceeAllElements(By locator, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
	}

	/**
	 * An expectation for checking that all elements present on the web page that
	 * match the locatorare visible. Visibility means that the elements are not only
	 * displayed but also have a heightand width that is greater than 0.
	 * 
	 * @param locator
	 * @param timeout
	 * @return
	 */
	public List<WebElement> waitForVisibilityAllElements(By locator, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		try {
			return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
		} catch (TimeoutException e) {
			return List.of();
		}
	}
}