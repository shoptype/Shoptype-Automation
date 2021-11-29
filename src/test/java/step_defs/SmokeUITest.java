package step_defs;

import java.io.IOException;
import java.util.HashMap;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import api_requests.APIRequest;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.BaseClass;
import utilities.Utilities;

public class SmokeUITest extends BaseClass {
	
	private static final Logger logger = LogManager.getLogger(SmokeUITest.class);
	public static HashMap<String, String> networkData = null;
	public static HashMap<String, String> vendorData = null;
	public static HashMap<String, String> cosellerData = null;
	public static Select countryDropDown;
	public static String networkName;
	public static String vendorName;
	
	@When("User enters all the required details to signup as {string}")
	public void signup_new_user(String userType) throws IOException, InterruptedException {
		
		wait.until(ExpectedConditions.elementToBeClickable(signUp.signUp));
		signUp.signUp.click();
		logger.info("Clicked on Sign Up");
		
		wait.until(ExpectedConditions.elementToBeClickable(signUp.firstName));
		
		if (userType.equalsIgnoreCase("network")) {
			
			networkData = Utilities.createTestData("network");
			logger.info("New network created - " + networkData);
			signUp.registerNewUser(wait, networkData);
			logger.info("Entered user details");
			
			logger.info("Verifying user email");
			if(Utilities.verifyEmail(driver, wait, networkData.get("email"))) {
				
				logger.info("Verified user email");
				
			} else {
				
				logger.info("Could not verify user email");
				
			}
			
		} else if (userType.equalsIgnoreCase("vendor")) {
			
			vendorData = Utilities.createTestData("vendor");
			logger.info("New vendor created - " + vendorData);
			signUp.registerNewUser(wait, vendorData);
			logger.info("Entered user details");
			
			logger.info("Verifying user email");
			if(Utilities.verifyEmail(driver, wait, vendorData.get("email"))) {
				
				logger.info("Verified user email");
				
			} else {
				
				logger.info("Could not verify user email");
				
			}
			
		} else if (userType.equalsIgnoreCase("coseller")) {
			
			cosellerData = Utilities.createTestData("coseller");
			logger.info("New coseller created - " + cosellerData);
			signUp.registerNewUser(wait, cosellerData);
			logger.info("Entered user details");
			
			logger.info("Verifying user email");
			if(Utilities.verifyEmail(driver, wait, cosellerData.get("email"))) {
				
				logger.info("Verified user email");
				
			} else {
				
				logger.info("Could not verify user email");
				
			}
			
		}
				
	}
	
	@When("Enters the onboarding details for {string}")
	public void enter_onboard_details(String userType) throws InterruptedException {
		
		if(userType.equalsIgnoreCase("network")) {
			
			networkName = networkData.get("newtork_name").split("@")[0];
			networkOnboard.networkName.sendKeys(networkName);
			logger.info("Entered network name - " + networkData.get("newtork_name"));
			
			networkOnboard.networkUrl.sendKeys(networkData.get("newtork_url"));
			logger.info("Entered network url - " + networkData.get("newtork_url"));
			Thread.sleep(3000);
						
		} else if(userType.equalsIgnoreCase("vendor")) {
			
			vendorName = Utilities.getNewEmailId().split("@")[0];
			vendorOnboard.vendorName.sendKeys(vendorName);
			logger.info("Entered vendor name - " + Utilities.getNewEmailId().split("@")[0]);
			
			vendorOnboard.websiteUrl.sendKeys(vendorData.get("vendor_url"));
			logger.info("Entered vendor url - " + vendorData.get("vendor_url"));
			
			vendorOnboard.sellingCategory.sendKeys("Fl");
			wait.until(ExpectedConditions.elementToBeClickable(vendorOnboard.category));
			vendorOnboard.category.click();
			logger.info("Entered vendor category - Flower/Gifts");
			
		} else if(userType.equalsIgnoreCase("coseller")) {
			
			logger.info("No onboard details for coseller");
			
		}
		
		vendorOnboard.next.click();
		logger.info("Clicked on next");
		
	}
	
	@Given("User login using the {string} credentials")
	public void user_login_using_the_credentials(String userType) {
		
		logger.info(" ========== " + scenarioName + " ========== ");
	    		
		if(userType.equalsIgnoreCase("network")) {
			
			login.signInUser(wait, networkData);
			logger.info("Logged in network");
			
			networkOnboard.network.click();
			logger.info("Clicked on network");
			
		} else if(userType.contains("admin")) {
			
			login.email.sendKeys(prop.get("shoptype_admin_user_name").toString());
			logger.info("Entered email - " + prop.get("shoptype_admin_user_name").toString());
			
			login.password.sendKeys(prop.get("shoptype_admin_password").toString());
			logger.info("Entered password - " + prop.get("shoptype_admin_password").toString());
			
			login.signIn.click();
			logger.info("Clicked on login");
			
			networkOnboard.network.click();
			logger.info("Clicked on network");
			
		} else if(userType.equalsIgnoreCase("vendor")) {
			
			login.signInUser(wait, vendorData);
			logger.info("Logged in vendor");
			
			vendorOnboard.vendor.click();
			logger.info("Clicked on vendor");
			
		} else if(userType.equalsIgnoreCase("coseller")) {
			
			login.signInUser(wait, cosellerData);
			logger.info("Logged in coseller");
			
			cosellerOnboard.coseller.click();
			logger.info("Clicked on coseller");
			
		}
		
		vendorOnboard.next.click();
		logger.info("Clicked on next");
		
	}
	
	@When("The Adds payment and payout config for a network")
	public void the_adds_payment_and_payout_config_for_a_network() throws InterruptedException {
		
		wait.until(ExpectedConditions.elementToBeClickable(networkOnboard.payments));
		networkOnboard.payments.click();
		logger.info("Clicked on payments");
		networkOnboard.outsideNavBar.click();
		logger.info("Clicked outside nav bar");
		
		Thread.sleep(3000);
		wait.until(ExpectedConditions.elementToBeClickable(networkOnboard.addPayments));
		networkOnboard.addPayments.click();
		logger.info("Clicked on add payments");
		
		enterPaymentPayoutDetails("payments");
		
		Thread.sleep(3000);
		wait.until(ExpectedConditions.elementToBeClickable(networkOnboard.payouts));
		networkOnboard.payouts.click();
		logger.info("Clicked on payouts");
		networkOnboard.outsideNavBar.click();
		logger.info("Clicked outside nav bar");
		
		wait.until(ExpectedConditions.elementToBeClickable(networkOnboard.configurePayouts));
		networkOnboard.configurePayouts.click();
		logger.info("Clicked on configure payouts");
		
		enterPaymentPayoutDetails("payouts");
		
	}
	
	@Then("The configurations should be saved")
	public void the_configurations_should_be_saved() {
	    
		wait.until(ExpectedConditions.elementToBeClickable(networkOnboard.payments));
		networkOnboard.payments.click();
		logger.info("Clicked on payments");
		
		Assert.assertTrue(networkOnboard.confirmPayments.isDisplayed());
		logger.info("Confirmed payments config");
		
		wait.until(ExpectedConditions.elementToBeClickable(networkOnboard.payouts));
		networkOnboard.payouts.click();
		logger.info("Clicked on payouts");
		
		Assert.assertTrue(networkOnboard.confirmPayouts.isDisplayed());
		logger.info("Confirmed payout config");
		
	}
	
	@When("The admin completes the KYC for that network")
	public void the_admin_completes_the_kyc_for_that_network() throws InterruptedException {
		
		int refreshCount = 0;
	    
		wait.until(ExpectedConditions.elementToBeClickable(admin.approval));
		admin.approval.click();
		logger.info("Clicked on approval");
		Thread.sleep(5000);
		
		wait.until(ExpectedConditions.elementToBeClickable(admin.network));
		admin.network.click();
		logger.info("Clicked on network");
		
		admin.clickOnAccept(networkName);
		logger.info("Clicked on accept");
		
		wait.until(ExpectedConditions.elementToBeClickable(admin.confirm)).click();
		logger.info("Clicked on confirm");
		
		logger.info("Switching to Stripe KYC Page");
		wait.until(ExpectedConditions.visibilityOf(admin.country));
		countryDropDown = new Select(admin.country);
		countryDropDown.selectByValue("US");
		logger.info("Selected US as country");
		
		admin.individual.click();
		logger.info("Clicked on Individual Entity");
		
		admin.testPhoneNumber.click();
		logger.info("Clicked on use test phone number");
		
		je.executeScript("arguments[0].scrollIntoView();", admin.email);
		admin.email.sendKeys(networkData.get("email").toString());
		logger.info("Entered email - " + networkData.get("email").toString());
		
		admin.continueKyc.click();
		logger.info("Clicked on continue");
		
		wait.until(ExpectedConditions.visibilityOf(admin.testCode));
		admin.testCode.click();
		logger.info("Clicked on use test code");
		
		wait.until(ExpectedConditions.visibilityOf(admin.firstName));
		admin.firstName.sendKeys("Automation");
		logger.info("Entered First Name - Automation");
		
		admin.lastName.sendKeys("KYC");
		logger.info("Entered Last Name - KYC");
		
		admin.day.sendKeys("12");
		logger.info("Entered DOB Day - 12");
		
		admin.month.sendKeys("09");
		logger.info("Entered DOB Month - 09");
		
		admin.year.sendKeys("1990");
		logger.info("Entered DOB Year - 1990");
		
		je.executeScript("arguments[0].scrollIntoView();", admin.addressLine1);
		admin.addressLine1.sendKeys("4498 Woodford Pass");
		logger.info("Entered Address - 4498 Woodford Pass");
		
		admin.city.sendKeys("Roswell");
		logger.info("Entered City - Roswell");
		
		countryDropDown = new Select(admin.state);
		countryDropDown.selectByValue("GA");
		logger.info("Selected state - Georgia");
		
		je.executeScript("arguments[0].scrollIntoView();", admin.socialNumber);
		admin.postalCode.sendKeys("30075");
		logger.info("Entered postal code - 30075");
		
		admin.socialNumber.sendKeys("123456789");
		logger.info("Entered social security number - 123456789");
		
		admin.continueKyc.click();
		logger.info("Clicked on continue");
		
		wait.until(ExpectedConditions.visibilityOf(admin.industry)).click();
		logger.info("Clicked on industry drop down");
		
		admin.software.click();
		logger.info("Clicked on software");
		
		admin.businessUrl.sendKeys("https://www.google.com");
		logger.info("Entered url - https://www.google.com");
		
		admin.continueKyc.click();
		logger.info("Clicked on continue");
		
		wait.until(ExpectedConditions.visibilityOf(admin.testAccount)).click();
		logger.info("Clicked on use test account");
		
		wait.until(ExpectedConditions.visibilityOf(admin.update)).click();
		logger.info("Clicked on update");
		
		wait.until(ExpectedConditions.visibilityOf(admin.testDocument)).click();
		logger.info("Clicked on use test document");
		
		while(true) {
			
			if(refreshCount == 10) {
				
				logger.info("Refreshed page - " + refreshCount + " times");
				Assert.assertTrue("Failed to verify the KYC documents", false);
				break;
				
			}
			
			if(Utilities.isElementPresent(driver, wait, admin.verified)) {
				
				wait.until(ExpectedConditions.visibilityOf(admin.verifiedDocuments));
				je.executeScript("arguments[0].scrollIntoView();", admin.verifiedDocuments);
				Assert.assertTrue(admin.verifiedDocuments.isDisplayed());
				logger.info("KYC documents verified");
				break;
				
			} else {
				
				driver.navigate().refresh();
				Thread.sleep(3000);				
				refreshCount++;
				
			}
			
		}
		
		admin.done.get(1).click();
		logger.info("Clicked on done");	
		
		wait.until(ExpectedConditions.visibilityOf(admin.network));
		networkOnboard.networkProfile.click();
		logger.info("Clicked on admin profile");
		
		wait.until(ExpectedConditions.visibilityOf(admin.logout)).click();
		logger.info("Clicked on logout");
		
		logger.info("Logging to network account to verify KYC");
		wait.until(ExpectedConditions.visibilityOf(login.email));
		login.signInUser(wait, networkData);
		
		wait.until(ExpectedConditions.visibilityOf(networkOnboard.network)).click();
		logger.info("Clicked on network");
		
		wait.until(ExpectedConditions.visibilityOf(vendorOnboard.next)).click();
		logger.info("Clicked on next");
		
		Thread.sleep(3000);
		wait.until(ExpectedConditions.elementToBeClickable(networkOnboard.payouts));
		networkOnboard.payouts.click();
		logger.info("Clicked on payouts");
		Thread.sleep(5000);
		
	}
	
	@Then("The payout config should be approved for that network")
	public void the_payout_config_should_be_approved_for_that_network() {
	    
		wait.until(ExpectedConditions.visibilityOf(networkOnboard.kycAccepted));
		Assert.assertTrue(networkOnboard.kycAccepted.isDisplayed());
		logger.info("Payout approved by admin");
		
	}
	
	@When("The user imports product from shopify")
	public void the_user_imports_product_from_shopify() throws InterruptedException {
	    
		Thread.sleep(3000);
		wait.until(ExpectedConditions.visibilityOf(vendorOnboard.products));
		vendorOnboard.products.click();
		logger.info("Clicked on products");
		
		wait.until(ExpectedConditions.visibilityOf(vendorOnboard.importShopify)).click();
		logger.info("Clicked on import shopify");
		
		wait.until(ExpectedConditions.visibilityOf(vendorOnboard.storeName));
		vendorOnboard.storeName.sendKeys(prop.get("shopify_store_name").toString());
		logger.info("Entered store name - " + prop.get("shopify_store_name").toString());
		
		vendorOnboard.key.sendKeys(prop.get("shopify_key").toString());
		logger.info("Entered key - " + prop.get("shopify_key").toString());
		
		vendorOnboard.secret.sendKeys(prop.get("shopify_secret").toString());
		logger.info("Entered secret - " + prop.get("shopify_secret").toString());
		
		vendorOnboard.enableShoptypeCheckout.click();
		logger.info("Enabled shoptype checkout");
		
		vendorOnboard.isAdult.click();
		logger.info("Adult filter set to true");
		
		vendorOnboard.isAgeRestricited.click();
		logger.info("Age restricted filter set to true");
		
		vendorOnboard.link.click();
		logger.info("Clicked on link");
		
	}
	
	@Then("The user should be able to see the products imported from vendor account")
	public void the_user_should_be_able_to_see_the_products_imported_from_vendor_account() {
	    
		int refreshCount = 0;
		
		while(true) {
			
			if(refreshCount == 10) {
				
				Assert.assertTrue("Could not verify the KYC", false);
				
			}
			
			if(Utilities.isElementPresent(driver, wait, vendorOnboard.addProduct)) {
				
				Assert.assertTrue(vendorOnboard.view.size() == 10);
				Assert.assertTrue(vendorOnboard.publish.size() == 10);
				Assert.assertTrue(vendorOnboard.attribution.size() == 10);
				logger.info("Successfully linked shopify products");
				break;
				
			} else {
				
				driver.navigate().refresh();
				refreshCount++;
				
			}
			
		}
		
	}
		
	@When("The network sends the connection request to a vendor")
	public void the_network_sends_the_connection_request_to_a_vendor() throws InterruptedException {
	    
		wait.until(ExpectedConditions.elementToBeClickable(networkOnboard.networks));
		networkOnboard.networks.click();
		logger.info("Clicked on networks");
		Thread.sleep(5000);
		
		wait.until(ExpectedConditions.visibilityOf(networkOnboard.discoverVendor)).click();
		wait.until(ExpectedConditions.visibilityOf(networkOnboard.discoverVendor)).click();
		
		wait.until(ExpectedConditions.visibilityOf(networkOnboard.search));
		networkOnboard.search.sendKeys(vendorName);
		logger.info("Searched for vendor - " + vendorName);
		
		networkOnboard.clickConnect(vendorName);
		logger.info("Clicked on connect");
		
		networkOnboard.connect.click();
		logger.info("Connection request sent to vendor");
		
		wait.until(ExpectedConditions.visibilityOf(networkOnboard.manageVendor)).click();
		logger.info("Clicked on manager vendor");
		
	}
	
	@Then("The network should be able to see the connected vendor")
	public void the_network_should_be_able_to_see_the_connected_vendor() {
	    
		networkOnboard.verifyConnectedVendor(vendorName, "present");
		logger.info("Vendor connection verified");
		
	}
	
	@When("The network searches for the product of that vendor")
	public void the_network_searches_for_the_product_of_that_vendor() throws InterruptedException {
	    
		vendorOnboard.products.click();
		logger.info("Clicked on products");
		Thread.sleep(5000);
		
		wait.until(ExpectedConditions.elementToBeClickable(networkOnboard.openVendorList.get(0)));
		actions.moveToElement(networkOnboard.openVendorList.get(0)).click().perform();
		logger.info("Clicked on vendor drop down");
		
		Thread.sleep(5000);
		networkOnboard.clickOnVendor(vendorName, je);
		logger.info("Clicked on vendor - " + vendorName);
		
	}
	
	@Then("The network should be able to see the list of products for that vendor")
	public void the_network_should_be_able_to_see_the_list_of_products_for_that_vendor() {
	    
		Assert.assertTrue(vendorOnboard.view.size() >= 10);
		Assert.assertTrue(vendorOnboard.publish.size() >= 10);
		logger.info("Successfull verified proudct list from vendor account");
		
	}
	
	@When("The vendor accepts the connection request")
	public void the_vendor_accepts_the_connection_request() throws InterruptedException {
	    
		Thread.sleep(5000);
		wait.until(ExpectedConditions.visibilityOf(vendorOnboard.networks)).click();
		logger.info("Clicked on network");
		
		wait.until(ExpectedConditions.visibilityOf(vendorOnboard.accept)).click();
		logger.info("Clicked on accept");
		
		wait.until(ExpectedConditions.visibilityOf(admin.confirm)).click();
		logger.info("Clicked on confirm");
	
	}
	
	@Then("The network should be added to connection")
	public void the_network_should_be_added_to_connection() {
	    
		wait.until(ExpectedConditions.visibilityOf(vendorOnboard.withdraw));
		Assert.assertTrue(vendorOnboard.withdraw.isDisplayed());
		Assert.assertTrue(vendorOnboard.remove.isDisplayed());
		logger.info("Network connection accepted from vendor side");
		
	}
	
	@When("The user removes that network from connection")
	public void the_user_removes_that_network_from_connection() {
	    
		vendorOnboard.remove.click();
		logger.info("Clicked on remove");
		
		admin.confirm.click();
		logger.info("Clicked on confirm");
		
	}
	
	@Then("The network connection should be removed")
	public void the_network_connection_should_be_removed() {
	    
		Assert.assertFalse(Utilities.isElementPresent(driver, wait, vendorOnboard.acceptLocator));
		Assert.assertFalse(Utilities.isElementPresent(driver, wait, vendorOnboard.withdrawLocator));
		logger.info("Removed network from connection");
		
	}
	
	@When("The user removes that vendor from connection")
	public void the_user_removes_that_vendor_from_connection() throws InterruptedException {
	    
		wait.until(ExpectedConditions.visibilityOf(vendorOnboard.networks)).click();
		logger.info("Clicked on network");
		
		wait.until(ExpectedConditions.visibilityOf(networkOnboard.manageVendor)).click();
		wait.until(ExpectedConditions.visibilityOf(networkOnboard.manageVendor)).click();
		logger.info("Clicked on manage vendor");
		
		networkOnboard.clickOnRemoveVendor(vendorName);
		logger.info("Clicked on remove vendor");
		
	}
	
	@Then("The vendor connection should be removed")
	public void the_vendor_connection_should_be_removed() {
	    
		networkOnboard.verifyConnectedVendor(vendorName, "not present");
		logger.info("Vendor successfully removed from network");
		
	}
		
	@Given("All the scenarios are executed")
	public void all_the_scenarios_are_executed() {
	    
		logger.info(" ========== " + scenarioName + " ========== ");
		
	}
	
	@Then("Delete {string} account")
	public void delete_account(String userType) {
	    
		if(userType.equalsIgnoreCase("network")) {
			
			APIRequest.deleteUser(prop.getProperty("backend_beta_url"), networkUserId, networkAuthToken);
			logger.info("Deleted user id - " + networkUserId);
			
		} else if(userType.equalsIgnoreCase("vendor")) {
			
			APIRequest.deleteUser(prop.getProperty("backend_beta_url"), vendorUserId, vendorAuthToken);
			logger.info("Deleted user id - " + vendorUserId);
			
		} else if(userType.equalsIgnoreCase("coseller")) {
			
			APIRequest.deleteUser(prop.getProperty("backend_beta_url"), cosellerUserId, cosellerAuthToken);
			logger.info("Deleted user id - " + cosellerUserId);
			
		}
		
	}
	
	@When("The coseller searches for the vendor product")
	public void the_coseller_searches_for_the_vendor_product() throws InterruptedException {
	    
		Thread.sleep(3000);
		wait.until(ExpectedConditions.visibilityOf(cosellerOnboard.cosellerProfile)).click();
		logger.info("Clicked on coseller profile");
		
		wait.until(ExpectedConditions.visibilityOf(cosellerOnboard.myProfile)).click();
		logger.info("Clicked on my profile");
		
		je.executeScript("arguments[0].scrollIntoView();", cosellerOnboard.isAdult);
		cosellerOnboard.isAdult.click();
		logger.info("Clicked on show adult content");
		
		cosellerOnboard.isAgeRestricted.click();
		logger.info("Clicked on show age restricted content");
		
		cosellerOnboard.saveChanges.click();
		logger.info("Clicked on save changes");
		
		Thread.sleep(3000);
		wait.until(ExpectedConditions.visibilityOf(vendorOnboard.products)).click();
		logger.info("Clicked on products");
		
		Thread.sleep(5000);
		wait.until(ExpectedConditions.visibilityOf(networkOnboard.openVendorList.get(1)));
		actions.moveToElement(networkOnboard.openVendorList.get(1)).click().perform();
		logger.info("Clicked on vendor drop down");
		
		Thread.sleep(5000);
		networkOnboard.clickOnVendor(vendorName, je);
		logger.info("Clicked on vendor - " + vendorName);
		
	}
	
	@Then("The coseller should be able to see the list of products for that vendor")
	public void the_coseller_should_be_able_to_see_the_list_of_products_for_that_vendor() {
	    
		Assert.assertTrue(vendorOnboard.view.size() >= 10);
		Assert.assertTrue(vendorOnboard.publish.size() >= 10);
		logger.info("Successfull verified proudct list from coseller account for that vendor");
		
	}
	
	
	
	
	// ADD ALL THE STEP DEFINITION BEFORE THIS
	
	public static void enterPaymentPayoutDetails(String type) throws InterruptedException {
		
		wait.until(ExpectedConditions.elementToBeClickable(networkOnboard.currency));
		networkOnboard.currency.click();
		logger.info("Clicked on currency");
		
		networkOnboard.usd.click();
		logger.info("Clicked on usd");
		
		networkOnboard.next.click();
		logger.info("Clicked on next");
		
		if(type.equalsIgnoreCase("payments")) {
			
			Thread.sleep(3000);
			wait.until(ExpectedConditions.elementToBeClickable(networkOnboard.stripe));
			networkOnboard.stripe.click();
			logger.info("Clicked on stripe");
						
			networkOnboard.next.click();
			logger.info("Clicked on next");
			
			wait.until(ExpectedConditions.elementToBeClickable(networkOnboard.key));
			networkOnboard.key.sendKeys(prop.get("payment_key").toString());
			logger.info("Entered key - " + prop.get("payment_key").toString());
			
			wait.until(ExpectedConditions.elementToBeClickable(networkOnboard.secret));
			networkOnboard.secret.sendKeys(prop.get("payment_secret").toString());
			logger.info("Entered secret - " + prop.get("payment_secret").toString());
							
			wait.until(ExpectedConditions.elementToBeClickable(networkOnboard.webhookSecret));
			networkOnboard.webhookSecret.sendKeys(prop.get("payment_webhook_secret").toString());
			logger.info("Entered webhook secret - " + prop.get("payment_webhook_secret").toString());
			
			wait.until(ExpectedConditions.elementToBeClickable(networkOnboard.accountId));
			networkOnboard.accountId.sendKeys(prop.get("payment_accountId").toString());
			logger.info("Entered account id - " + prop.get("payment_accountId").toString());

					
		} else if(type.equalsIgnoreCase("payouts")) {
			
			Thread.sleep(3000);
			wait.until(ExpectedConditions.elementToBeClickable(networkOnboard.payoutAccountId));
			networkOnboard.payoutAccountId.sendKeys(prop.get("payment_accountId").toString());
			logger.info("Entered account id - " + prop.get("payment_accountId").toString());			
			
			wait.until(ExpectedConditions.elementToBeClickable(networkOnboard.clientId));
			networkOnboard.clientId.sendKeys(prop.get("payment_clientId").toString());
			logger.info("Entered client id - " + prop.get("payment_clientId").toString());
			
			wait.until(ExpectedConditions.elementToBeClickable(networkOnboard.key));
			networkOnboard.key.sendKeys(prop.get("payment_key").toString());
			logger.info("Entered key - " + prop.get("payment_key").toString());
			
			wait.until(ExpectedConditions.elementToBeClickable(networkOnboard.secret));
			networkOnboard.secret.sendKeys(prop.get("payment_secret").toString());
			logger.info("Entered secret - " + prop.get("payment_secret").toString());			
			
			je.executeScript("arguments[0].scrollIntoView();", networkOnboard.payoutWebhookSecret);
			networkOnboard.payoutWebhookSecret.sendKeys(prop.get("payout_webhook_secret").toString());
			logger.info("Entered webhook secret - " + prop.get("payout_webhook_secret").toString());
			
		}
		
		networkOnboard.link.click();
		logger.info("Clicked on Link");
		
	}

}
