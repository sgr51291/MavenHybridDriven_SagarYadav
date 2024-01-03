package testscripts;

import org.testng.Assert;
import org.testng.annotations.Test;

import pages.DashboardPage;
import pages.DashboardPage.Menu;
import pages.MyInfoPage.MyInfoMenu;
import pages.MyInfo_SalaryPage;
import pages.MyInfoPage;

public class MyInfo_SalaryTest extends TestBase{
	
	@Test
	public void verifyCTC() {
		DashboardPage dashboardPage = DashboardPage.getObject();
		dashboardPage.gotoMenu(Menu.MYINFO);
		MyInfoPage myInfoPage = MyInfoPage.getObject();
		myInfoPage.gotoMenu(MyInfoMenu.SALARY);
		MyInfo_SalaryPage salaryPage = MyInfo_SalaryPage.getObject();
		
		System.out.println("VERIFY: CTC is non-zero");
		String getCTC = salaryPage.getCostToCompany();
		Assert.assertTrue(getCTC.startsWith("$"), "Actual CTC was "+getCTC);
		getCTC=getCTC.replace("$", "").replace(",", "");
		double d = Double.parseDouble(getCTC);
		Assert.assertTrue(d>0, "Actual CTC was "+getCTC);
		System.out.println(getCTC);
	}

}
