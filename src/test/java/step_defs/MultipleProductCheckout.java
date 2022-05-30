package step_defs;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import pages.BaseClass;
import utilities.Utilities;

public class MultipleProductCheckout extends BaseClass {

	public static List<String> urls;
	String parentWindow;
	protected static final Logger logger = LogManager.getLogger(MultipleProductCheckout.class.getName());

	@Given("^The list of products are obtained$")
	public void the_list_of_products_are_obtained() throws Throwable {

		urls = Utilities.readProductUrlsFromExcel();
		logger.info("List of urls from Excel - " + urls);

	}

	@When("^All the products are added to cart$")
	public void all_the_products_are_added_to_cart() throws Throwable {
		
		ArrayList<String> productInStock = new ArrayList<>();
		
		for(int i=0; i<urls.size(); i++) {

			driver.get(urls.get(i).toString());
			wait.until(ExpectedConditions.visibilityOf(checkout.buyNow));
			je.executeScript("arguments[0].scrollIntoView();", checkout.buyNow);
			
			if (checkout.buyNow.isEnabled()) {
				
				productInStock.add(urls.get(i));
				
			} else {
				
				logger.info("Product of stock !!!");
				
			}
			
		}
		
		for(int i=0; i<productInStock.size(); i++) {
			
			driver.get(urls.get(i));
			wait.until(ExpectedConditions.visibilityOf(checkout.buyNow));
			Thread.sleep(2000);
			je.executeScript("arguments[0].scrollIntoView();", checkout.buyNow);
			Thread.sleep(2000);
			checkout.buyNow.click();
			Thread.sleep(2000);
			logger.info("Clicked on buy now for " + ( i+1 ) + " product");
			wait.until(ExpectedConditions.visibilityOf(checkout.checkout));

		}
		
		logger.info("Added all elements to cart");
		wait.until(ExpectedConditions.elementToBeClickable(checkout.checkout));
		je.executeScript("arguments[0].click();", checkout.checkout);
//		checkout.checkout.click();
		logger.info("Clicked on checkout");

	}

	@When("^A checkout happens$")
	public void a_checkout_happens() throws Throwable {

		logger.info("Adding shipping details");
		wait.until(ExpectedConditions.elementToBeClickable(checkout.name));
		checkout.name.sendKeys("Automation Checkout");
		logger.info("Name - Automation Checkout");
		checkout.phone.sendKeys("8888888888");
		logger.info("Phone - 8888888888");
		autoRegisteredEmail = Utilities.getNewEmailId();
		checkout.email.sendKeys(autoRegisteredEmail);
		logger.info("Email - shoptype@mailinator.com");
		checkout.location.sendKeys("4498 Woodford Pass");
		Thread.sleep(4000);
		checkout.location.sendKeys(Keys.chord(Keys.ARROW_DOWN, Keys.ENTER));
		logger.info("Selected location");

		Thread.sleep(5000);
		je.executeScript("arguments[0].scrollIntoView();", checkout.continueCheckout);
		Thread.sleep(5000);
		Actions action = new Actions(driver);
		action.moveToElement(checkout.continueCheckout).click().perform();
		logger.info("Clicked on continue on checkout");

		try {
			
			wait.until(ExpectedConditions.elementToBeClickable(checkout.continuePayment));
			
		} catch (Exception e) {
			
			action.moveToElement(checkout.continueCheckout).click().perform();
			wait.until(ExpectedConditions.elementToBeClickable(checkout.continuePayment));
		}
		
		Thread.sleep(5000);
		checkout.continuePayment.click();
		logger.info("Clicked on continue to payment");
		
		try {
			
			wait.until(ExpectedConditions.visibilityOf(checkout.paymentModal));
			checkout.phoneNumber.sendKeys("1234567890");
			logger.info("Entered phone number 1234567890 on payment screen");
			
			Thread.sleep(5000);
			driver.switchTo().frame(checkout.paymentIframe);
			logger.info("Switched to payment iframe");
			
			wait.until(ExpectedConditions.elementToBeClickable(checkout.cardNumber));
			checkout.cardNumber.sendKeys("4242424242424242");
			logger.info("Entered card number - 4242424242424242");
			
			checkout.expiryDate.sendKeys("0228");
			logger.info("Entered expiry date - 02/28");
			checkout.cvc.sendKeys("4242");
			logger.info("Entered CVV - 4242");
			
			driver.switchTo().defaultContent();
			logger.info("Switched back to payment iframe");
			
			wait.until(ExpectedConditions.elementToBeClickable(checkout.pay));
			checkout.pay.click();
			Thread.sleep(3000);
			logger.info("Clicked on pay now");
		
		} catch (Exception e) {
			
			wait.until(ExpectedConditions.visibilityOf(checkout.authorizePyamentModel));
			checkout.authorizeCardNumber.sendKeys("4242 4242 4242 4242");
			logger.info("Entered card number - 4242 4242 4242 4242");
			
			checkout.authorizeCardMonth.sendKeys("06");
			logger.info("Entered card expiry month - 06");
	
			checkout.authorizeCardYear.sendKeys("29");
			logger.info("Entered card expiry year - 29");
			
			checkout.authorizeCVV.sendKeys("5454");
			logger.info("Entered card cvv - 5454");
			
			checkout.authorizePayNow.click();
			logger.info("Clicked on pay now");
			
		}

	}

	@Then("^The order should be placed$")
	public void the_order_should_be_placed() throws Throwable {
	    
		wait.until(ExpectedConditions.visibilityOf(checkout.orderConfirmed));
		Assert.assertTrue(checkout.orderConfirmed.isDisplayed());
		int refreshCount = 0;
				
		while(true) {
			
			try {
				
				if(refreshCount == refreshCountThreshold) {
					
					Assert.assertTrue("No Order ID Found", false);
					logger.info("No Order ID Found");
					
				}
				
				if(checkout.orderId.get(1).getText().split("#")[1] != null) {
					
					Assert.assertTrue("Order Id Found", Integer.parseInt(checkout.orderId.get(1).getText().split("#")[1]) > 0);
					logger.info("Order ID - " + Integer.parseInt(checkout.orderId.get(1).getText().split("#")[1]));
					break;
					
				}
				
			} catch (IndexOutOfBoundsException e) {
				
				System.out.println(e.getMessage());
				driver.navigate().refresh();
				Thread.sleep(3000);
				refreshCount++;
				logger.info("Trying to fetch order id after " + refreshCount + " refresh");
				
			}
			
		}
				
		logger.info("Order placed successfully");
		
	}

}
