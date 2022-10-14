package com.apexit.basepage;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DemoMethods {

	public static void main(String[] args) {
		// 
		//DemoMethods objmethod=new DemoMethods();
	
		//objmethod.currentDate();
		System.out.println(currentDate());
		
	}

	public static String currentDate() {
		DateFormat newDateFormat = new SimpleDateFormat("ddmmyyyydd");
		Date date = new Date();
		String today = newDateFormat.format(date);
		return today;
	}

	public static void clickElement(WebDriver driver,String Locator,int Timeout) {
	WebElement element=driver.findElement(By.xpath(Locator));
	WebDriverWait wait= new WebDriverWait(driver,Timeout);
	wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(Locator)));
	element.click();
	}
	
	public static void ElementSendKeys(WebDriver driver,String Locator,int Timeout,String value) {
		WebElement element=driver.findElement(By.xpath(Locator));
		WebDriverWait wait= new WebDriverWait(driver,Timeout);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(Locator)));
		element.sendKeys(value);;
		}
	
	
	public static void launchBrowser(WebDriver driver,String url,int Timeout) {
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(Timeout, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(Timeout, TimeUnit.SECONDS);
		driver.get(url);
	}
	
	
	
	
	     }
