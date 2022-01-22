package step_defs;

import java.io.IOException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.Assert;
import api_requests.APIRequest;
import api_requests.Payload;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.BaseClass;
import utilities.Utilities;

public class PaymentPayoutConfig extends BaseClass {
	
	private static final Logger logger = LogManager.getLogger(PaymentPayoutConfig.class);
	public static String attributionId;
	
	@Given("User signs up as {string}")
	public void user_signs_up(String userType) throws IOException {
	    
		logger.info(" ========== " + scenarioName + " ========== ");
		
		if(userType.equalsIgnoreCase("network")) {
			
			networkData = Utilities.createTestData("Network");
			logger.info("Created new user data for network - " + networkData);
			payload = Payload.registerUser(networkData.get("email"), networkData.get("password"), networkData.get("first_name") + " " + networkData.get("last_name"), networkData.get("phone_number"), prop.getProperty("email_bypass_id"));
			logger.info("Payload to register a new network - " + payload);
			response = APIRequest.registerUser(prop.getProperty("backend_beta_url"), payload, prop.getProperty("email_bypass_id"));
			logger.info("Response code from register user API - " + response.statusCode());
			if(response.statusCode() != 201) {
				
				logger.info("Response from register user api API - " + response.getBody().asPrettyString());
				
			}
			response.then().assertThat().statusCode(201);
			
			automationNetworkUserId = response.then().extract().body().jsonPath().get("data.id");
			logger.info("Network user id - " + automationNetworkUserId);
			
			payload = Payload.loginUser(networkData.get("email"), networkData.get("password"));
			logger.info("Login user data - " + payload);
			response = APIRequest.loginUser(prop.getProperty("backend_beta_url"), payload, prop.getProperty("email_bypass_id"));
			logger.info("Response code from login user API - " + response.statusCode());
			if(response.statusCode() != 200) {
				
				logger.info("Response from login user api API - " + response.getBody().asPrettyString());
				
			}
			response.then().assertThat().statusCode(200);
			
			loginAuthToken = response.then().extract().body().jsonPath().get("data.token");
			logger.info("Login Auth Token - " + loginAuthToken);

			
		} else if(userType.equalsIgnoreCase("vendor")) {
			
			vendorData = Utilities.createTestData("vendor");
			logger.info("Created new user data for vendor - " + vendorData);
			payload = Payload.registerUser(vendorData.get("email"), vendorData.get("password"), vendorData.get("first_name") + " " + vendorData.get("last_name"), vendorData.get("phone_number"), prop.getProperty("email_bypass_id"));
			logger.info("Payload to register a new vendor - " + payload);
			response = APIRequest.registerUser(prop.getProperty("backend_beta_url"), payload, prop.getProperty("email_bypass_id"));
			logger.info("Response code from register user API - " + response.statusCode());
			if(response.statusCode() != 201) {
				
				logger.info("Response from register user api API - " + response.getBody().asPrettyString());
				
			}
			response.then().assertThat().statusCode(201);
			
			automationVendorUserId = response.then().extract().body().jsonPath().get("data.id");
			logger.info("Vendor user id - " + automationVendorUserId);
			
			payload = Payload.loginUser(vendorData.get("email"), vendorData.get("password"));
			logger.info("Login user data - " + payload);
			response = APIRequest.loginUser(prop.getProperty("backend_beta_url"), payload, prop.getProperty("email_bypass_id"));
			logger.info("Response code from login user API - " + response.statusCode());
			if(response.statusCode() != 200) {
				
				logger.info("Response from login user api API - " + response.getBody().asPrettyString());
				
			}
			response.then().assertThat().statusCode(200);
			
			loginAuthToken = response.then().extract().body().jsonPath().get("data.token");
			logger.info("Login Auth Token - " + loginAuthToken);
			
		} else if(userType.equalsIgnoreCase("coseller")) {
			
			cosellerData = Utilities.createTestData("coseller");
			logger.info("Created new user data for coseller - " + cosellerData);
			payload = Payload.registerUser(cosellerData.get("email"), cosellerData.get("password"), cosellerData.get("first_name") + " " + cosellerData.get("last_name"), cosellerData.get("phone_number"), prop.getProperty("email_bypass_id"));
			logger.info("Payload to register a new coseller - " + payload);
			response = APIRequest.registerUser(prop.getProperty("backend_beta_url"), payload, prop.getProperty("email_bypass_id"));
			logger.info("Response code from register user API - " + response.statusCode());
			if(response.statusCode() != 201) {
				
				logger.info("Response from register user api API - " + response.getBody().asPrettyString());
				
			}
			response.then().assertThat().statusCode(201);
			
			automationCosellerUserId = response.then().extract().body().jsonPath().get("data.id");
			logger.info("Coseller user id - " + automationCosellerUserId);
			
			payload = Payload.loginUser(cosellerData.get("email"), cosellerData.get("password"));
			logger.info("Login user data - " + payload);
			response = APIRequest.loginUser(prop.getProperty("backend_beta_url"), payload, prop.getProperty("email_bypass_id"));
			logger.info("Response code from login user API - " + response.statusCode());
			if(response.statusCode() != 200) {
				
				logger.info("Response from login user api API - " + response.getBody().asPrettyString());
				
			}
			response.then().assertThat().statusCode(200);
			
			loginAuthToken = response.then().extract().body().jsonPath().get("data.token");
			logger.info("Login Auth Token - " + loginAuthToken);
			
		}
						
	}
	
	@When("User chooses {string} as a profile")
	public void user_chooses_as_a_profile(String userType) {
	    
		if(userType.equalsIgnoreCase("network")) {
			
			payload = Payload.authenticateUser("network");
			logger.info("Payload to create network profile - " + payload);
			response = APIRequest.authenticateUser(prop.getProperty("backend_beta_url"), "Network", payload, loginAuthToken);
			logger.info("Response code from authenticate user API - " + response.statusCode());
			if(response.statusCode() != 200) {
				
				logger.info("Response from authenticate user api - " + response.getBody().asPrettyString());
				
			}
			response.then().assertThat().statusCode(200);
			
			loginAuthToken = response.then().extract().body().jsonPath().get("token");
			logger.info("Login auth token from authenticate network api - " + loginAuthToken);
			
		} else if(userType.equalsIgnoreCase("vendor")) {
			
			payload = Payload.authenticateUser("vendor");
			logger.info("Payload to create vendor profile - " + payload);
			response = APIRequest.authenticateUser(prop.getProperty("backend_beta_url"), "Vendor", payload, loginAuthToken);
			logger.info("Response code from authenticate user API - " + response.statusCode());
			if(response.statusCode() != 200) {
				
				logger.info("Response from authenticate user api - " + response.getBody().asPrettyString());
				
			}
			response.then().assertThat().statusCode(200);
			
			loginAuthToken = response.then().extract().body().jsonPath().get("token");
			logger.info("Login auth token from authenticate vendor api - " + loginAuthToken);
			
		} else if(userType.equalsIgnoreCase("coseller")) {
			
			payload = Payload.authenticateUser("coseller");
			logger.info("Payload to create coseller profile - " + payload);
			response = APIRequest.authenticateUser(prop.getProperty("backend_beta_url"), "Coseller", payload, loginAuthToken);
			logger.info("Response code from authenticate user API - " + response.statusCode());
			if(response.statusCode() != 200) {
				
				logger.info("Response from authenticate user api - " + response.getBody().asPrettyString());
				
			}
			response.then().assertThat().statusCode(200);
			
			automationCosellerToken = response.then().extract().body().jsonPath().get("token");
			logger.info("Coseller auth token obtained - " + automationCosellerToken);
			
		}
		
	}
	
	@When("Enters the onboarding details for {string}")
	public void enter_onboarding_details(String userType) {
		
		if(userType.equalsIgnoreCase("network")) {
			
			payload = Payload.networkDetails(networkData.get("network_name"), networkData.get("newtork_url"));
			logger.info("Payload for entering network details - " + payload);
			response = APIRequest.createNetworkProfile(prop.getProperty("backend_beta_url"), payload, loginAuthToken);
			logger.info("Response code from create network profile API - " + response.statusCode());
			if(response.statusCode() != 200) {
				
				logger.info("Response from create network profile API - " + response.getBody().asPrettyString());
				
			}
			response.then().assertThat().statusCode(200);
			
			automationNetworkToken = response.then().extract().body().jsonPath().get("token");
			logger.info("Network auth token obtained - " + automationNetworkToken);
			
		} else if(userType.equalsIgnoreCase("vendor")) {
			
			payload = Payload.vendorDetails(vendorData.get("vendor_name"), vendorData.get("vendor_url"));
			logger.info("Payload for entering vendor details - " + payload);
			response = APIRequest.createVendorProfile(prop.getProperty("backend_beta_url"), payload, loginAuthToken);
			logger.info("Response code from create vendor profile API - " + response.statusCode());
			if(response.statusCode() != 200) {
				
				logger.info("Response from create vendor profile API - " + response.getBody().asPrettyString());
				
			}
			response.then().assertThat().statusCode(202);
			
			automationVendorToken = response.then().extract().body().jsonPath().get("token");
			logger.info("Vendor auth token obtained - " + automationVendorToken);
			
		}
		
	}
	
	@Then("User should be signed in as {string}")
	public void user_should_be_logged_in_as(String userType) {

		if (userType.equalsIgnoreCase("vendor")) {
			
			logger.info("Logged in as vendor");
			response = APIRequest.getVendorProfileDetails(prop.getProperty("backend_beta_url"), automationVendorToken);
			logger.info("Response code from get vendor profile API - " + response.statusCode());
			if(response.statusCode() != 200) {
				
				logger.info("Response from get vendor profile API - " + response.getBody().asPrettyString());
				
			}
			response.then().assertThat().statusCode(200);
			automationVendorId = response.then().extract().body().jsonPath().get("vendors[0].id");
			logger.info("Vendor ID - " + automationVendorId);

		} else if (userType.equalsIgnoreCase("coseller")) {

			logger.info("Logged in as coseller");

		} else if (userType.equalsIgnoreCase("network")) {
			
			logger.info("Logged in as network");
			response = APIRequest.getNetworkProfileDetails(prop.getProperty("backend_beta_url"), automationNetworkToken);
			logger.info("Response code from get network profile API - " + response.statusCode());
			if(response.statusCode() != 200) {
				
				logger.info("Response from get network profile API - " + response.getBody().asPrettyString());
				
			}
			response.then().assertThat().statusCode(200);
			automationNetworkId = response.then().extract().body().jsonPath().get("network.id");
			logger.info("Network ID - " + automationNetworkId);
			
		}
		
	}
					
	@Given("{string} auth token is obtained")
	public void vendor_auth_token_is_obtained(String userType) {
	    
		logger.info(" ============ " + scenarioName + " ============ ");
		
		if(userType.equalsIgnoreCase("network")) {
			
			logger.info("Network auth token - " + automationNetworkToken);
			
		} else if(userType.equalsIgnoreCase("vendor")) {
			
			logger.info("Vendor auth token - " + automationVendorToken);
			
		}
		
	}
				
	@When("Import product from shopify api is hit")
	public void import_product_from_shopify_api_is_hit() {
		
		payload = Payload.syncShopifyProducts(true, true);
		logger.info("Payload for syncing shopify store - " + payload);
	    
		response = APIRequest.syncShopifyStore(prop.getProperty("backend_beta_url"), automationVendorToken, payload);
		logger.info("Response code from sync shopify store API - " + response.statusCode());
		if(response.statusCode() != 200) {
			
			logger.info("Response from sync shopify store API - " + response.getBody().asPrettyString());
			
		}
		
	}
	
	@Then("Products should be imported from shopify to the vendor account")
	public void products_should_imported_from_shopify_to_the_vendor_account() {
	    
		response.then().assertThat().statusCode(202);
		logger.info("Products successfully imported from shopify");
		
	}
	
	@When("Update attribution api is hit")
	public void update_attribution_api_is_hit() {
		
		response = APIRequest.getAttributionDetails(prop.getProperty("backend_beta_url"), automationVendorToken, automationVendorId);
		logger.info("Response code from get attribution details API - " + response.statusCode());
		if(response.statusCode() != 200) {
			
			logger.info("Response from get attribution details API - " + response.getBody().asPrettyString());
			
		}
		response.then().assertThat().statusCode(200);
		
		attributionId = response.then().extract().body().jsonPath().get("data.id");
		logger.info("Attribution ID - " + attributionId);
		
		payload = Payload.updateAttributionDetails(automationVendorId, attributionId);
		logger.info("Payload for updating attribution - " + payload);
		
		response = APIRequest.updateAttribution(prop.getProperty("backend_beta_url"), automationVendorToken, payload, attributionId);
		logger.info("Response code from update attribution api - " + response.statusCode());
		if(response.statusCode() != 200) {
			
			logger.info("Response from update attribution api - " + response.getBody().asPrettyString());
			
		}
		
		
		
	}
	
	@Then("Attribution details should be updated for that vendor account")
	public void attribution_details_should_be_updated_for_that_vendor_account() {
	    
		response = APIRequest.getAttributionDetails(prop.getProperty("backend_beta_url"), automationVendorToken, automationVendorId);
		logger.info("Response code from get attribution details API - " + response.statusCode());
		if(response.statusCode() != 200) {
			
			logger.info("Response from get attribution details API - " + response.getBody().asPrettyString());
			
		}
		response.then().assertThat().statusCode(200);
		
		int lintro = response.then().extract().body().jsonPath().get("data.attributionConfig.configs.lIntro.percentage");
		int l1 = response.then().extract().body().jsonPath().get("data.attributionConfig.configs.l1.percentage");
		int l2 = response.then().extract().body().jsonPath().get("data.attributionConfig.configs.l2.percentage");
		int lx = response.then().extract().body().jsonPath().get("data.attributionConfig.configs.lX.percentage");
		int networkCommision = response.then().extract().body().jsonPath().get("data.attributionConfig.configs.networkCommission.percentage");	
		
		Assert.assertEquals(lintro, 20);
		Assert.assertEquals(l1, 20);
		Assert.assertEquals(l2, 20);
		Assert.assertEquals(lx, 40);
		Assert.assertEquals(networkCommision, 30);
		logger.info("Attribution details for the vendor verified successfully");
		
	}
	
	@When("Payment and payout details are added for the network")
	public void the_add_payment_details_api_is_hit() {
	    
		payload = Payload.addStripePaymentPayoutConfig(
						prop.getProperty("payment_accountId"), prop.getProperty("payment_key"), 
						prop.getProperty("payment_secret"), prop.getProperty("payment_clientId"));
		logger.info("Payload for adding payments and payouts - " + payload);
		
		response = APIRequest.addPaymentsAndPayouts(prop.getProperty("backend_beta_url"), automationNetworkToken, payload);
		logger.info("Response code from add payments and payouts API - " + response.statusCode());
		if(response.statusCode() != 200) {
			
			logger.info("Response from add payments and payouts API - " + response.getBody().asPrettyString());
			
		}
		response.then().assertThat().statusCode(200);
		
	}
	
	@Then("Payment configuration should be saved for that network")
	public void payment_configuration_should_be_saved_for_that_network() {
	    
		response = APIRequest.getPaymentDetails(prop.getProperty("backend_beta_url"), automationNetworkToken);
		logger.info("Response code from get payment details API - " + response.statusCode());
		if(response.statusCode() != 200) {
			
			logger.info("Response from get payment details API - " + response.getBody().asPrettyString());
			
		}
		response.then().assertThat().statusCode(200);
		
		Assert.assertEquals(response.then().extract().body().jsonPath().get("[0].last_4.key"), "g3np");
		logger.info("Payment details saved successfully for the network");
		
	}
	
	@Then("Payout configuration should be saved for that network")
	public void payout_configuration_should_be_saved_for_that_network() {
		
		response = APIRequest.getPayoutDetails(prop.getProperty("backend_beta_url"), automationNetworkToken);
		logger.info("Response code from get payouts API - " + response.statusCode());
		if(response.statusCode() != 200) {
			
			logger.info("Response from get payouts API - " + response.getBody().asPrettyString());
			
		}
		response.then().assertThat().statusCode(200);
		
		Assert.assertEquals(response.then().extract().body().jsonPath().get("data[0].last_4"), "g3np");
		logger.info("Payout details saved successfully for the network");
		
	}
	
	
	
	
	
	

	
	
	// ADD ALL SCENARIOS BEFORE THIS
	
	@Given("All the scenarios are executed")
	public void all_the_scenarios_are_executed() {
	    
		logger.info(" ========== " + scenarioName + " ========== ");
		
	}
	
	@Then("Delete {string} account")
	public void delete_account(String userType) {
	    
		if(userType.equalsIgnoreCase("network")) {
			
			APIRequest.deleteUser(prop.getProperty("backend_beta_url"), automationNetworkUserId, automationNetworkToken);
			logger.info("Deleted user id - " + automationNetworkUserId);
			
		} else if(userType.equalsIgnoreCase("vendor")) {
			
			APIRequest.deleteUser(prop.getProperty("backend_beta_url"), automationVendorUserId, automationVendorToken);
			logger.info("Deleted user id - " + automationVendorUserId);
			
		} else if(userType.equalsIgnoreCase("coseller")) {
			
			APIRequest.deleteUser(prop.getProperty("backend_beta_url"), automationCosellerUserId, automationCosellerToken);
			logger.info("Deleted user id - " + automationCosellerUserId);
			
		}
		
	}
	
}
