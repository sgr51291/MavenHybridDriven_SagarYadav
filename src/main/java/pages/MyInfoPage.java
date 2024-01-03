package pages;

import org.openqa.selenium.support.PageFactory;

import base.PredefinedActions;

public class MyInfoPage extends PredefinedActions{
	
	private static MyInfoPage myInfoPage;
	
	private String menuPageMenu = "//a[contains(text(), '%s')]";
	
	private MyInfoPage(){
	}
	
	public static MyInfoPage getObject() {
		if(myInfoPage==null)
			myInfoPage=new MyInfoPage();

		PageFactory.initElements(driver, myInfoPage);
		return myInfoPage;
	}
	
	public enum MyInfoMenu{
		PROFILE("Profile"),
		PERSONALDETAILS("Personal Details"),
		JOB("Job"),
		SALARY("Salary"),
		CONTACTDETAILS("Contact Details"),
		MORE("More");
		
		public String value;
		
		private MyInfoMenu(String value) {
			this.value=value;
		}
		
	}
	
	public void gotoMenu(MyInfoMenu myInfoMenu) {
		String menuText = myInfoMenu.value;
		String locatorValue = String.format(menuPageMenu, menuText);
		clickOnElement(getElement("xpath", locatorValue, true),false);
//		
//		if(MyInfoMenu.JOB == myInfoMenu)
//			return new MyInfo_JobPage();
		
				
	}

}
