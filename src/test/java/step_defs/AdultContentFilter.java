package step_defs;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.Assert;

import api_requests.APIRequest;
import api_requests.Payload;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.BaseClass;

public class AdultContentFilter extends BaseClass {
	
	private static final Logger logger = LogManager.getLogger(AdultContentFilter.class);
	
	@When("Import product from shopify api is hit with adult content filter")
	public void import_product_from_shopify_api_is_hit_with_adult_content_filter() {
	    
		payload = Payload.syncShopifyProducts("true", "false", prop.getProperty("shopifyAdminAccessToken"));
		logger.info("Payload for syncing shopify store - " + payload);
	    
		response = APIRequest.syncShopifyStore(prop.getProperty("backend_beta_url"), automationVendorToken, payload);
		logger.info("Response code from sync shopify API - " + response.statusCode());
		if(response.statusCode() != 200) {
			
			logger.info("Response from sync shopify  API - " + response.getBody().asPrettyString());
			
		}
		
	}
		
	@Then("Only adult content filter should be updated")
	public void only_adult_content_filter_should_be_updated() {
	    
		response = APIRequest.getVendorProfileDetails(prop.getProperty("backend_beta_url"), automationVendorToken);
		logger.info("Response code from get vendor profile API - " + response.statusCode());
		if(response.statusCode() != 200) {
			
			logger.info("Response from get vendor profile API - " + response.getBody().asPrettyString());
			
		}
		response.then().assertThat().statusCode(200);
		
		Assert.assertEquals(response.then().extract().body().jsonPath().get("vendors[0].restrictions.isAdult"), "true");
		Assert.assertEquals(response.then().extract().body().jsonPath().get("vendors[0].restrictions.isAgeRestricted"), "false");
		
	}
	
	@When("Search vendor api is hit with adult content filter")
	public void search_vendor_api_is_hit_with_adult_content_filter() {
	    		
		response = APIRequest.getVendorsFromCoseller(prop.getProperty("backend_beta_url"), "true:false,true", "isAdult:isAgeRestricted,showDefault");
		logger.info("Response code from search vendors from coseller API - " + response.statusCode());
		if(response.statusCode() != 200) {
			
			logger.info("Response from search vendors from coseller API - " + response.getBody().asPrettyString());
			
		}
		response.then().assertThat().statusCode(200);
		
		Assert.assertTrue("Vendor Id not found", response.getBody().asPrettyString().contains(automationVendorId));
		logger.info("Vendor Id found with given restriction");
		
	}
	
	@Then("Coseller should be able to see the list of products with {string} restriction value")
	public void coseller_should_be_able_to_see_the_list_of_products(String restrictionValue) {
	    
		response = APIRequest.getProductsForVendorFromCoseller(prop.getProperty("backend_beta_url"), automationCosellerToken, restrictionValue, "isAdult:isAgeRestricted,showDefault", automationVendorId);
		logger.info("Response code from get products for a vendor from coseller API - " + response.statusCode());
		if(response.statusCode() != 200) {
			
			logger.info("Response from get products for a vendor from coseller API - " + response.getBody().asPrettyString());
			
		}
		response.then().assertThat().statusCode(200);
		logger.info("Products found for the given vendor ID - " + automationVendorId);
		
	}
	
	@When("Import product from shopify api is hit with age restricted adult content filter")
	public void import_product_from_shopify_api_is_hit_with_age_restricted_adult_content_filter() {
	    
		payload = Payload.syncShopifyProducts("false", "true", prop.getProperty("shopifyAdminAccessToken"));
		logger.info("Payload for syncing shopify store - " + payload);
	    
		response = APIRequest.syncShopifyStore(prop.getProperty("backend_beta_url"), automationVendorToken, payload);
		logger.info("Response code from sync shopify API - " + response.statusCode());
		if(response.statusCode() != 200) {
			
			logger.info("Response from sync shopify API - " + response.getBody().asPrettyString());
			
		}
		
	}
	
	@Then("Only age restricted adult content filter should be updated")
	public void only_age_restricted_adult_content_filter_should_be_updated() {
	    
		response = APIRequest.getVendorProfileDetails(prop.getProperty("backend_beta_url"), automationVendorToken);
		logger.info("Response code from get vendor profile API - " + response.statusCode());
		if(response.statusCode() != 200) {
			
			logger.info("Response from get vendor profile API - " + response.getBody().asPrettyString());
			
		}
		response.then().assertThat().statusCode(200);
		
		Assert.assertEquals(response.then().extract().body().jsonPath().get("vendors[0].restrictions.isAdult"), "false");
		Assert.assertEquals(response.then().extract().body().jsonPath().get("vendors[0].restrictions.isAgeRestricted"), "true");
		
	}
	
	@When("Search vendor api is hit with age restricted filter")
	public void search_vendor_api_is_hit_with_age_restricted_filter() {
	    
		response = APIRequest.getVendorsFromCoseller(prop.getProperty("backend_beta_url"), "false:true,true", "isAdult:isAgeRestricted,showDefault");
		logger.info("Response code from search vendors from coseller api - " + response.statusCode());
		if(response.statusCode() != 200) {
			
			logger.info("Response from search vendors from coseller api - " + response.getBody().asPrettyString());
			
		}
		response.then().assertThat().statusCode(200);
		
		Assert.assertTrue("Vendor Id not found", response.getBody().asPrettyString().contains(automationVendorId));
		logger.info("Vendor Id found with given restriction");
		
	}
	
	@When("Import product from shopify api is hit with age restricted and adult content filter")
	public void import_product_from_shopify_api_is_hit_with_age_restricted_and_adult_content_filter() {
	    
		payload = Payload.syncShopifyProducts("true", "true", prop.getProperty("shopifyAdminAccessToken"));
		logger.info("Payload for syncing shopify store - " + payload);
	    
		response = APIRequest.syncShopifyStore(prop.getProperty("backend_beta_url"), automationVendorToken, payload);
		logger.info("Response code sync shopify API - " + response.statusCode());
		if(response.statusCode() != 200) {
			
			logger.info("Response from sync shopify API - " + response.getBody().asPrettyString());
			
		}
		
	}
	
	@Then("Both age restricted and adult content filter should be updated")
	public void both_age_restricted_and_adult_content_filter_should_be_updated() {
	    
		response = APIRequest.getVendorProfileDetails(prop.getProperty("backend_beta_url"), automationVendorToken);
		logger.info("Response code from get vendor profile API - " + response.statusCode());
		if(response.statusCode() != 200) {
			
			logger.info("Response from get vendor profile API - " + response.getBody().asPrettyString());
			
		}
		response.then().assertThat().statusCode(200);
		
		Assert.assertEquals(response.then().extract().body().jsonPath().get("vendors[0].restrictions.isAdult"), "true");
		Assert.assertEquals(response.then().extract().body().jsonPath().get("vendors[0].restrictions.isAgeRestricted"), "true");
		
	}
	
	@When("Search vendor api is hit with both age restricted and adult content filter")
	public void search_vendor_api_is_hit_with_both_age_restrcited_and_adult_content_filter() {
	    
		response = APIRequest.getVendorsFromCoseller(prop.getProperty("backend_beta_url"), "true,true,true", "isAdult,isAgeRestricted,showDefault");
		logger.info("Response code from search vendors from coseller API - " + response.statusCode());
		if(response.statusCode() != 200) {
			
			logger.info("Response from get vendor profile API - " + response.getBody().asPrettyString());
			
		}
		response.then().assertThat().statusCode(200);
		
		Assert.assertTrue("Vendor Id not found", response.getBody().asPrettyString().contains(automationVendorId));
		logger.info("Vendor Id found with given restriction");
		
	}
	
	@When("Import product from shopify api is hit with no adult content filters are selected")
	public void import_product_from_shopify_api_is_hit_with_no_adult_content_filters_are_selected() {
	    
		payload = Payload.syncShopifyProducts("false", "false", prop.getProperty("shopifyAdminAccessToken"));
		logger.info("Payload for syncing shopify store - " + payload);
	    
		response = APIRequest.syncShopifyStore(prop.getProperty("backend_beta_url"), automationVendorToken, payload);
		logger.info("Response code from sync shopify API - " + response.statusCode());
		if(response.statusCode() != 200) {
			
			logger.info("Response from sync shopify API - " + response.getBody().asPrettyString());
			
		}
		
	}
	
	@Then("No adult content filters should be updated")
	public void no_adult_content_filters_should_be_updated() {
	    
		response = APIRequest.getVendorProfileDetails(prop.getProperty("backend_beta_url"), automationVendorToken);
		logger.info("Response code from get vendor profile API - " + response.statusCode());
		if(response.statusCode() != 200) {
			
			logger.info("Response from get vendor profile API - " + response.getBody().asPrettyString());
			
		}
		response.then().assertThat().statusCode(200);
		
		Assert.assertEquals(response.then().extract().body().jsonPath().get("vendors[0].restrictions.isAdult"), "false");
		Assert.assertEquals(response.then().extract().body().jsonPath().get("vendors[0].restrictions.isAgeRestricted"), "false");
		
	}
	
	@When("Search vendor api is hit with no adult content filters")
	public void search_vendor_api_is_hit_with_no_adult_content_filters() {
	    
		response = APIRequest.getVendorsFromCoseller(prop.getProperty("backend_beta_url"), "false:false,true", "isAdult:isAgeRestricted,showDefault");
		response.then().assertThat().statusCode(200);
		
		Assert.assertTrue("Vendor Id not found", response.getBody().asPrettyString().contains(automationVendorId));
		logger.info("Vendor Id found with given restriction");
		
	}
	
	@Then("Coseller should be able to see the list of products with both the filters")
	public void coseller_should_be_able_to_see_list_of_products() {
		
		response = APIRequest.getProductsForVendorFromCoseller(prop.getProperty("backend_beta_url"), automationCosellerToken, "true,true,true", "isAdult,isAgeRestricted,showDefault", automationVendorId);
		logger.info("Response code from search products for vendor from coseller API - " + response.statusCode());
		if(response.statusCode() != 200) {
			
			logger.info("Response from search products for vendor from coseller API - " + response.getBody().asPrettyString());
			
		}
		response.then().assertThat().statusCode(200);
		logger.info("Products found for the given vendor ID - " + automationVendorId);
		
	}
	
}
