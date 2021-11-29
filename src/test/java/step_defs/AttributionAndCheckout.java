package step_defs;

import static org.hamcrest.Matchers.*;
import java.io.IOException;
import java.util.*;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Assert;
import org.openqa.selenium.support.ui.ExpectedConditions;
import api_requests.APIRequest;
import api_requests.Payload;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.path.json.JsonPath;
import pages.BaseClass;
import utilities.Utilities;

public class AttributionAndCheckout extends BaseClass {

	protected static final Logger logger = LogManager.getLogger(AttributionAndCheckout.class.getName());
	public static boolean isLoggedIn = false;
	public static boolean isEmailVerified;
	public static String vendorName;
	public static HashMap<String, String> data;
	public static String vendorId;
	public static String productId;
	public static String productVariantId;
	public static String attributionId;
	public static String lIntro;
	public static String lX;
	public static String l1;
	public static String networkComission;
	public static String publishSlug;
	public static String orderId;
	public static String trackerId;
	public static String publishProductUrl;
	public static String orderTotal;

	@Given("^User opens the website and signs in as existing user$")
	public void user_opens_the_website_and_wants_to_register_a_new_user() throws IOException {
		
		logger.info(" =========== " + scenarioName + " =========== ");

	}

	@When("^User enters all the required details to signin as \"([^\"]*)\" \"([^\"]*)\"$")
	public void user_enters_all_the_required_details_to_signup_and_verifies_the_email(String userNumber, String userType) throws IOException, InterruptedException {
		
		if (userType.equalsIgnoreCase("vendor")) {
			
			if(userNumber.equalsIgnoreCase("")) {
				
				Assert.assertTrue(userSignIn("", "Vendor"));
				logger.info("Signed in as Vendor");
				
			} else if (userNumber.equalsIgnoreCase("first")) {
				
				Assert.assertTrue(userSignIn("First", "Vendor"));
				logger.info("Signed in first Vendor");				
				
			} else if (userNumber.equalsIgnoreCase("second")) {

				Assert.assertTrue(userSignIn("Second", "Vendor"));
				logger.info("Signed in second Vendor");
				
			}
		
		} else if (userType.equalsIgnoreCase("coseller")) {

			Assert.assertTrue(userSignIn("", "Coseller"));
			logger.info("Signed in as Coseller");

		} else if(userType.equalsIgnoreCase("network")) {
			
			Assert.assertTrue(userSignIn("", "Network"));
			logger.info("Signed in as Network");			
			
		}

	}

	@When("^Clicks on \"([^\"]*)\"$")
	public void clicks_on(String userType) throws IOException, InterruptedException {
		
		if (userType.equalsIgnoreCase("vendor")) {

			wait.until(ExpectedConditions.visibilityOf(vendorOnboard.vendor)).click();
			logger.info("Selected Vendor");
			vendorOnboard.next.click();

		} else if (userType.equalsIgnoreCase("coseller")) {

			wait.until(ExpectedConditions.visibilityOf(cosellerOnboard.coseller)).click();
			logger.info("Selected Coseller");
			vendorOnboard.next.click();

		} else if (userType.equalsIgnoreCase("network")) {
			
			wait.until(ExpectedConditions.visibilityOf(networkOnboard.network)).click();
			logger.info("Selected Network");
			vendorOnboard.next.click();
			
		}
		
		Thread.sleep(5000);
		
	}

	@Then("^User should be logged in as \"([^\"]*)\"$")
	public void user_should_be_logged_in_as(String userType) {

		if (userType.equalsIgnoreCase("vendor")) {
			
			wait.until(ExpectedConditions.visibilityOf(vendorOnboard.vendorProfile));
			Assert.assertTrue(vendorOnboard.vendorProfile.isDisplayed());
			logger.info("Landed on vendor dashboard");
			vendorUserId = Utilities.getItemFromLocalStorage(driver, "_id");
			logger.info("User ID of vendor - " + vendorUserId);
			vendorAuthToken = Utilities.getItemFromLocalStorage(driver, "token");
			logger.info("Vendor Auth Token - " + vendorAuthToken);

		} else if (userType.equalsIgnoreCase("coseller")) {

			wait.until(ExpectedConditions.visibilityOf(cosellerOnboard.cosellerProfile));
			Assert.assertTrue(cosellerOnboard.cosellerProfile.isDisplayed());
			logger.info("Landed on coseller dashboard");
			cosellerUserId = Utilities.getItemFromLocalStorage(driver, "_id");
			logger.info("User ID of coseller - " + cosellerUserId);
			cosellerAuthToken = Utilities.getItemFromLocalStorage(driver, "token");
			logger.info("Coseller Auth Token - " + cosellerAuthToken);

		} else if (userType.equalsIgnoreCase("network")) {
			
			wait.until(ExpectedConditions.visibilityOf(networkOnboard.networkProfile));
			Assert.assertTrue(networkOnboard.networkProfile.isDisplayed());
			logger.info("Landed on network dashboard");
			networkUserId = Utilities.getItemFromLocalStorage(driver, "_id");
			logger.info("User ID of network - " + networkUserId);
			networkAuthToken = Utilities.getItemFromLocalStorage(driver, "token");
			logger.info("Network Auth Token - " + networkAuthToken);
			
		}

	}
	
	@Then("^Vendor id and user id should be saved for \"([^\"]*)\" vendor$")
	public void save_first_vendor_id_and_user_id(String userNumber) {
		
		if(userNumber.equalsIgnoreCase("first")) {
			
			firstVendorUserId = Utilities.getItemFromLocalStorage(driver, "_id");
			logger.info("User ID of first vendor - " + firstVendorUserId);
			JsonPath js = new JsonPath(Utilities.getItemFromLocalStorage(driver, "userProfile"));
			firstVendorId = js.get("vendors[0].id");
			logger.info("First Vendor ID - " + firstVendorId);
			
		} else if(userNumber.equalsIgnoreCase("second")) {
			
			secondVendorUserId = Utilities.getItemFromLocalStorage(driver, "_id");
			logger.info("User ID of second vendor - " + secondVendorUserId);
			JsonPath js = new JsonPath(Utilities.getItemFromLocalStorage(driver, "userProfile"));
			secondVendorId = js.get("vendors[0].id");
			logger.info("Second Vendor ID - " + secondVendorId);
			
		} else if(userNumber.equalsIgnoreCase("")) {
			
			vendorUserId = Utilities.getItemFromLocalStorage(driver, "_id");
			logger.info("User ID of vendor - " + vendorUserId);
			JsonPath js = new JsonPath(Utilities.getItemFromLocalStorage(driver, "userProfile"));
			vendorId = js.get("vendors[0].id");
			logger.info("Vendor ID - " + vendorId);
			
		}
		
	}

	@Then("^\"([^\"]*)\" user id should be saved$")
	public void user_id_should_be_saved(String userType) {
		
		if(userType.equalsIgnoreCase("coseller")) {
			
			cosellerUserId = Utilities.getItemFromLocalStorage(driver, "_id");
			logger.info("User ID of coseller - " + cosellerUserId);
			
		} else if(userType.equalsIgnoreCase("network")) {
			
			networkUserId = Utilities.getItemFromLocalStorage(driver, "_id");
			logger.info("User ID of network - " + networkUserId);
			
		}
		
	}

	@Then("^Authorization token of \"([^\"]*)\" \"([^\"]*)\" should be saved$")
	public void authorization_token_of_should_be_saved(String userNumber, String userType) {

		if (userType.equalsIgnoreCase("vendor")) {
			
			if(userNumber.equalsIgnoreCase("")) {
				
				vendorAuthToken = Utilities.getItemFromLocalStorage(driver, "token");
				logger.info("Vendor Auth Token - " + vendorAuthToken);
				
			} else if(userNumber.equalsIgnoreCase("first")) {
				
				firstVendorAuthToken = Utilities.getItemFromLocalStorage(driver, "token");
				logger.info("First Vendor Auth Token - " + firstVendorAuthToken);	
				
			} else if(userNumber.equalsIgnoreCase("second")) {
				
				secondVendorAuthToken = Utilities.getItemFromLocalStorage(driver, "token");
				logger.info("Second Vendor Auth Token - " + secondVendorAuthToken);	
				
			}
			
		} else if (userType.equalsIgnoreCase("coseller")) {

			cosellerAuthToken = Utilities.getItemFromLocalStorage(driver, "token");
			logger.info("Coseller Auth Token - " + cosellerAuthToken);

		} else if (userType.equalsIgnoreCase("network")) {
			
			networkAuthToken = Utilities.getItemFromLocalStorage(driver, "token");
			logger.info("Network Auth Token - " + networkAuthToken);
			
		}

	}

	@Given("^Authorization token of \"([^\"]*)\" vendor and the Shopify payload are obtained$")
	public void set_authorization_and_payload(String userNumber) {
		
		logger.info(" =========== " + scenarioName + " =========== ");
		payload = Payload.syncShopifyProducts();
		logger.info("Store - Shopify");
		logger.info("Payload - " + payload);
		
		if(userNumber.equalsIgnoreCase("")) {
			
			logger.info("Vendor Auth Token - " + vendorAuthToken);
			
		} else if(userNumber.equalsIgnoreCase("first")) {
			
			logger.info("First Vendor Auth Token - " + firstVendorAuthToken);
			
		} else if(userNumber.equalsIgnoreCase("second")) {
			
			logger.info("Second Vendor Auth Token - " + secondVendorAuthToken);
			
		}

	}

	@When("^Shopify Sync Product API is hit for \"([^\"]*)\" vendor$")
	public void sync_shopify_products(String userNumber) throws IOException {
		
		if(userNumber.equalsIgnoreCase("")) {
			
			response = APIRequest.syncShopifyStore(prop.getProperty("backend_beta_url"), vendorAuthToken, payload);
				
		} else if(userNumber.equalsIgnoreCase("first")) {
			
			response = APIRequest.syncShopifyStore(prop.getProperty("backend_beta_url"), firstVendorAuthToken, payload);
			
		} else if(userNumber.equalsIgnoreCase("second")) {
			
			response = APIRequest.syncShopifyStore(prop.getProperty("backend_beta_url"), secondVendorAuthToken, payload);

		}
		
//		logger.info("Response from Sync Shopify API - " + response.getBody().asPrettyString());
		logger.info("Response code from Sync Shopify API - " + response.statusCode());
		
	}

	@Then("^Products should added to catalog and the store should be synced$")
	public void check_store_syc() {

		if(response.getStatusCode() != 400) {
			
			response.then().assertThat().statusCode(202);
			
		}

	}

	@Given("^Authorization token of \"([^\"]*)\" vendor and attribution payload for setting intro and close is obtained$")
	public void get_payload_for_setting_intro_and_close(String userNumber) {
		
		logger.info(" =========== " + scenarioName + " =========== ");
		
		if(userNumber.equalsIgnoreCase("")) {

			logger.info("Setting Attribution with Intro and Close level");
			payload = Payload.setAttribution_Intro_Close(vendorId);
			
		} else if(userNumber.equalsIgnoreCase("first")) {
			
			logger.info("Setting Attribution with Intro and Close level for First Vendor");
			payload = Payload.setAttribution_Intro_Close(firstVendorId);
			
		} else if(userNumber.equalsIgnoreCase("second")) {
			
			logger.info("Setting Attribution with Intro and Close level for Second vendor");
			payload = Payload.setAttribution_Intro_Close(secondVendorId);
			
		}
		
		logger.info("Payload - " + payload);

	}

	@When("^Create Vendor Attribution API is hit for \"([^\"]*)\" vendor$")
	public void create_vendor_attribution(String userNumber) throws IOException {
		
		if(userNumber.equalsIgnoreCase("")) {
			
			response = APIRequest.setAttributionIntroClose(prop.getProperty("backend_beta_url"), vendorAuthToken, payload);
			
		} else if(userNumber.equalsIgnoreCase("first")) {
			
			response = APIRequest.setAttributionIntroClose(prop.getProperty("backend_beta_url"), firstVendorAuthToken, payload);
			
		} else if(userNumber.equalsIgnoreCase("second")) {
			
			response = APIRequest.setAttributionIntroClose(prop.getProperty("backend_beta_url"), secondVendorAuthToken, payload);
			
		}
		
//		logger.info("Response from Create Vendor Attribution API - " + response.getBody().asPrettyString());
		logger.info("Response code from Create Vendor Attribution API - " + response.statusCode());

	}

	@Then("^Network commision should be set with Intro and Close Level for \"([^\"]*)\" vendor$")
	public void network_commision_with_intro_and_close_set(String userNumber) {
		
		if(userNumber.equalsIgnoreCase("")) {
			
			attributionId = response.then().extract().body().jsonPath().get("data.id");
			logger.info("Vendor Attrribution Id - " + attributionId);
			
		} else if(userNumber.equalsIgnoreCase("first")) {
			
			firstVendorAttributionId = response.then().extract().body().jsonPath().get("data.id");
			logger.info("First Vendor Attrribution Id - " + firstVendorAttributionId);
			
		} else if(userNumber.equalsIgnoreCase("second")) {
			
			secondVendorAttributionId = response.then().extract().body().jsonPath().get("data.id");
			logger.info("Second Vendor Attrribution Id - " + secondVendorAttributionId);
			
		}
		
		if(response.getStatusCode() != 400) {
			
			response.then().assertThat().statusCode(200);		
			response.then().assertThat().body("data.attributionConfig.configs.lIntro.percentage", equalTo(50));
			response.then().assertThat().body("data.attributionConfig.configs.lX.percentage", equalTo(50));
			response.then().assertThat().body("data.attributionConfig.configs.networkCommission.percentage", equalTo(30));
			
		}

	}
	
	@Given("^Authorization token of coseller, \"([^\"]*)\" vendor id and currency is obtained$")
	public void set_currency_for_fetch_product(String userNumber) throws IOException {
		
		logger.info(" =========== " + scenarioName + " =========== ");
		currency = prop.getProperty("currency");
		logger.info("Currency set - " + currency);
		
		if(userNumber.equalsIgnoreCase("")) {
			
			logger.info("Searching for products with vendor id  - " + vendorId);
			
		} else if(userNumber.equalsIgnoreCase("first")) {
			
			logger.info("Searching for products with vendor id  - " + firstVendorId);
			
		} else if(userNumber.equalsIgnoreCase("second")) {
			
			logger.info("Searching for products with vendor id  - " + secondVendorId);
			
		}
		
	}
	
	@When("^Fetch product API is hit for \"([^\"]*)\" vendor$")
	public void fetch_product_with_vendor_id(String userNumber) throws IOException, InterruptedException {
		
		Thread.sleep(5000);
		
		if(userNumber.equalsIgnoreCase("")) {
			
			response = APIRequest.fetchProductWithVendorId(prop.getProperty("backend_beta_url"), vendorId, currency);
			
		} else if(userNumber.equalsIgnoreCase("first")) {
			
			response = APIRequest.fetchProductWithVendorId(prop.getProperty("backend_beta_url"), firstVendorId, currency);
			
		} else if(userNumber.equalsIgnoreCase("second")) {
			
			response = APIRequest.fetchProductWithVendorId(prop.getProperty("backend_beta_url"), secondVendorId, currency);
			
		}
		
//		logger.info("Response from Fetch Product API - " + response.getBody().asPrettyString());
		logger.info("Response code from Fetch Product API - " + response.statusCode());
		
	}
	
	@Then("^Get a product id from the list of products for \"([^\"]*)\" vendor$")
	public void get_product_id(String userNumber) throws ParseException {

		response.then().assertThat().statusCode(200);
		List<Map<String, String>> productDetails = new ArrayList<>();
		Random rand = new Random();
		int randomNumber;
		
		Object responseBody = new JSONParser().parse(response.getBody().asString());
		JSONObject jsonResponse = (JSONObject) responseBody;
		JSONArray productList = (JSONArray) jsonResponse.get("products");
		
		for(int i=0; i < productList.size(); i++) {
			
			JSONObject productListJson = (JSONObject) productList.get(i);
			JSONArray variantList = (JSONArray) productListJson.get("variants");
			JSONObject variant = (JSONObject) variantList.get(0); 
			
			if(Integer.parseInt(variant.get("quantity").toString()) > 0) {
				
				HashMap<String, String> productsVariants = new HashMap<String, String>();
				productsVariants.put("id", productListJson.get("id").toString());
				productsVariants.put("variant_id", variant.get("id").toString());
				logger.info("Product Detail - " + productsVariants);
				productDetails.add(productsVariants);
				
			}
			
		}
		
		logger.info("List of Products Available - " + productDetails);
		
		if(userNumber.equalsIgnoreCase("")) {
			
			randomNumber = rand.nextInt(productDetails.size());
			logger.info("Random Number - " + randomNumber);
			productId = productDetails.get(randomNumber).get("id");
			productVariantId = productDetails.get(randomNumber).get("variant_id");
			logger.info("Fetched Product ID - " + productId);
			logger.info("Fetched Product Variant ID - " + productVariantId);
			
		} else if(userNumber.equalsIgnoreCase("first")) {
			
			randomNumber = rand.nextInt(productDetails.size());
			logger.info("Random Number - " + randomNumber);
			firstVendorProductId = productDetails.get(randomNumber).get("id");
			firstVendorProductVariantId = productDetails.get(randomNumber).get("variant_id");
			logger.info("Fetched First Vendor Product ID - " + firstVendorProductId);
			logger.info("Fetched First Vendor Product Variant ID - " + firstVendorProductVariantId);
			
		} else if(userNumber.equalsIgnoreCase("second")) {
			
			randomNumber = rand.nextInt(productDetails.size());
			logger.info("Random Number - " + randomNumber);
			secondVendorProductId = productDetails.get(randomNumber).get("id");
			secondVendorProductVariantId = productDetails.get(randomNumber).get("variant_id");
			
			if(secondVendorProductId.equals(firstVendorProductId)) {
				
				logger.info("First Product ID and Second Product ID are same");
				randomNumber = rand.nextInt(productDetails.size());
				secondVendorProductId = productDetails.get(randomNumber).get("id");
				secondVendorProductVariantId = productDetails.get(randomNumber).get("variant_id");
				
			}
			
			logger.info("Fetched Second Vendor Product ID - " + secondVendorProductId);
			logger.info("Fetched Second Vendor Product Variant ID - " + secondVendorProductVariantId);
			
		}
			
	}
	
	@Given("^Authorization token of coseller and product id from \"([^\"]*)\" vendor is obtained$")
	public void get_coseller_authToken_and_product_id(String userNumber) {
		
		logger.info(" =========== " + scenarioName + " =========== ");
		
		if(userNumber.equalsIgnoreCase("")) {
			
			logger.info("Vendor Product ID - " + productId);
			
		} else if(userNumber.equalsIgnoreCase("first")) {
			
			logger.info("First Vendor Product ID - " + firstVendorProductId);
			
		} else if(userNumber.equalsIgnoreCase("second")) {
			
			logger.info("Second Vendor Product ID - " + secondVendorProductId);
			
		}
		
	}
	
	@When("^Get Publish Slug API is hit with \"([^\"]*)\" vendor product id$") 
	public void get_publish_slug_api(String userNumber) throws IOException {
		
		if(userNumber.equalsIgnoreCase("")) {
			
			response = APIRequest.getPublishSlug(prop.getProperty("backend_beta_url"), cosellerAuthToken, productId);
			
		} else if(userNumber.equalsIgnoreCase("first")) {
			
			response = APIRequest.getPublishSlug(prop.getProperty("backend_beta_url"), cosellerAuthToken, firstVendorProductId);
			
		} else if(userNumber.equalsIgnoreCase("second")) {
			
			response = APIRequest.getPublishSlug(prop.getProperty("backend_beta_url"), cosellerAuthToken, secondVendorProductId);
			
		}
		
//		logger.info("Response from Get Publish Slug API - " + response.getBody().asPrettyString());
		logger.info("Response code from Get Publish Slug API - " + response.statusCode());
		
	}
	
	@Then("^Get the publish slug url for \"([^\"]*)\" vendor product$")
	public void get_publish_slug_url(String userNumber) {
		
		response.then().assertThat().statusCode(200);
		
		if(userNumber.equalsIgnoreCase("")) {
			
			publishSlug = response.then().extract().body().jsonPath().get("slug");
			trackerId = response.then().extract().body().jsonPath().get("trackerId");
			logger.info("Fetched Publish Slug URL - " + publishSlug);
			logger.info("Fetched Tracker Id - " + trackerId);
			
		} if(userNumber.equalsIgnoreCase("first")) {
			
			firstVendorPublishSlug = response.then().extract().body().jsonPath().get("slug");
			firstVendorTrackerId = response.then().extract().body().jsonPath().get("trackerId");
			logger.info("Fetched Publish Slug URL for first vendor product - " + firstVendorPublishSlug);
			logger.info("Fetched Tracker Id for first vendor - " + firstVendorTrackerId);
			
		} else if(userNumber.equalsIgnoreCase("second")) {
			
			secondVendorPublishSlug = response.then().extract().body().jsonPath().get("slug");
			secondVendorTrackerId = response.then().extract().body().jsonPath().get("trackerId");
			logger.info("Fetched Publish Slug URL for second vendor product - " + secondVendorPublishSlug);
			logger.info("Fetched Tracker Id for second vendor - " + secondVendorTrackerId);
			
		}
				
	}
	
	@Given("^Authorization token, \"([^\"]*)\" vendor id, coseller id, product id and link is obtained$")
	public void get_publish_product_payload(String userNumber) throws IOException {
		
		logger.info(" =========== " + scenarioName + " =========== ");

		logger.info("Picking product url in random");

		if (userNumber.equalsIgnoreCase("")) {

			publishProductUrl = "https://beta.shoptype.com" + publishSlug;
			logger.info("Publish Product Url - " + publishProductUrl);
			payload = Payload.publishProduct(vendorId, cosellerUserId.replace("\"", ""), publishProductUrl, productId);

		} else if (userNumber.equalsIgnoreCase("first")) {

			firstVendorPublishProductUrl = "https://beta.shoptype.com" + firstVendorPublishSlug;
			logger.info("Publish Product Url - " + firstVendorPublishProductUrl);
			payload = Payload.publishProduct(firstVendorId, cosellerUserId.replace("\"", ""),
					firstVendorPublishProductUrl, firstVendorProductId);

		} else if (userNumber.equalsIgnoreCase("second")) {

			secondVendorPublishProductUrl = "https://beta.shoptype.com" + secondVendorPublishSlug;
			logger.info("Publish Product Url - " + secondVendorPublishProductUrl);
			payload = Payload.publishProduct(secondVendorId, cosellerUserId.replace("\"", ""),
					secondVendorPublishProductUrl, secondVendorProductId);

		}

		logger.info("Payload - " + payload);
		
	}
	
	@When("^Publish product API is hit$")
	public void publish_product() throws IOException {
		
		APIRequest.publishProduct(prop.getProperty("backend_beta_url"), cosellerAuthToken, payload);
		
	}
	
	@Then("^Publish should be recorded for that coseller$")
	public void check_publish_recorded_for_coseller() throws IOException {
		
		response = APIRequest.getCosellerView(prop.getProperty("backend_beta_url"), cosellerAuthToken, "cosellerView", currency);
		logger.info("Response code from Coseller View API - " + response.statusCode());
		logger.info("Response from Coseller View API - " + response.body().asPrettyString());
		
		response.then().assertThat().body("total_publishes", greaterThan(0));
		
	}
	
	@Given("^Device id, tracker id and url is obtained for \"([^\"]*)\" vendor product$")
	public void get_tracker_id_and_url(String userNumber) {
		
		logger.info(" =========== " + scenarioName + " =========== ");
		
		if(userNumber.equalsIgnoreCase("")) {
			
			payload = Payload.createUserEvent(deviceId, publishProductUrl, trackerId);
			
		} else if(userNumber.equalsIgnoreCase("first")) {
			
			logger.info("Tracker ID - " + firstVendorTrackerId);
			payload = Payload.createUserEvent(deviceId, firstVendorPublishProductUrl, firstVendorTrackerId);
			
		} else if(userNumber.equalsIgnoreCase("second")) {
			
			logger.info("Tracker ID - " + secondVendorTrackerId);
			payload = Payload.createUserEvent(deviceId, secondVendorPublishProductUrl, secondVendorTrackerId);
			
		}
		
		logger.info("Payload - " + payload);
		
	}
	
	@When("^Track user event api is hit$")
	public void track_user_event() {
		
		response = APIRequest.createUserEvent(prop.getProperty("backend_beta_url"), payload);
		logger.info("Response code from Create User Event API - " + response.statusCode());
//		logger.info("Response from Create User Event API - " + response.getBody().asPrettyString());
		
	}
	
	@Then("^Number of clicks should be recorded for coseller$")
	public void record_user_event_and_click_event() {
		
		response.then().assertThat().statusCode(200);
		response = APIRequest.getCosellerView(prop.getProperty("backend_beta_url"), cosellerAuthToken, "cosellerView", currency);
		response.then().assertThat().statusCode(200);
		logger.info("Response code from Coseller View API - " + response.statusCode());
		logger.info("Response from Coseller View API - " + response.body().asPrettyString());
		
		response.then().assertThat().body("total_clicks", greaterThan(0));
		
	}
	
	@Given("^Shoptype api key is obtained$")
	public void get_shoptype_api_key() throws IOException {
		
		logger.info(" =========== " + scenarioName + " =========== ");
		shoptypeApiKey = prop.getProperty("shoptype_api_key");
		logger.info("Shoptype APi Key - " + shoptypeApiKey);
		
	}
	
	@When("^Create empty cart api is hit$")
	public void create_empty_cart_api() {
		
		response = APIRequest.createEmptyCart(prop.getProperty("backend_beta_url"), shoptypeApiKey);
		logger.info("Response code from create empty cart api - " + response.statusCode());
//		logger.info("Response from create empty cart api - " + response.getBody().asPrettyString());
		
	}
	
	@Then("^Empty cart should be created and cart id should be obtained$")
	public void get_cart_id() {
		
		response.then().assertThat().statusCode(200);
		cartId = response.then().extract().body().jsonPath().get("id");
		logger.info("Cart Id - " + cartId);
		
	}
	
	@Given("^Product details of \"([^\"]*)\" vendor are obtained$")
	public void get_product_details(String userNumber) {
		
		logger.info(" =========== " + scenarioName + " =========== ");
		
		if(userNumber.equalsIgnoreCase("")) {
			
			payload = Payload.addItemstoCart(productId, productVariantId);
			
		} else if(userNumber.equalsIgnoreCase("first")) {
			
			payload = Payload.addItemstoCart(firstVendorProductId, firstVendorProductVariantId);
			
		} else if(userNumber.equalsIgnoreCase("second")) {
			
			payload = Payload.addItemstoCart(secondVendorProductId, secondVendorProductVariantId);
			
		}
	
		logger.info("Payload - " + payload);
		
	} 
	
	@When("^Add items to cart api is hit with \"([^\"]*)\" vendor product$")
	public void add_items_to_cart(String userNumber) {
		
		if(userNumber.equalsIgnoreCase("")) {
			
			response = APIRequest.addItemsToCart(prop.getProperty("backend_beta_url"), shoptypeApiKey, cartId, productId, productVariantId);
			
		} else if(userNumber.equalsIgnoreCase("first")) {
			
			response = APIRequest.addItemsToCart(prop.getProperty("backend_beta_url"), shoptypeApiKey, cartId, firstVendorProductId, firstVendorProductVariantId);
			
		} else if(userNumber.equalsIgnoreCase("second")) {
			
			response = APIRequest.addItemsToCart(prop.getProperty("backend_beta_url"), shoptypeApiKey, cartId, secondVendorProductId, secondVendorProductVariantId);
			
		}	

		logger.info("Response code from Add Items to Cart API - " + response.statusCode());
//		logger.info("Response from Add Items to Cart API - " + response.getBody().asPrettyString());
		
	}
	
	@Then("^Item should be added to cart and total quantity should be increased$")
	public void items_should_be_added_to_cart() {
		
		response.then().assertThat().statusCode(200);
		response.then().assertThat().body("total_quantity", greaterThan(0));
		
	}
	
	@Given("^Device Id and Cart id obtained$")
	public void get_device_id_and_cart_id() {
		
		logger.info(" =========== " + scenarioName + " =========== ");
		logger.info("Device Id - " + deviceId);
		payload = Payload.createCheckout(deviceId,  cartId);
		logger.info("Payload - " + payload);
		
	}
	
	@When("^Create Checkout API is hit$")
	public void create_checkout_api() {
		
		response = APIRequest.createCheckout(prop.getProperty("backend_beta_url"), shoptypeApiKey, deviceId, cartId, null);
		logger.info("Response Code from Create Checkout API - " + response.statusCode());
//		logger.info("Response from Create Checkout API - " + response.getBody().asPrettyString());
		
	}
	
	@Then("^Checkout Id and Redirect Uri should be obtained$")
	public void get_checkout_id_and_redirect_uri() {
		
		response.then().assertThat().statusCode(200); 
		checkoutId = response.then().extract().body().jsonPath().get("checkout_id");
		logger.info("Checkout ID - " + checkoutId);
		redirectUri = "beta.shoptype.com" + response.then().extract().body().jsonPath().get("redirect_uri");
		logger.info("Redirect URI - " + redirectUri);
		
	}
		
	public static boolean userSignIn(String userNumber, String userType) throws IOException, InterruptedException {

		isEmailVerified = false;

		if (userType.equalsIgnoreCase("vendor")) {
			
			if(userNumber.equalsIgnoreCase("") ) {
				
				data = Utilities.readExcel("vendor", "1");
				logger.info("Trying to Sign In as Vendor");	
				
			} else if(userNumber.equalsIgnoreCase("first")) {
				
				data = Utilities.readExcel("vendor", "1");
				vendorEmail = data.get("email");
				logger.info("Trying to Sign In as Vendor");
				
			} else if(userNumber.equalsIgnoreCase("second")) {
				
				data = Utilities.readExcel("vendor", "2");
				logger.info("Trying to Sign In as Vendor");
				
			}
			
		} else if (userType.equalsIgnoreCase("coseller")) {

			data = Utilities.readExcel("coseller", "");
			logger.info("Trying to Sign In as Coseller");

		} else if (userType.equalsIgnoreCase("network")) {

			data = Utilities.readExcel("network", "");
			logger.info("Trying to Sign In as Network");

		}

		isEmailVerified = login.signInUser(wait, data);
		logger.info("Email ID - " + data.get("email"));

		return isEmailVerified;

	}

}
