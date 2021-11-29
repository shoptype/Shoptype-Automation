package step_defs;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import api_requests.APIRequest;
import api_requests.Payload;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import pages.BaseClass;

public class MultiVendorNetworkCheckout extends BaseClass {

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
//		logger.info("Response from Get Network Details API - " + response.getBody().asPrettyString());

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
		logger.info("Response Code from Create Checkout API with Platform ID - " + response.statusCode());
//		logger.info("Response from Create Checkout API with Platform ID - " + response.getBody().asPrettyString());

	}

	@Given("^Checkout Url is obtained$")
	public void get_checkout_url() {

		logger.info(" =========== " + scenarioName + " =========== ");
		driver.get("https://" + redirectUri.toString());
		logger.info("Opened checkout url - " + redirectUri);

	}

}
