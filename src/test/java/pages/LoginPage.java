package pages;

import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
/**
 * 
 * @author suman
 *
 */
public class LoginPage {

	
	@FindBy(xpath = "(//input[@type='text'])[2]")
	private WebElement usernameTextField;
	
	@FindBy(xpath = "//input[@type='password']")
	private WebElement passwordTextField;
	
	@FindBy(xpath = "//button[@type='submit']")
	private WebElement submitButton;
	
	@FindBy(xpath = "//button[text()='Request OTP']")
	private WebElement requestOtp;
	
	@FindBy(linkText = "New to Flipkart? Create an account")
	private WebElement createAccount;
	
	@FindBy(xpath = "//button[text()='✕']")
	private WebElement cancelButton;
	
	@FindBy(xpath = "//div[@id='container']/div/div[1]/div[1]/div[2]/div[3]/div/div/div/div")
	private WebElement myAccount; 
	
	public LoginPage(WebDriver driver)
	{
		PageFactory.initElements(driver, this);
	}
	
	public void enterUserName(String username)
	{
		usernameTextField.sendKeys(username);
	}
	
	public void enterPassword(String password)
	{
		passwordTextField.sendKeys(password,Keys.ENTER);
	}
	
	public void clickOnSubmitBtn(WebDriver driver)
	{
		try
		{
		submitButton.click();
		}
		catch (ElementClickInterceptedException e) {
			JavascriptExecutor javaScriptExecutor = (JavascriptExecutor)driver;
			javaScriptExecutor.executeScript("arguments[0].click();","submitButton");
		}
	}
	
	public void clickOnRequestOtp()
	{
		requestOtp.click();
	}
	
	public void clickOnCreateAccount()
	{
		createAccount.click();
	}
	
	public void clickOnCancelBtn()
	{
		cancelButton.click();
	}
	
	public String getMyAccountText(WebDriver driver)
	{
		WebDriverWait wait=new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.textToBePresentInElement(myAccount, "My Account"));
		return myAccount.getText();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
