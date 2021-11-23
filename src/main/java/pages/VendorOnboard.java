package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class VendorOnboard extends BaseClass {

	WebDriver driver;

	public VendorOnboard(WebDriver driver) {

		this.driver = driver;

	}

	@FindBy(xpath = "//div[contains(text(), 'vendor')]")
	public WebElement vendor;

	@FindBy(xpath = "//button[text()='Next']")
	public WebElement next;

	@FindBy(css = "input[name='vendorName']")
	public WebElement vendorName;

	@FindBy(css = "input[name='textBar']")
	public WebElement sellingCategory;

	@FindBy(css = "input[name='websiteUrl']")
	public WebElement websiteUrl;

	@FindBy(xpath = "//span[text()='Vendor']")
	public WebElement vendorProfile;
	
	@FindBy(xpath = "//li[contains(text(), 'Refer')]")
	public WebElement refer;

}
