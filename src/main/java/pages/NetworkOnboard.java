package pages;

import java.util.List;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
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
	
	@FindBy(xpath = "//span[contains(text(), 'payments')]/parent::div")
	public WebElement payments;
	
	@FindBy(xpath = "//span[contains(text(), 'payouts')]/parent::div")
	public WebElement payouts;
	
	@FindBy(xpath = "//span[contains(text(), 'Add')]/parent::button")
	public WebElement addPayments;
	
	@FindBy(xpath = "//div[contains(@aria-haspopup, 'listbox')]")
	public WebElement currency;
	
	@FindBy(xpath = "//li[contains(text(), 'USD')]")
	public WebElement usd;
	
	@FindBy(xpath = "//span[contains(text(), 'Next')]/parent::button")
	public WebElement next;
	
	@FindBy(xpath = "//span[contains(text(), 'Stripe')]")
	public WebElement stripe;
	
	@FindBy(css = "input[name='key']")
	public WebElement key;
	
	@FindBy(css = "input[name='secret']")
	public WebElement secret;
	
	@FindBy(css = "input[name='webhookSecret']")
	public WebElement webhookSecret;
	
	@FindBy(css = "input[name='webhook_secret']")
	public WebElement payoutWebhookSecret;
	
	@FindBy(css = "input[name='accountId']")
	public WebElement accountId;
	
	@FindBy(css = "input[name='account_id']")
	public WebElement payoutAccountId;
	
	@FindBy(css = "input[name='client_id']")
	public WebElement clientId;
	
	@FindBy(xpath = "//span[contains(text(), 'Link')]/parent::button")
	public WebElement link;
	
	@FindBy(xpath = "//span[contains(text(), 'Payouts')]/parent::button")
	public WebElement configurePayouts;
	
	@FindBy(xpath = "//p[contains(text(), 'Connected')]")
	public WebElement confirmPayments;
	
	@FindBy(xpath = "//p[contains(text(), 'Pending')]")
	public WebElement confirmPayouts;
	
	@FindBy(xpath = "//div[contains(text(), 'configured yet')]")
	public WebElement outsideNavBar; 
	
	@FindBy(xpath = "//p[contains(text(), 'Accepted')]")
	public WebElement kycAccepted;
	
	@FindBy(xpath = "//input[contains(@placeholder, 'Search')]")
	public WebElement search;
	
	@FindBy(xpath = "//span[contains(text(), 'networks')]/parent::div")
	public WebElement networks;
	
	@FindBy(xpath = "//button[contains(text(), 'Connect')]")
	public WebElement connect;
	
	@FindBy(xpath = "//span[contains(text(), 'Manage')]/parent::button")
	public WebElement manageVendor;
	
	@FindBy(xpath = "//span[contains(text(), 'Discover')]/parent::button")
	public WebElement discoverVendor;
	
	@FindBy(css = "button[title='Open']")
	public List<WebElement> openVendorList;
	
	public void clickConnect(String vendorName) {
		
		By connectVendor = By.xpath("//h2[contains(text(), '" + vendorName + "')]/parent::div/parent::div//span[contains(text(), 'Connect')]/parent::button");
		driver.findElement(connectVendor).click();
		
	}
	
	public void verifyConnectedVendor(String vendorName, String type) {
		
		By connectedVendor = By.xpath("//h2[contains(text(), '" + vendorName + "')]/parent::div/parent::div");
		
		if(type.equalsIgnoreCase("present")) {
			
			Assert.assertTrue(driver.findElement(connectedVendor).isDisplayed());
			
		} else if(type.equalsIgnoreCase("not present")) {
			
			Assert.assertFalse(driver.findElement(connectedVendor).isDisplayed());
			
		}
		
	}
	
	public void clickOnVendor(String vendorName, JavascriptExecutor je) {
		
		By connectedVendor = By.xpath("//li[contains(text(), '" + vendorName + "')]");
		je.executeScript("arguments[0].scrollIntoView();", driver.findElement(connectedVendor));
		driver.findElement(connectedVendor).click();
		
	}
	
	public void clickOnRemoveVendor(String vendorName) {
		
		By removeVendor = By.xpath("//h2[contains(text(), '" + vendorName + "')]/parent::div/following-sibling::div//*[name()='svg']");
		driver.findElement(removeVendor).click();
		
	}
	
}
