package testscripts;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import pages.DashboardPage;

public class DashboardTest extends TestBase{	
	
	
	@Test(priority=0)
	public void verifyWidgetCountAndText() throws IOException {
		
		DashboardPage dashboardPage = DashboardPage.getObject();
		
		System.out.println("VERIFY: Total widget count is 9");
		
		int totalWidgets = dashboardPage.getNumberOfWidgets();
		
		System.out.println("Total widgets:"+totalWidgets);
		
		Assert.assertEquals(totalWidgets, 10, "Total widgets were not as expected. Expected was 9 but actual widgets displayed were "+totalWidgets);
		
		List<String> expectedWidgetTextList = new ArrayList<String>(Arrays.asList("My Actions", "Quick Access","Time At Work", "Employees on Leave Today", "Latest News", "Latest Documents", "Current Year's Leave Taken by Department", "Buzz Latest Posts", "Headcount by Location", "Annual Basic Payment by Location"));
		
		System.out.println("Expected List: "+expectedWidgetTextList.size()+":"+expectedWidgetTextList);
		
		System.out.println("STEP: Get list of all widgets");
		
		List<String> actualWidgetTextList = dashboardPage.getAllWidgetTexts();
		
		System.out.println("Actual List:   "+actualWidgetTextList.size()+":"+ actualWidgetTextList);
		
		System.out.println("VERIFY: Text of all Widgets");
		 
		Assert.assertEquals(expectedWidgetTextList, actualWidgetTextList);
	}
	
	@Test(enabled=false)
	public void verfiyProfileAboutContentTest() {
		
		DashboardPage dashboardPage = DashboardPage.getObject();
		
		System.out.println("STEP: Mouse Hover on profile and click on settings.");
		List<String> actualProfileSettingOptions = dashboardPage.getProfileSettingOptions();
		List<String> expectedProfileSettingOptions = new ArrayList<String>(Arrays.asList("Change Password","About"));
		
		System.out.println("VERIFY: Available Profile Setting Options.");
		Assert.assertEquals(actualProfileSettingOptions, expectedProfileSettingOptions);
		
		System.out.println("STEP: Click on About Button from profile");
		dashboardPage.clickOnProfileAbout();
		
		Map<String, String> aboutTextMap = dashboardPage.getprofileAboutDetails();
		
		SoftAssert softAssert = new SoftAssert();
		
		System.out.println("VERIFY: Company Name");
		String companyName = "OrangeHRM (Pvt) Ltd(Bi Master Data - Demo)";
		softAssert.assertEquals(dashboardPage.getCompanyName(), companyName);
		
		System.out.println("VERIFY: Version");
		String version = "OrangeHRM 7.11-VAS.394d49f1";
		softAssert.assertEquals(dashboardPage.getVersion(), version);
		
		System.out.println("VERIFY: Employees");
		String employees = "173 (327 more allowed)";
		softAssert.assertEquals(dashboardPage.getEmployee(), employees);
		
		System.out.println("VERIFY: Users");
		String users = "58 (442 more allowed)";
		softAssert.assertEquals(dashboardPage.getUsers(), users);
		
		System.out.println("VERIFY: Renewal O");
		String renewal = "2023-12-31";
		softAssert.assertEquals(dashboardPage.getRenewalOn(), renewal);
		
		System.out.println("STEP: Click on Ok Button");
		dashboardPage.clickOnProfileAboutOkButton("Ok");
		
		softAssert.assertAll();	
		
	}
	
}
