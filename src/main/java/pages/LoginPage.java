package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import base.PredefinedActions;

public class LoginPage extends PredefinedActions{
	
	private static LoginPage loginPage;
	
	@FindBy(id="txtUsername")
	private WebElement userNameElement;
	
	@FindBy(id="txtPassword")
	private WebElement userPasswordElement;
	
	@FindBy(css="#txtUsername-error")
	private WebElement usernameErrorElement;
	
	@FindBy(css="#txtPassword-error")
	private WebElement passwordErrorElement;
	
	@FindBy(xpath="//button")
	private WebElement submitButton;
	
	@FindBy(css="div.organization-logo.shadow > img")
	private WebElement logoElement;
	
	private LoginPage() {
	}
	public static LoginPage getObject() {
		if(loginPage==null)
			loginPage = new LoginPage();
		
		PageFactory.initElements(driver, loginPage);
		return loginPage;
	}
	
	public void login(String userName, String password) {
		enterUserName(userName);
		enterPassword(password);
		clickOnLoginButton();
	}
	
	public void enterUserName(String userName) {
//		driver.findElement(By.id("txtUsername")).sendKeys(userName);
//		
//		WebElement element = getElement("id", "txtUsername", false);
//		setText(element, userName);
		
		setText(userNameElement, userName);
	}
	
	public void enterPassword(String password) {
		setText(userPasswordElement, password);
	}
	
	public void clickOnLoginButton() {
		//driver.findElement(By.xpath("//button")).click();
		clickOnElement(submitButton, false);
	}
	
	public boolean isUsernameErrorMsgDisplayed() {
		return isElementDisplayed(usernameErrorElement);
	}
	
	public boolean isPasswordErrorMsgDisplayed() {
		return isElementDisplayed(passwordErrorElement);
	}
	
	public boolean isLogoDisplayed() {
		return isElementDisplayed(logoElement);
	}
	
}
