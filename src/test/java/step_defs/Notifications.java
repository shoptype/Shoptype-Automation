package step_defs;

import java.io.IOException;
import java.util.HashMap;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.support.ui.ExpectedConditions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.BaseClass;
import utilities.Utilities;

public class Notifications extends BaseClass{
	
	protected static final Logger logger = LogManager.getLogger(Notifications.class.getName());
	public static HashMap<String, String> signUpData;
	
	@Given("^A new user comes to shoptype$")
	public void open_website() throws IOException {
		
		logger.info(" =========== " + scenarioName + " =========== ");
		logger.info("Opened Website");
		signUpData = Utilities.createTestData("vendor");
		logger.info("Created test data - " + signUpData);
		
	}
	
	@When("^The user signs up$")
	public void signup_as_new_user() throws InterruptedException {
		
		wait.until(ExpectedConditions.elementToBeClickable(signUp.signUp));
		signUp.signUp.click();
		logger.info("Landed on sign up page");
		wait.until(ExpectedConditions.elementToBeClickable(signUp.firstName));
		signUp.registerNewUser(wait, signUpData);
		logger.info("Registered new user successfully");
		wait.until(ExpectedConditions.visibilityOf(notifications.emailSent));
		
	}
	
	@Then("The user should receive a notification regarding {string}")
	public void check_for_verify_email(String emailType) throws InterruptedException {
		boolean isNotificationReceived = false;
		
		logger.info("Opened email");
		
		if(emailType.contains("verify")) {
			
			isNotificationReceived = Utilities.verifyNotifications(driver, wait, signUpData.get("email"), "verify");
			
		} else if(emailType.contains("reset password")) {
			
			isNotificationReceived = Utilities.verifyNotifications(driver, wait, signUpData.get("email"), "reset password");
			
		} else if(emailType.contains("auto registered")) {
			
			isNotificationReceived = Utilities.verifyNotifications(driver, wait, autoRegisteredEmail, "auto registered");
			
		} else if(emailType.contains("order")) {
			
			isNotificationReceived = Utilities.verifyNotifications(driver, wait, autoRegisteredEmail, "order confirmation");
			
		} else if(emailType.contains("vendor")) {
			
			isNotificationReceived = Utilities.verifyNotifications(driver, wait, vendorEmail, "vendor order");
			
		} else if(emailType.contains("otp")) {
			
			isNotificationReceived = Utilities.verifyNotifications(driver, wait, cosellerEmail, "otp verification");
			
		} else if(emailType.contains("referral")) {
			
			isNotificationReceived = Utilities.verifyNotifications(driver, wait, cosellerEmail, "referral");
			
		}
		
		Assert.assertTrue("Notification not found", isNotificationReceived);
		logger.info("Notification received successfully");
		
	}
	
	@Given("A user clicks on forgot password")
	public void click_forgot_password() {
		
		logger.info(" =========== " + scenarioName + " =========== ");
		notifications.forgotPassword.click();
		logger.info("Clicked on forgot password");
		
	}
	
	@When("The user enters email")
	public void enter_email() {
		
		signUp.email.get(0).sendKeys(signUpData.get("email"));
		logger.info("Entered Email for forgot password - " + signUpData.get("email"));
		notifications.sendForgotPasswordEmail.click();
		logger.info("Clicked on send");
		
	}
	
	@Given("A user has placed an order using email address")
	public void place_order() {
		
		logger.info(" =========== " + scenarioName + " =========== ");
		
	}
	
	@When("The user opens the email")
	public void open_email() {
		
		logger.info("Email Address used while placing order - " + autoRegisteredEmail);
		
	}
	
	@When("The user opens the vendor email id")
	public void open_vendor_email() {
		
		logger.info("Vendor Email - " + vendorEmail);
		
	}
	
	@Given("A user wants to cosell a product")
	public void coseller_product() {
		
		logger.info(" =========== " + scenarioName + " =========== ");
		driver.get(firstVendorPublishProductUrl);
		logger.info("Publish Product Link - " + firstVendorPublishProductUrl);
		
	}
	
	@When("The user clicks on cosell and registers as a  new user")
	public void coseller_signup() throws IOException, InterruptedException {
		
		wait.until(ExpectedConditions.elementToBeClickable(cosellerRegistration.cosell));
		cosellerRegistration.cosell.click();
		je.executeScript("arguments[0].scrollIntoView();", cosellerRegistration.cosellRegister);
		cosellerRegistration.cosellRegister.click();
		logger.info("Clicked on register button");
		je.executeScript("arguments[0].scrollIntoView();", cosellerRegistration.signUpCosell);
		wait.until(ExpectedConditions.elementToBeClickable(cosellerRegistration.signUpCosell));
		cosellerRegistration.signUpCosell.click();
		logger.info("Clicked on coseller sign up button");
		signUpData = Utilities.createTestData("coseller");
		logger.info("User Data - " + signUpData);
		cosellerEmail = signUpData.get("email");
		
		wait.until(ExpectedConditions.elementToBeClickable(cosellerRegistration.firstName));
		cosellerRegistration.registerCoseller(signUpData);
		logger.info("Registered as a coseller");
		
	}
	
	@Given("A user wants to refer a person to shoptype")
	public void refer_a_user() {
		
		logger.info(" =========== " + scenarioName + " =========== ");
		signUpData.replace("email", vendorEmail);
		logger.info("User Data - " + signUpData);
		login.signInUser(wait, signUpData);
		logger.info("User logged in");
		
		wait.until(ExpectedConditions.visibilityOf(vendorOnboard.vendor));
		vendorOnboard.vendor.click();
		wait.until(ExpectedConditions.visibilityOf(vendorOnboard.next));
		vendorOnboard.next.click();
		wait.until(ExpectedConditions.visibilityOf(vendorOnboard.vendorProfile));
		vendorOnboard.vendorProfile.click();
		wait.until(ExpectedConditions.visibilityOf(vendorOnboard.refer));
		vendorOnboard.refer.click();
		logger.info("Clicked on profile");
		
		wait.until(ExpectedConditions.elementToBeClickable(notifications.toEmail));
		cosellerEmail = Utilities.getNewEmailId();
		notifications.toEmail.sendKeys(cosellerEmail);
		logger.info("Referring user with email id - " + cosellerEmail);
		
	}
	
	@When("The user sends an invite")
	public void sent_invite() {
		
		wait.until(ExpectedConditions.elementToBeClickable(notifications.sendForgotPasswordEmail));
		je.executeScript("arguments[0].scrollIntoView();", notifications.sendForgotPasswordEmail);
		notifications.sendForgotPasswordEmail.click();
		logger.info("Refer email sent");
		
	}
	
}
