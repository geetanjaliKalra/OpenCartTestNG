package com.qa.opencart.factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.safari.SafariDriver;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.errors.AppError;
import com.qa.opencart.exceptions.BrowserException;

public class DriverFactory {
	WebDriver driver;
	Properties prop;
	OptionsManager om; 
	
	
	public static ThreadLocal<WebDriver> tldriver = new ThreadLocal<WebDriver>();
	public static String highlight;

	/**
	 * This method is use to initialize the driver based on the browser name
	 * 
	 * @param browser
	 * @return
	 */
	public WebDriver initDriver(Properties prop) {
		String browser = prop.getProperty("browser");
		om=new OptionsManager(prop);
		highlight=	prop.getProperty("highlight");
		switch (browser.toLowerCase().trim()) {

		case "chrome":
			// driver=new ChromeDriver();
			tldriver.set(new ChromeDriver(om.getChromeOptions()));
			break;
		case "firefox":
			// driver=new FirefoxDriver();
			tldriver.set(new FirefoxDriver(om.getFirefoxOptions()));
			break;
		case "edge":
			// driver=new EdgeDriver();
			tldriver.set(new EdgeDriver(om.getEdgeOptions()));
			break;
		case "safari":
			// driver=new SafariDriver();
			tldriver.set(new SafariDriver());
			break;
		default:
			System.out.println("plz pass the right browser name.. " + browser);
			throw new BrowserException(AppError.BROWSER_NOT_FOUND);

		}
		getDriver().manage().window().maximize();
		getDriver().manage().deleteAllCookies();
		getDriver().get(prop.getProperty("url"));
		return getDriver();
	}

	/**
	 * get the thread local copy of driver
	 * 
	 * @return
	 */
	public static WebDriver getDriver() {
		return tldriver.get();
	}

	public Properties initProp() {
		prop = new Properties();
		FileInputStream ip = null;
		// mvn clean install -Denv="qa"
		// mvn clean install

		String envName = System.getProperty("env");
		System.out.println("running test suite on env--->" + envName);

		if (envName == null) {
			System.out.println("env name is not given, hence running it on default config environment....");
			try {
				ip = new FileInputStream(AppConstants.CONFIG_FILE_PATH);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		} else {
			try {
				switch (envName.trim().toLowerCase()) {
				case "qa":
					ip = new FileInputStream(AppConstants.QA_FILE_PATH);
					System.out.println(prop.getProperty("username"));
					break;
				
				case "dev":
					ip = new FileInputStream(AppConstants.DEV_FILE_PATH);
				
				case "prod":
					ip = new FileInputStream(AppConstants.CONFIG_FILE_PATH);
					break;

				default:
					System.out.println("please pass the right env name.." + envName);
					//throw new FrameworkException("===WRONGENVPASSED===");
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}

		try {
			prop.load(ip);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return prop;

	}
	public static String getScreenshot(String methodName) {

		File src = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
		String path = System.getProperty("user.dir") + "/screenshots/" + methodName + "_" + System.currentTimeMillis()
				+ ".png";
		File destination = new File(path);

		try {
			FileHandler.copy(src, destination);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return path;
	}
}
