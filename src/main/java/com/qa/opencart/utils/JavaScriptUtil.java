package com.qa.opencart.utils;

import org.openqa.selenium.Alert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class JavaScriptUtil {

	WebDriver driver;
	JavascriptExecutor js;

	public JavaScriptUtil(WebDriver driver) {
		this.driver = driver;
		js = (JavascriptExecutor) this.driver;
	}

	public String getPageTitleByJs() {
		return js.executeScript("return document.title").toString();
	}

	public String getURLeByJs() {
		return js.executeScript("return document.URL").toString();
	}

	public void generateJSAlert(String msg) {
		js.executeScript("alert('" + msg + "')");

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
		driver.switchTo().alert().accept();

	}

	public void generateJSConfirm(String msg) {
		js.executeScript("confirm('" + msg + "')");

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
		driver.switchTo().alert().accept();

	}

	public void generateJSPromt(String msg, String value) {
		js.executeScript("prompt('" + msg + "')");

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}

		Alert alert = driver.switchTo().alert();
		alert.sendKeys(value);
		alert.accept();

	}

	public void goBackWithJS() {
		js.executeScript("history.go(-1)");
	}

	public void goForwardWithJS() {
		js.executeScript("history.go(1)");
	}

	public void pageRefreshWithJS() {
		js.executeScript("history.go(0)");
	}

	public String getPageInnerText() {
		return js.executeScript("return document.documentElement.innerText;").toString();
	}

	public void scrollMiddlePageDown() {
		js.executeScript("window.scrollTo(0, document.body.scrollHeight/2);");
	}

	public void scrollPageDown() {
		js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
	}

	public void scrollPageDown(String height) {
		js.executeScript("window.scrollTo(0, '" + height + "');");
	}

	public void scrollPageUp() {
		js.executeScript("window.scrollTo(document.body.scrollHeight, 0);");
	}

	public void scrollTillElement(WebElement ele) {
		js.executeScript("arguments[0].scrollIntoView(true);", ele);
	}

	/**
	 * example: "document.body.style.zoom = '400.0%'"
	 * 
	 * @param zoomPercentage
	 */
	public void zoomFirefoxChromeEdgeSafari(String zoomPercentage) {
		String zoom = "document.body.style.zoom = '" + zoomPercentage + "%'";
		js.executeScript(zoom);
	}

	/**
	 * example: "document.body.style.MozTransform = 'scale(0.5)'; ";
	 * 
	 * @param zoomPercentage
	 */
	public void zoomFirefox(String zoomPercentage) {
		String zoom = "document.body.style.MozTransform = 'scale(" + zoomPercentage + ")'";
		js.executeScript(zoom);
	}

	public void drawBorder(WebElement element) {
		js.executeScript("arguments[0].style.border='5px solid blue'", element);
	}

	public void flash(WebElement element) {
		String bgcolor = element.getCssValue("backgroundColor");// grey-white
		for (int i = 0; i < 5; i++) {
			changeColor("rgb(0,200,0)", element);// green
			changeColor(bgcolor, element);// greywghite
		}
	}

	private void changeColor(String color, WebElement element) {
		JavascriptExecutor js = ((JavascriptExecutor) driver);
		js.executeScript("arguments[0].style.backgroundColor = '" + color + "'", element);
		// Green->gw->Green->gw

		try {
			Thread.sleep(20);
		} catch (InterruptedException e) {
		}
	}

	public void clickElementByJS(WebElement element) {
		js.executeScript("arguments[0].click();", element);
	}

	public void sendKeysUsingWithId(String id, String value) {
		js.executeScript("document.getElementById('" + id + "').value='" + value + "'");
		// document.getElementById('input-email').value ='tom@gmail.com'
	}
}
