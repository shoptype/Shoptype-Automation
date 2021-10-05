package api_requests;

import utilities.Utilities;

public class Payload {
	
	public static String setAttribution_Intro_Close(String vendorId) {
		
		return "{\r\n"
				+ "  \"attributionConfig\": {\r\n"
				+ "    \"configs\": {\r\n"
				+ "      \"networkCommission\": {\r\n"
				+ "        \"percentage\": 30\r\n"
				+ "      },\r\n"
				+ "      \"lIntro\": {\r\n"
				+ "        \"percentage\": 50,\r\n"
				+ "        \"timeLimit\": null,\r\n"
				+ "        \"timeLimitUnit\": \"infinite\"\r\n"
				+ "      },\r\n"
				+ "      \"lX\": {\r\n"
				+ "        \"percentage\": 50,\r\n"
				+ "        \"timeLimit\": null,\r\n"
				+ "        \"timeLimitUnit\": \"infinite\"\r\n"
				+ "      }\r\n"
				+ "    }\r\n"
				+ "  },\r\n"
				+ "  \"vendorId\": \"" + vendorId + "\"\r\n"
				+ "}";
		
	}
	
	public static String setAttribution_Intro_L1_Close(String vendorId) {
		
		return "{\r\n"
				+ "  \"attributionConfig\": {\r\n"
				+ "    \"configs\": {\r\n"
				+ "      \"networkCommission\": {\r\n"
				+ "        \"percentage\": 40\r\n"
				+ "      },\r\n"
				+ "      \"lIntro\": {\r\n"
				+ "        \"percentage\": 50,\r\n"
				+ "        \"timeLimit\": null,\r\n"
				+ "        \"timeLimitUnit\": \"infinite\"\r\n"
				+ "      },\r\n"
				+ "      \"l1\": {\r\n"
				+ "        \"percentage\": 20,\r\n"
				+ "        \"timeLimit\": null,\r\n"
				+ "        \"timeLimitUnit\": \"infinite\"\r\n"
				+ "      },\r\n"
				+ "      \"lX\": {\r\n"
				+ "        \"percentage\": 30,\r\n"
				+ "        \"timeLimit\": null,\r\n"
				+ "        \"timeLimitUnit\": \"infinite\"\r\n"
				+ "      }\r\n"
				+ "    }\r\n"
				+ "  },\r\n"
				+ "  \"vendorId\": \"" + vendorId + "\"\r\n"
				+ "}";
		
	}
	
	public static String syncShopifyProducts() {
		return "{\n"
				+ "  \"storeName\": \"shop-types\",\n"
				+ "  \"apiKey\": \"06f9d72b9670dcc715e5b0e42768b37d\",\n"
				+ "  \"password\": \"e202eb4a13cae81e8eeed939f0529385\",\n"
				+ "  \"timestamp\": "+Utilities.getUnixEpochTime()+",\n"
				+ "  \"enableCheckoutShoptype\": true\n"
				+ "}";
	}
	
	public static String syncWoocommerceProducts() {
		return "{\r\n"
				+ "  \"storeName\": \"https://shoptypewoo.wpcomstaging.com\",\r\n"
				+ "  \"storeHostUrl\": \"https://shoptypewoo.wpcomstaging.com\",\r\n"
				+ "  \"consumerKey\": \"ck_b22be12d33b3bee1365fb2776aaff11d6c9d7c9a\",\r\n"
				+ "  \"consumerSecret\": \"cs_11d03e4028aaec811ef45dd1b246250e030fb517\",\r\n"
				+ "  \"dokan_vendor_id\": \"176134914\",\r\n"
				+ "  \"dokan_vendor_name\": \"Gada electronics\",\r\n"
				+ "  \"enableCheckoutShoptype\": true,\r\n"
				+ "  \"shippingServiceId\": \"001\"\r\n"
				+ "}";
		
	}
	
	public static String updateAttributionWithL1Level(String vendorId, String attributionId) {
		
		return "{\r\n"
				+ "  \"attributionConfig\": {\r\n"
				+ "    \"configs\": {\r\n"
				+ "      \"networkCommission\": {\r\n"
				+ "        \"percentage\": 40\r\n"
				+ "      },\r\n"
				+ "      \"lIntro\": {\r\n"
				+ "        \"percentage\": 50,\r\n"
				+ "        \"timeLimit\": null,\r\n"
				+ "        \"timeLimitUnit\": \"infinite\"\r\n"
				+ "      },\r\n"
				+ "      \"l1\": {\r\n"
				+ "        \"percentage\": 20,\r\n"
				+ "        \"timeLimit\": null,\r\n"
				+ "        \"timeLimitUnit\": \"infinite\"\r\n"
				+ "      },\r\n"
				+ "      \"lX\": {\r\n"
				+ "        \"percentage\": 30,\r\n"
				+ "        \"timeLimit\": null,\r\n"
				+ "        \"timeLimitUnit\": \"infinite\"\r\n"
				+ "      }\r\n"
				+ "    }\r\n"
				+ "  },\r\n"
				+ "  \"vendorId\": \"" + vendorId + "\",\r\n"
				+ "  \"id\": \"" + attributionId + "\"\r\n"
				+ "}";
		
	}
	
	public static String publishProduct(String vendorId, String cosellerId, String slug, String productId) {
		
		return "{"
				+ "\"productId\":\"" + productId + "\","
				+ "\"vendorId\":\"" + vendorId + "\","
				+ "\"referrerLink\":\"https://dev.shoptype.com/cosellerproducts\","
				+ "\"link\":\"" + slug + "\","
				+ "\"medium\":\"copy\","
				+ "\"timestamp\":1629109316231,"
				+ "\"cosellerId\":\"" + cosellerId + "\"}";
		
	}
	
	public static String addItemstoCart(String productId, String variant_id) {
		
		return "{\r\n"
				+ "    \"product_id\": \"" + productId + "\",\r\n"
				+ "    \"product_variant_id\": \"" + variant_id + "\",\r\n"
				+ "    \"quantity\": 1\r\n"
				+ "}";
		
	}
	
	public static String createCheckout(String device_id, String cart_id) {
		
		return "{\r\n"
				+ "  \"deviceId\": \"" + device_id + "\",\r\n"
				+ "  \"cartId\": \"" + cart_id + "\"\r\n"
				+ "}";
		
	}
	
	public static String placeOrder(String checkout_id, String variant_id) {
		
		return "{\r\n"
				+ "    \"meta_data\": [\r\n"
				+ "        {\r\n"
				+ "            \"key\": \"checkout_id\",\r\n"
				+ "            \"value\": \"" + checkout_id + "\"\r\n"
				+ "        }\r\n"
				+ "    ],\r\n"
				+ "    \"line_items\": [\r\n"
				+ "        {\r\n"
				+ "            \"product_id\": " + variant_id + ",\r\n"
				+ "            \"quantity\": 1\r\n"
				+ "        }\r\n"
				+ "    ]\r\n"
				+ "}";
		
	}
	
	public static String createUserEvent(String device_id, String url, String tracker_id) {
		
		return "{"
				+ "\"device_id\":\"" + device_id + "\","
				+ "\"url\":\"" + url + "\","
				+ "\"tracker_id\":\"" + tracker_id + "\","
				+ "\"referrer\":\"\""
				+ "}";
		
	}
	
	public static String createUserEventWithPlatformUrl(String device_id, String url, String tracker_id, String platformUrl) {
		
		return "{"
				+ "\"device_id\":\"" + device_id + "\","
				+ "\"url\":\"" + url + "\","
				+ "\"tracker_id\":\"" + tracker_id + "\","
				+ "\"referrer\":\"" + platformUrl + "\""
				+ "}";
		
	}

}
