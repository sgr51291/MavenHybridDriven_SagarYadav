package testscripts;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import base.PredefinedActions;
import constants.ConstantValue;
import pages.LoginPage;
import utility.ExcelOperations;

public class LoginTest {
	
	@Test(dataProvider = "LoginDataProvider")
	public void loginTest(String url, String username, String password, boolean isLoginSuccessfull) {
		
		System.out.println("STEP: Launch chrome browser and hit url.");
		
		PredefinedActions.start(url);
		
		System.out.println("STEP: Enter Login Credentials.");
		
		LoginPage loginPage  = LoginPage.getObject();	
		loginPage.login(username, password);
		
		if(isLoginSuccessfull) {
				System.out.println("VERIFY: Home Page is loaded");
				String expectedTitle = "Employee Management";
				String actualTitle = loginPage.getPageTitle();
				Assert.assertEquals(actualTitle, expectedTitle,
					"Expected title was '" + expectedTitle + "' but actual title was '" + actualTitle + "'");
		}else {
				System.out.println("VERIFY: Home Page is loaded");
				String expectedTitle = "OrangeHRM";
				String actualTitle = loginPage.getPageTitle();
			
				Assert.assertEquals(actualTitle, expectedTitle, "Expected title was '"+expectedTitle+"' but actual title was '"+actualTitle+"'");
			
				System.out.println("VERIFY: Retry login page is loaded.");
				String expectedUrlEndsWith = "retryLogin";
				String actualUrl = loginPage.getPageUrl();
				Assert.assertTrue(actualUrl.endsWith("retryLogin"), expectedUrlEndsWith);
		}	
		
	}
	
	@AfterMethod
	public void tearDown() {		
		PredefinedActions.closeBrowser();
	}
	
	@DataProvider(name="LoginDataProvider")
	public Object[][] getLoginData() throws IOException{
		Object[][] data;
		try {
			data = ExcelOperations.readExcelData(ConstantValue.LOGINDATA, "Data");
		} catch (IOException e) {
			data = ExcelOperations.readExcelData(".//testdata1//LoginData.xlsx", "Data");
		}		
		return data;
		
	}
	
	@DataProvider(name="loginDataProvider1")
	public Object[] [] getLoginData1(){
		Object[] [] data = new Object[2] [4];
		data[0] [0] = "https://syadav-trials711.orangehrmlive.com";
		data[0] [1] = "admin";
		data[0] [2] = "PyVDa@41nT";
		data[0] [3] = true;
		
		data[1] [0] = "https://syadav-trials711.orangehrmlive.com";
		data[1] [1] = "admin";
		data[1] [2] = "PyVDa@41nT12";
		data[1] [3] = false;
		
		
		return data;
		
	}
	
	@DataProvider(name="signUpData")
	public Object[] [] getsignUpData(){
		return null;
		
	}
	
	@Test(enabled=false)
	public void logintest_negative() {
		
		System.out.println("STEP: Launch chrome browser and hit url.");
		
		PredefinedActions.start("https://syadav-trials711.orangehrmlive.com/");
		
		System.out.println("STEP: Enter Login Credentials.");
		
		LoginPage loginPage  = LoginPage.getObject(); 
		
		loginPage.login("admin", "PyVDa@41nT12");
		
		System.out.println("VERIFY: Home Page is loaded");
		String expectedTitle = "OrangeHRM";
		String actualTitle = loginPage.getPageTitle();
		
		Assert.assertEquals(actualTitle, expectedTitle, "Expected title was '"+expectedTitle+"' but actual title was '"+actualTitle+"'");
		
		System.out.println("VERIFY: Retry login page is loaded.");
		String expectedUrlEndsWith = "retryLogin";
		String actualUrl = loginPage.getPageUrl();
		Assert.assertTrue(actualUrl.endsWith("retryLogin"), expectedUrlEndsWith);
		
		System.out.println("STEP: Close the browser");
		PredefinedActions.closeBrowser();
		
	}

}
