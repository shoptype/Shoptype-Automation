package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class NetworkOnboard {
	
	WebDriver driver;

	public NetworkOnboard(WebDriver driver) {

		this.driver = driver;

	}
	
	@FindBy(xpath="//div[contains(text(), 'Network Operator')]")
	public WebElement network;
	
	@FindBy(xpath = "//span[text()='Network']")
	public WebElement networkProfile;
	
	@FindBy(css="#vendorName")
	public WebElement networkName;
	
	@FindBy(css="#website")
	public WebElement networkUrl;

}
