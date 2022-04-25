package step_defs;

import java.io.IOException;
import java.util.HashMap;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.Assert;

import api_requests.APIRequest;
import api_requests.Payload;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import pages.BaseClass;
import utilities.Utilities;

public class MultiVendorNetworkCheckout extends BaseClass {

	protected static final Logger logger = LogManager.getLogger(MultiVendorNetworkCheckout.class.getName());
	public static String platformId;
	public static HashMap<String, String> data;
	public static double actualTax1;
	public static double actualTax2;
	
	@Given("User logs in as {string}")
	public void user_logs_in_into_his_account(String userType) throws IOException {
		
		logger.info(" =========== " + scenarioName + " =========== ");
	    
		if(userType.equalsIgnoreCase("network")) {
			
			data = Utilities.readExcel("network", "");
			logger.info("Network login details - " + data);
			
		} else if(userType.equalsIgnoreCase("vendor one")) {
			
			data = Utilities.readExcel("vendor", "1");
			logger.info("Vendor One login details - " + data);
			vendorEmail = data.get("email");
			
		} else if(userType.equalsIgnoreCase("vendor two")) {
			
			data = Utilities.readExcel("vendor", "2");
			logger.info("Vendor Two login details - " + data);
			
		} else if(userType.equalsIgnoreCase("coseller")) {
			
			data = Utilities.readExcel("coseller", "");
			logger.info("Coseller login details - " + data);
			
		}
		
		payload = Payload.loginUser(data.get("email"), data.get("password"));
		logger.info("Payload for login api - " + payload);
		
		response = APIRequest.loginUser(prop.getProperty("backend_beta_url"), payload, null);
		logger.info("Response code from Login API - " + response.statusCode());
		if(response.statusCode() != 200) {
			
			logger.info("Response from create empty  API - " + response.getBody().asPrettyString());
			
		}

		loginAuthToken = response.then().extract().body().jsonPath().get("data.token");
		logger.info("Login Auth Token - " + loginAuthToken);
		
	}
	
	@When("User selects {string} as a profile")
	public void select_profile(String userType) {
		
		if(userType.equalsIgnoreCase("network")) {
			
			payload = Payload.authenticateUser("network");
			logger.info("Payload to authenticate network profile - " + payload);
			response = APIRequest.authenticateUser(prop.getProperty("backend_beta_url"), "Network", payload, loginAuthToken);
			logger.info("Response code from authenticate API - " + response.statusCode());
			if(response.statusCode() != 200) {
				
				logger.info("Response from create empty  API - " + response.getBody().asPrettyString());
				
			}
			response.then().assertThat().statusCode(200);
			
			networkAuthToken = response.then().extract().body().jsonPath().get("token");
			logger.info("Network auth token - " + networkAuthToken);
			
		} else if(userType.equalsIgnoreCase("vendor one")) {
			
			payload = Payload.authenticateUser("vendor");
			logger.info("Payload to authenticate vendor one profile - " + payload);
			response = APIRequest.authenticateUser(prop.getProperty("backend_beta_url"), "Vendor", payload, loginAuthToken);
			logger.info("Response code from authenticate API - " + response.statusCode());
			if(response.statusCode() != 200) {
				
				logger.info("Response from create empty  API - " + response.getBody().asPrettyString());
				
			}
			response.then().assertThat().statusCode(200);
			
			firstVendorAuthToken = response.then().extract().body().jsonPath().get("token");
			logger.info("First vendor auth token - " + firstVendorAuthToken);
			
		} else if(userType.equalsIgnoreCase("vendor two")) {
			
			payload = Payload.authenticateUser("vendor");
			logger.info("Payload to authenticate vendor two network profile - " + payload);
			response = APIRequest.authenticateUser(prop.getProperty("backend_beta_url"), "Vendor", payload, loginAuthToken);
			logger.info("Response code from authenticate API - " + response.statusCode());
			if(response.statusCode() != 200) {
				
				logger.info("Response from create empty  API - " + response.getBody().asPrettyString());
				
			}
			response.then().assertThat().statusCode(200);
			
			secondVendorAuthToken = response.then().extract().body().jsonPath().get("token");
			logger.info("Second vendor auth token - " + secondVendorAuthToken);
			
		} else if(userType.equalsIgnoreCase("coseller")) {
			
			payload = Payload.authenticateUser("coseller");
			logger.info("Payload to authenticate coseller profile - " + payload);
			response = APIRequest.authenticateUser(prop.getProperty("backend_beta_url"), "Coseller", payload, loginAuthToken);
			logger.info("Response code from authenticate API - " + response.statusCode());
			if(response.statusCode() != 200) {
				
				logger.info("Response from create empty  API - " + response.getBody().asPrettyString());
				
			}
			response.then().assertThat().statusCode(200);
			
			cosellerAuthToken = response.then().extract().body().jsonPath().get("token");
			logger.info("Coseller auth token - " + cosellerAuthToken);
			
		}
		
	}
	
	@Then("user should be logged in as {string}")
	public void user_should_be_logged_in_as(String userType) {
	    
		if(userType.equalsIgnoreCase("network")) {
			
			
			
		} else if(userType.equalsIgnoreCase("vendor one")) {
			
			response = APIRequest.getVendorProfileDetails(prop.getProperty("backend_beta_url"), firstVendorAuthToken);
			logger.info("Response code from get vendor profile API - " + response.statusCode());
			if(response.statusCode() != 200) {
				
				logger.info("Response from get vendor profile API - " + response.getBody().asPrettyString());
				
			}
			response.then().assertThat().statusCode(200);
			firstVendorId = response.then().extract().body().jsonPath().get("vendors[0].id");
			logger.info("Vendor ID - " + firstVendorId);
			
		} else if(userType.equalsIgnoreCase("vendor two")) {
						
			response = APIRequest.getVendorProfileDetails(prop.getProperty("backend_beta_url"), secondVendorAuthToken);
			logger.info("Response code from get vendor profile API - " + response.statusCode());
			if(response.statusCode() != 200) {
				
				logger.info("Response from get vendor profile API - " + response.getBody().asPrettyString());
				
			}
			response.then().assertThat().statusCode(200);
			secondVendorId = response.then().extract().body().jsonPath().get("vendors[0].id");
			logger.info("Vendor ID - " + secondVendorId);
			
		} else if(userType.equalsIgnoreCase("coseller")) {
			
			response = APIRequest.getCosellerProfileDetails(prop.getProperty("backend_beta_url"), cosellerAuthToken);
			logger.info("Response code from get coseller profile API - " + response.statusCode());
			if(response.statusCode() != 200) {
				
				logger.info("Response from get coseller profile API - " + response.getBody().asPrettyString());
				
			}
			cosellerUserId = response.then().extract().body().jsonPath().get("id");
			logger.info("Coseller user id - " + cosellerUserId);
			
		} 
		
	}

	@Given("^Network auth token is obtained$")
	public void get_network_auth_token() {
		
		logger.info(" =========== " + scenarioName + " =========== ");

		logger.info("Network Auth Token - " + networkAuthToken);

	}

	@When("^Get Network Details API is hit$")
	public void network_details_api() {

		response = APIRequest.getNetworkDetails(prop.getProperty("backend_beta_url"), networkAuthToken);
		logger.info("Response code from get network profile API - " + response.statusCode());
		if(response.statusCode() != 200) {
			
			logger.info("Response from get network profile API - " + response.getBody().asPrettyString());
			
		}

	}

	@Then("^Platform Url and Platform Url should be saved$")
	public void get_platform_url_and_platform_id() {

		response.then().assertThat().statusCode(200);
		platformId = response.then().extract().body().jsonPath().getString("platforms[0].id");
		platformUrl = "https://" + response.then().extract().body().jsonPath().getString("platforms[0].url");
		logger.info("Platform ID - " + platformId);
		logger.info("Platform URL - " + platformUrl);

	}

	@Given("^Device id, tracker id and platform url is obtained for \"([^\"]*)\" vendor product$")
	public void get_tracker_id_and_url(String userNumber) {

		logger.info(" =========== " + scenarioName + " =========== ");

		if (userNumber.equalsIgnoreCase("first")) {

			logger.info("Tracker ID - " + firstVendorTrackerId);
			payload = Payload.createUserEventWithPlatformUrl(deviceId, firstVendorPublishProductUrl,
					firstVendorTrackerId, platformUrl);

		} else if (userNumber.equalsIgnoreCase("second")) {

			logger.info("Tracker ID - " + secondVendorTrackerId);
			payload = Payload.createUserEventWithPlatformUrl(deviceId, secondVendorPublishProductUrl,
					secondVendorTrackerId, platformUrl);

		}

		logger.info("Payload - " + payload);

	}

	@Given("^Device Id, cart id and platform id is obtained$")
	public void get_platform_id() {

		logger.info(" =========== " + scenarioName + " =========== ");
		logger.info(" Device Id - " + deviceId);
		logger.info(" Cart Id - " + cartId);
		logger.info(" Platform Id - " + platformId);

	}

	@When("^Create checkout api is hit with platform id$")
	public void hit_create_checkout_api_with_platform_id() {
		
		response = APIRequest.createCheckout(prop.getProperty("backend_beta_url"), shoptypeApiKey, deviceId, cartId, platformId);
		logger.info("Response code from create checkout with platform id API - " + response.statusCode());
		if(response.statusCode() != 200) {
			
			logger.info("Response ahdbfbadhfbbakd from create checkout with platform id API - " + response.getBody().asPrettyString());
			
		}

	}

	@Given("^Checkout Url is obtained$")
	public void get_checkout_url() {

		logger.info(" =========== " + scenarioName + " =========== ");
		driver.get("https://" + redirectUri.toString());
		logger.info("Opened checkout url - " + redirectUri);

	}
	
	@Given("Checkout id has been obtained")
	public void get_checkout_id() {
		
		logger.info(" =========== " + scenarioName + " =========== ");
		
		logger.info("Checkout Id - " + checkoutId);
		logger.info("First vendor id - " + firstVendorId);
		logger.info("Second vendor id - " + secondVendorId);
			
	}
	
	@When("Checkout details for that order is requested")
	public void checkout_details_api() throws InterruptedException {
		
		boolean isFound = false;
		int hitCount = 0;
		
		while(true) {
			
			if(hitCount == 15) {
				
				Assert.assertTrue("Taxes at vendor and product level not found", false);
				break;
				
			}
			
			if(response.then().extract().body().jsonPath().get("order_details_per_vendor." + firstVendorId + ".cart_lines[0].taxes.amount") != null) {
				
				isFound = true;
				
			} else {
				
				response = APIRequest.getCheckoutDetails(prop.getProperty("backend_beta_url"), checkoutId, prop.getProperty("shoptype_api_key"));
				logger.info("Response code from get checkout details api - " + response.getStatusCode());
				
				if(response.statusCode() != 200) {
					
					logger.info("Response from get checkout details api - " + response.getBody().asPrettyString());
					
				}
				
				Thread.sleep(3000);
				
			}
			
			if(isFound == true) {
				
				break;
				
			}
			
			hitCount++;
			logger.info("Number of time checkout details api was requested " + hitCount);
			
		}
				
	}
	
	@Then("Response should contain the shipping and taxes both at vendor and product level")
	public void response_should_contatin_taxes_object() {
				
		logger.info("First Vendor Tax - " + response.then().extract().body().jsonPath().get("order_details_per_vendor." + firstVendorId + ".taxes"));
		float firstVendorTax = response.then().extract().body().jsonPath().get("order_details_per_vendor." + firstVendorId + ".taxes.amount");
		
		logger.info("Second Vendor Tax - " + response.then().extract().body().jsonPath().get("order_details_per_vendor." + secondVendorId + ".taxes"));
		float secondVendorTax = response.then().extract().body().jsonPath().get("order_details_per_vendor." + secondVendorId + ".taxes.amount");
		
		Assert.assertTrue(firstVendorTax > 0);
		logger.info("Verified first vendor tax");
		Assert.assertTrue(secondVendorTax > 0);
		logger.info("Verified second vendor tax");
		
		logger.info("Cart lines for first vendor product - " + response.then().extract().body().jsonPath().get("order_details_per_vendor." + firstVendorId + ".cart_lines"));
		float firstVendorProductTax = response.then().extract().body().jsonPath().get("order_details_per_vendor." + firstVendorId + ".cart_lines[0].taxes.amount");
		Assert.assertTrue(firstVendorProductTax > 0);
		logger.info("Verified first vendor product tax");
		
		logger.info("Cart lines for second vendor product - " + response.then().extract().body().jsonPath().get("order_details_per_vendor." + secondVendorId + ".cart_lines"));
		float secondVendorProductTax = response.then().extract().body().jsonPath().get("order_details_per_vendor." + secondVendorId + ".cart_lines[0].taxes.amount");
		Assert.assertTrue(secondVendorProductTax > 0);
		logger.info("Verified second vendor product tax");
		
	}
	
}
