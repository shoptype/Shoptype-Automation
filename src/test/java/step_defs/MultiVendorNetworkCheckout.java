package step_defs;

import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import api_requests.APIRequest;
import api_requests.Payload;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import pages.BaseClass;
import utilities.Utilities;

public class MultiVendorNetworkCheckout extends BaseClass{
	
	public static JavascriptExecutor je;
	
	protected static final Logger logger = LogManager.getLogger(MultiVendorNetworkCheckout.class.getName());
	public static String platformId;
	
	@Given("^Network auth token is obtained$")
	public void get_network_auth_token() {
		
		logger.info("Network Auth Token - " + networkAuthToken);
		
	}
	
	@When("^Get Network Details API is hit$")
	public void network_details_api() {
		
		response = APIRequest.getNetworkDetails(prop.getProperty("backend_beta_url"), networkAuthToken);
		logger.info("Response code from Get Network Details API - " + response.getStatusCode());
		logger.info("Response from Get Network Details API - " + response.getBody().asPrettyString());
		
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
		deviceId = Utilities.getDeviceId();
		
		if(userNumber.equalsIgnoreCase("first")) {
			
			logger.info("Tracker ID - " + firstVendorTrackerId);
			payload = Payload.createUserEventWithPlatformUrl(deviceId, firstVendorPublishProductUrl, firstVendorTrackerId, platformUrl);
			
		} else if(userNumber.equalsIgnoreCase("second")) {
			
			logger.info("Tracker ID - " + secondVendorTrackerId);
			payload = Payload.createUserEventWithPlatformUrl(deviceId, secondVendorPublishProductUrl, secondVendorTrackerId, platformUrl);
			
		}
		
		logger.info("Payload - " + payload);
		
	}
	
	@Given("^Checkout Url is obtained$")
	public void get_checkout_url() {
		
		logger.info(" =========== " + scenarioName + " =========== ");
		
	}
	
	@Then("^\"([^\"]*)\" Checkout Url should be written to file$")
	public void write_checkout_url_to_file(String urlType) throws IOException {
		
		checkoutProp = Utilities.readPropertiesFiles("checkoutUrls.properties");
		
		if(urlType.contains("Platform")) {
			
			checkoutProp.setProperty("Multi_Vendor_Platform_Checkout_Url", redirectUri);
			
		} else if(urlType.contains("Intro")) {
			
			checkoutProp.setProperty("Vendor_Attribution_Intro_Close_Checkout_Url", redirectUri);
			
		} else if(urlType.contains("Shoptype")) {
			
			checkoutProp.setProperty("Multi_Vendor_Shoptype_Checkout_Url", redirectUri);
			
		}

		checkoutProp.store(new FileOutputStream(System.getProperty("user.dir") + "/src/main/resources/props/checkoutUrls.properties"), "URL written to file");
		logger.info("Checkout Url written to property file");
		
	}
	
}
