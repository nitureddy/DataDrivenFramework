package com.apexit.Stepdefinitions;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import com.apexit.Excel.ExcelApiFile;
import com.apexit.basepage.DemoMethods;
import com.apexit.basepage.LocatorsPath;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class Login {

	public Login() throws Exception {
		super();
	}

	WebDriver driver = new ChromeDriver();
	ExcelApiFile file = new ExcelApiFile("DataDrivenFramework.xlsx");

	@Given("I naviate to application")
	public void i_naviate_to_application() {
		String url = file.getCellData("Login", "URL", 2);
		DemoMethods.launchBrowser(driver, url, LocatorsPath.Max_Time);
	}

	@When("i click on recharge")
	public void i_click_on_recharge() {
		DemoMethods.clickElement(driver, LocatorsPath.recharge, LocatorsPath.Max_Time);
	}

	@Then("I select postpaid")
	public void i_select_postpaid() {
	}

	@Then("I polulate Mobilenumber,Operator,Amount")
	public void i_polulate_mobilenumber_operator_amount() throws InterruptedException {
		DemoMethods.ElementSendKeys(driver, LocatorsPath.mobNumber, LocatorsPath.Max_Time, DemoMethods.currentDate());
		DemoMethods.clickElement(driver, LocatorsPath.operator, LocatorsPath.Max_Time);
		Thread.sleep(5000);
		List<WebElement> options = driver.findElements(By.xpath("//div[@class='_3xI1']//ul/child::li"));
		for (int i = 0; i <= options.size() - 1; i++) {
			WebElement listvalue = options.get(i);
			if (listvalue.getText().equals("Vi")) {
				listvalue.click();
				System.out.println("the value selectd is" + listvalue.getText());
			} else {
				System.out.println("the value selectd is not" + listvalue.getText());
			}		
		}
		DemoMethods.ElementSendKeys(driver, LocatorsPath.amount, LocatorsPath.Max_Time, file.getCellData("Login", "Amount",LocatorsPath.Max_Time));
	}

	

	@Then("Proceed to Recharge")
	public void proceed_to_recharge() {
		DemoMethods.clickElement(driver, LocatorsPath.proRecahrge, LocatorsPath.Max_Time);
		closebrowser();
	}

	public void closebrowser() {
		driver.quit();
	}
	}

