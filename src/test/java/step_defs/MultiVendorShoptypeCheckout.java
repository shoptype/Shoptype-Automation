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
import api_requests.APIRequest;
import api_requests.Payload;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.BaseClass;

public class MultiVendorShoptypeCheckout extends BaseClass {

	protected static final Logger logger = LogManager.getLogger(MultiVendorShoptypeCheckout.class.getName());
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
		
		logger.info("Response code from Fetch Product API - " + response.statusCode());
		if(response.statusCode() != 200) {
			
			logger.info("Response from fetch product API - " + response.getBody().asPrettyString());
			
		}
		
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
		
		logger.info("Response code from Get Publish Slug API - " + response.statusCode());
		if(response.statusCode() != 200) {
			
			logger.info("Response from get publish slug api API - " + response.getBody().asPrettyString());
			
		}
		
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
		
		if(response.statusCode() != 200) {
			
			logger.info("Response from Coseller View API - " + response.getBody().asPrettyString());
			
		}
		
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
		if(response.statusCode() != 200) {
			
			logger.info("Response from Create User Event API - " + response.getBody().asPrettyString());
			
		}
		
	}
	
	@Then("^Number of clicks should be recorded for coseller$")
	public void record_user_event_and_click_event() {
		
		response.then().assertThat().statusCode(200);
		response = APIRequest.getCosellerView(prop.getProperty("backend_beta_url"), cosellerAuthToken, "cosellerView", currency);
		response.then().assertThat().statusCode(200);
		logger.info("Response code from Coseller View API - " + response.statusCode());
		
		if(response.statusCode() != 200) {
			
			logger.info("Response from Coseller View API - " + response.getBody().asPrettyString());
			
		}
		
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
		
		if(response.statusCode() != 200) {
			
			logger.info("Response from create empty  API - " + response.getBody().asPrettyString());
			
		}
		
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
		if(response.statusCode() != 200) {
			
			logger.info("Response from Add Items to Cart  API - " + response.getBody().asPrettyString());
			
		}
		
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
		if(response.statusCode() != 200) {
			
			logger.info("Response from create checkout  API - " + response.getBody().asPrettyString());
			
		}

		
	}
	
	@Then("^Checkout Id and Redirect Uri should be obtained$")
	public void get_checkout_id_and_redirect_uri() {
		
		response.then().assertThat().statusCode(200); 
		checkoutId = response.then().extract().body().jsonPath().get("checkout_id");
		logger.info("Checkout ID - " + checkoutId);
		redirectUri = "beta.shoptype.com" + response.then().extract().body().jsonPath().get("redirect_uri");
		logger.info("Redirect URI - " + redirectUri);
		
	}
	
}
