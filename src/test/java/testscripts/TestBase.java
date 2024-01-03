package testscripts;

import java.io.IOException;

import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import base.PredefinedActions;
import pages.LoginPage;
import utility.PropertyFileOperations;

public class TestBase {
	
	@BeforeMethod
	public void setup() throws IOException {
		PropertyFileOperations fileOperaions = new PropertyFileOperations(".//src/main/resources/config/setting.properties");
		String url = fileOperaions.getValue("url");
		PredefinedActions.start(url);
		
		LoginPage loginPage = LoginPage.getObject();
		loginPage.login(fileOperaions.getValue("username"), fileOperaions.getValue("password"));
	} 
	
	@AfterMethod
	public void tearDown(ITestResult result) throws IOException {
		if(result.getStatus() == ITestResult.FAILURE) {
			PredefinedActions.takesScreenShot(result.getMethod().getMethodName()); 
		}
			
		
		PredefinedActions.closeBrowser();
	}

}
