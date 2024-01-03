package pages;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import base.PredefinedActions;

public class DashboardPage extends PredefinedActions{
	
	private static DashboardPage dashboardPage;
	
	@FindBy (xpath = "//div[contains(@class, 'oxd dashboard-widget-shell') and not(contains(@class, 'oxd dashboard-widget-shell ng-hide'))]//div[@class='widget-header']/span[2]")
	private List<WebElement> listOfWidgets;
	
	@FindBy(xpath="//div[contains(@class, 'oxd dashboard-widget-shell') and not(contains(@class, 'oxd dashboard-widget-shell ng-hide'))]//div[@class='widget-header']/span[text()='My Actions']")
	private WebElement myActionElement;
	
	@FindBy(css="div.picture>div.image-container")
	private WebElement profile;
	
	@FindBy (css = " a#aboutDisplayLink")
	private WebElement aboutProfileElement;
	
	@FindBy(xpath="//div[@class='sub-menu-container-php profile-context-menu-handler opened']//div[@class='sub-menu-item']")
	private List<WebElement> listOfProfileOptions;
	
	@FindBy(xpath="//a[@class='password-action profile-context-menu-handler']")
	private WebElement settingButton;
	
	@FindBy(css="div#companyInfo p")
	private List<WebElement> profileAboutDatails;
	
	@FindBy(css="a#heartbeatSubmitBtn")
	private WebElement aboutOkButton;
	
	@FindBy(css="div#companyInfo>div>div:nth-child(1)>p")
	private WebElement aboutContentFirstParagraph;
	
	private String aboutLocator = "//a[text()= '%s']";
	
	private String menuLocator = "//a[contains(text(), '%s')]";
	
	
	private DashboardPage() {
	}
	
	public static DashboardPage getObject() {
		if(dashboardPage==null)
			dashboardPage = new DashboardPage();

		PageFactory.initElements(driver, dashboardPage);
		return dashboardPage;
	}
	
	public int getNumberOfWidgets() {
		waitForVisibilityOfElement(myActionElement);
		return listOfWidgets.size();
		
	}
	
	public List<String> getAllWidgetTexts(){		
		return getListOfWebElementTexts(listOfWidgets);
	}
	
	public boolean isProfileDisplayed() {
		return isElementDisplayed(profile);
	}
	
	private void getProfileOptions() {
		mouseHoverOnElement(profile);
		clickOnElement(settingButton, false);
	}
	
	public List<String> getProfileSettingOptions(){
		getProfileOptions();
		return getListOfWebElementTexts(listOfProfileOptions);
		
	}
	
	public void clickOnProfileAbout() {
		if(!(isElementDisplayed(aboutProfileElement))) {	
			getProfileOptions();
		}
		clickOnElement(aboutProfileElement, false);
	}
	
	public void clickOnProfileAboutOkButton(String buttonText) {
		String locatorValue = String.format(aboutLocator, buttonText);
		WebElement element = getElement("xpath", locatorValue, false);
		clickOnElement(element, false);
	}
	
	public Map<String, String> getprofileAboutDetails(){
			boolean flag = waitForVisibilityOfElement(aboutContentFirstParagraph);			
			if(!flag)
				throw new NoSuchElementException("About content is not loading in given timeout");
			
			List<String> profileAboutDetails = getListOfWebElementTexts(profileAboutDatails);
			Map<String, String> profileAboutDetailsMap = new LinkedHashMap<String, String>();
			for(String str:  profileAboutDetails) {
				String[] array = str.split(":");
				profileAboutDetailsMap.put(array[0].trim(), array[1].trim());				
			}
			return profileAboutDetailsMap;
	}
	
	public String getCompanyName() {
		return getprofileAboutDetails().get("Company Name");
	}
	
	public String getVersion() {
		return getprofileAboutDetails().get("Version");
	}	
	public String getEmployee() {
		return getprofileAboutDetails().get("Employees");
	}
	
	public String getUsers() {
		return getprofileAboutDetails().get("Users");
	}
	
	public String getRenewalOn() {
		return getprofileAboutDetails().get("Renewal on");
	}
	
	public enum Menu{
		EMPLOYEELIST("Employee List "),
		MYINFO("My Info"),
		DIRECTORY("Directory");		
		
		public String menuItem;
		
		private Menu(String menuTitle){
			this.menuItem = menuTitle;
		}		
	}
	
	public enum Browser{
		CHROME,
		SAFARI,
		IE;
	}
	
	
	public void gotoMenu(Menu menuName) {
		String menuText = menuName.menuItem;
		String locatorValue = String.format(menuLocator, menuText);
		clickOnElement(getElement("xpath", locatorValue, true), false);
	}
}
