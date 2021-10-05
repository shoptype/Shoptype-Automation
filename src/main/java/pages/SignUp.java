package pages;

import java.util.HashMap;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SignUp {
	
	WebDriver driver;
	
	public SignUp(WebDriver driver) {
		
		this.driver = driver;

	}

	@FindBy(css="input[name='fname']")
	public WebElement firstName;
	
	@FindBy(css="input[name='lname']")
	public WebElement lastName;
	
	@FindBy(css="button[id='rfs-btn']")
	public WebElement countryCodeDropDown;
	
	@FindBy(css="input[name='rfs-q']")
	public WebElement countryCodeSearchBox;
	
	@FindBy(css="li[id='rfs-IN']")
	public WebElement countryCode;
	
	@FindBy(css="input[name='phone']")
	public WebElement phone;
	
	@FindBy(css="input[name='email']")
	public WebElement email;

	@FindBy(css="input[name='password']")
	public WebElement password;
	
	@FindBy(xpath="//button[text()='Sign Up']")
	public WebElement signUp;
	
	@FindBy(xpath="//div[contains(text(), 'Email already exist')]")
	public WebElement userExists;
	
	public void registerNewUser(WebDriverWait wait, HashMap<String, String> data) {
		
		firstName.sendKeys(data.get("first_name"));
		lastName.sendKeys(data.get("last_name"));
		countryCodeDropDown.click();
		wait.until(ExpectedConditions.visibilityOf(countryCodeSearchBox));
		countryCodeSearchBox.sendKeys("91");
		wait.until(ExpectedConditions.visibilityOf(countryCode));
		countryCode.click();
		phone.sendKeys(data.get("phone_number"));
		email.sendKeys(data.get("email"));
		password.sendKeys(data.get("password"));
		signUp.click();
		
	}

}