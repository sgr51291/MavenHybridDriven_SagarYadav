package base;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.tools.FileObject;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import constants.ConstantValue;

//import reports.ExtentManager;

public class PredefinedActions {
	
	protected PredefinedActions	() {
		
	}	
	 protected static WebDriver driver;
	 static WebDriverWait wait;
	 private Actions actions = new Actions(driver);
	
	public static void start(String url) {
		
		String browser = System.getProperty("browserName");
		String env = System.getProperty("env");
		
		if (browser == null)
			browser = "chrome";

		if (env == null)
			env = "Qa";
		
		System.out.println("Browser Name : " + browser);
		System.out.println("Environment Name : " + env);
		
		switch (browser.toLowerCase()) {
		case "chrome":
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--disable-notifications");
			System.setProperty(ConstantValue.CHROMEDRIVERKEY, ConstantValue.CHROMEDRIVER);
			driver = new ChromeDriver();
			driver.manage().window().maximize();
			driver.get(url);
			wait = new WebDriverWait(driver, ConstantValue.EXPLICITWAITTIME);
		}
		
	}
	
	protected WebElement getElement(String locatorType, String locatorValue, boolean isWaitRequired) {
		WebElement element = null;
		WebDriverWait wait = new WebDriverWait(driver, 60);
		switch (locatorType.toLowerCase()) {
		case "id":
			if (isWaitRequired) {
				element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(locatorValue)));
			} else
				element = driver.findElement(By.id(locatorValue));
			break;
		case "xpath":
			if (isWaitRequired) {
				element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locatorValue)));
			} else
				element = driver.findElement(By.xpath(locatorValue));
			break;
		case "cssselector":
			if (isWaitRequired) {
				element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(locatorValue)));
			} else
				element = driver.findElement(By.id(locatorValue));
			break;
		case "name":
			if (isWaitRequired) {
				element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name(locatorValue)));
			} else
				element = driver.findElement(By.name(locatorValue));
			break;
		case "linktext":
			if (isWaitRequired) {
				element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText(locatorValue)));
			} else
				element = driver.findElement(By.linkText(locatorValue));
			break;
		case "partiallinktext":
			if (isWaitRequired) {
				element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.partialLinkText(locatorValue)));
			} else
				element = driver.findElement(By.partialLinkText(locatorValue));
			break;
		case "tagname":
			if (isWaitRequired) {
				element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName(locatorValue)));
			} else
				element = driver.findElement(By.tagName(locatorValue));
			break;
		case "classname":
			if (isWaitRequired) {				
				element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className(locatorValue)));
			} else
				element = driver.findElement(By.className(locatorValue));
			break;
		}

		return element;
	}	
	
	protected boolean waitForVisibilityOfElement(WebElement element) {
		try {
			wait.until(ExpectedConditions.visibilityOf(element));
			//ExtentManager.log("Waiting for element to be visible");
		} catch (Exception exception) {
			return false;
		}
		return true;
	}
	
	protected void setText(String locatorType, String locatorValue, boolean isWaitRequired, String text) {
		WebElement element = getElement(locatorType, locatorValue, isWaitRequired);
		if(element.isEnabled()) {
			element.sendKeys(text);
		}
	}
	
	protected String getElementText(WebElement element, boolean isWaitRequired) {
		
		if(isWaitRequired)
			waitForVisibilityOfElement(element);
		
		String value = element.getText();
		
		if(value.equals("")) {
			value = element.getAttribute("value");
		}
		return value;
	}
	
	protected void setText(WebElement element, String text) {
		if(!element.isDisplayed()) {
			scrollToElement(element);			
		}
		if(element.isEnabled()) {
			element.sendKeys(text);
		}
	}
	
	protected void clickOnElement(WebElement element, boolean isWaitRequiredBeforeClick) {
		scrollToElement(element);		
		if(isWaitRequiredBeforeClick) {
			wait.until(ExpectedConditions.elementToBeClickable(element));
			element.click();
		}else
			element.click();
	}
	
	protected boolean isElementDisplayed(WebElement element) {
		scrollToElement(element);
		return element.isDisplayed();
	}

	private void scrollToElement(WebElement element) {
		if(!element.isDisplayed()) {
			JavascriptExecutor js = (JavascriptExecutor)driver;
			js.executeScript("argument[0].scrollIntoView(true)", element);
		}
	}
	

	protected void mouseHoverOnElement(WebElement element) {
		actions.moveToElement(element).build().perform();
		
	}
	
	protected List<String> getListOfWebElementTexts(List<WebElement> list){
		
		List<String> listOfElementText = new ArrayList<String>();
		
		for(WebElement element: list) {
			listOfElementText.add(element.getText());
		}
		return listOfElementText;
		
	}
	
	public String getPageTitle() {
		return driver.getTitle();
	}
	
	public String getPageUrl() {
		return  driver.getCurrentUrl();
	}
	
	public static void takesScreenShot(String screenshotName) throws IOException {
		TakesScreenshot takesScreenShot = (TakesScreenshot)driver;
		File srcFile = takesScreenShot.getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(srcFile,new File(ConstantValue.SCREENSHOTPATH+screenshotName+ConstantValue.SCREENSHOTEXTENSION));
	}

	public static void closeBrowser() {
		driver.close();
	}

}
