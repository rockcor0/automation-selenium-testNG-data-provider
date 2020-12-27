package com.rockcor;

import org.testng.annotations.Test;
import org.testng.annotations.DataProvider;
import org.testng.annotations.BeforeClass;

import static org.testng.Assert.assertTrue;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;

public class DataProviderTestNG {

	private final static String URL = "http://automationpractice.com/index.php";
	private static final String EMAIL_USR_1 = "qs12345@gmail.com";
	private static final String PASSWORD_USR_1 = "qs123";
	private static final String EMAIL_USR_2 = "testng_qs@gmail.com";
	private static final String PASSWORD_USR_2 = "qs123";
	
	private static final By LOC_CONFIRMATION = By.xpath("//a[@class='logout']");
	private final static By LOC_SIGN_IN_LINK = By.xpath("//a[@class='login']");
	private final static By LOC_EMAIL = By.id("email");
	private final static By LOC_PASSWORD = By.id("passwd");
	private final static By LOC_BTN_SEND = By.id("SubmitLogin");

	private WebDriver driver;

	@Test(dataProvider = "authenticationData")
	public void login(String email, String password) throws InterruptedException {
		
		if(driver.findElement(LOC_SIGN_IN_LINK).isDisplayed()) {
			driver.findElement(LOC_SIGN_IN_LINK).click();
			
			WebDriverWait wait = new WebDriverWait(driver, 10);
			wait.until(ExpectedConditions.presenceOfElementLocated(LOC_EMAIL));
			
			driver.findElement(LOC_EMAIL).sendKeys(email);
			driver.findElement(LOC_PASSWORD).sendKeys(password);
			
			driver.findElement(LOC_BTN_SEND).click();
			
			wait.until(ExpectedConditions.presenceOfElementLocated(LOC_CONFIRMATION));
			
			Thread.sleep(2000);
			
			assertTrue(driver.findElement(LOC_CONFIRMATION).isDisplayed());
			
			driver.findElement(LOC_CONFIRMATION).click();
		} else {
			System.out.println("Login btn was not found");
		}
		
	}

	@DataProvider(name = "authenticationData")
	public Object[][] getData() {
		Object[][] data = new Object[2][2];

		data[0][0] = EMAIL_USR_1; data[0][1] = PASSWORD_USR_1;
		data[1][0] = EMAIL_USR_2; data[1][1] = PASSWORD_USR_2;

		return data;
	}

	@BeforeClass
	public void beforeClass() {
		driver = getWebDriver();
		driver.manage().window().maximize();
		driver.get(URL);
	}

	@AfterClass
	public void afterClass() {
		//driver.quit();
	}

	private WebDriver getWebDriver() {
		System.setProperty("webdriver.chrome.driver", "./src/test/resources/chromedriver/chromedriver");
		return new ChromeDriver();
	}

}
