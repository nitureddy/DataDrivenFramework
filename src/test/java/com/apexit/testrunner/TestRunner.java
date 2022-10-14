/**
 * 
 */
package com.apexit.testrunner;




import io.cucumber.testng.CucumberOptions;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
	
	@CucumberOptions(
			features = {"Features"}, 
			//features = {"DSD_3792_Account_Owner_Navigator.feature","BC_3808_Sales_Rep_Navigator.feature"}, 
	        glue = {"com.apexit.Stepdefinitions"}, 
	    	        // glue = {"TestNGScenarios"}, 
	         plugin = {"summary","json:target/cucumber-reports/cucumber.json",//to generate different kinds of reports
	               //  "pretty",
	        		// "html:target/cucumber-html-report", 
	                   "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"},
	       	     	      	tags="@LoginPayTM",
	        monochrome = true)//display output in readable format
	        //dryRun=true) //to check the feature files are mapped with step definition
	

	//For JUNIT Framework Use ---------->  @RunWith(Cucumber.class)
	//TESTNG Framework
public class TestRunner extends AbstractTestNGCucumberTests {
		
		
	}
	
	

