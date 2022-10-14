package com.apexit.Excel;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.poi.sl.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


public class LoginFaceBook {
	public LoginFaceBook() throws Exception{
		super();
	}
	ExcelApiFile file1=new ExcelApiFile("Keurig.xlsx");
	WebDriver driver;
//	ReadExcelData data=new ReadExcelData();

	@DataProvider(name="LC")
	public Object[][] LoginCredentials() throws Exception{
		//System.out.println("Login to facebook"+username);
		// Object[][] cred=data.createArrayData("C:\\Users\\sunitha.reddy\\OneDrive - Apex IT\\Documents\\DataDrivenFramework\\DataDriven.xlsx","New");
		 Object[][] cred=getExcelData("Keurig.xlsx","Navigators");
		 return cred;
	}
		
    @BeforeMethod
    public void setUp() {
    	
		driver=new ChromeDriver();
		
		System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
		System.out.println("Login to facebook");
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		
    }
	@Test(dataProvider="LC")
	public void TestLogin(String username,String password) {	
		try {
			driver.get("http://www.facebook.com");

			
			//driver.findElement(By.id("email")).clear();
			driver.findElement(By.id("email")).sendKeys(username);

			//driver.findElement(By.id("email")).clear();
			driver.findElement(By.id("pass")).sendKeys(password);
			driver.findElement(By.id("loginbutton")).submit();
			driver.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
		/**
		 * @param File Name
		 * @param Sheet Name
		 * @return
		 */
	 @AfterMethod
	    public void burnDown() {
	        driver.quit();
	    }
    // respective cells using a for-loop & returns it in the form of a string array
    public String[][] getExcelData(String fileName, String sheetName) throws IOException {
        String[][] data = null;
        try {
             
            FileInputStream fis = new FileInputStream(fileName);
            XSSFWorkbook workbook = new XSSFWorkbook(fis);
            XSSFSheet sheet = workbook.getSheet(sheetName);
            XSSFRow row = sheet.getRow(0);
            int noOfRows = sheet.getPhysicalNumberOfRows();
            int noOfCols = row.getLastCellNum();
            Cell cell;
            data = new String[noOfRows - 1][noOfCols];
 
            for (int i = 1; i < noOfRows; i++) {
                for (int j = 0; j < noOfCols; j++) {
                    row = sheet.getRow(i);
                    cell = row.getCell(j);
                    data[i - 1][j] = cell.getStringCellValue();
                }
            }
        } catch (Exception e) {
            System.out.println("The exception is: " + e.getMessage());
        }
        return data;
    }	
}

