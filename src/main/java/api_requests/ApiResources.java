package api_requests;

public enum ApiResources {
	
	Register("/register"), 
	LoginAPI("/login"), 
	AuthAPI("/authenticate"), 
	VendorProfileCreation("/vendors"),
	SyncShopifyStoreAPI("/store/shopify"),
	SyncWoocommerceStoreAPI("/store/woo-commerce"), 
	VendorDetailsAPI("/me"),
	PublishSlug("/track/publish-slug"), 
	PublishProduct("/publish-product"),
	ProductSearch("/products"),
	CreateVendorAttribution("/vendor-attribution"),
	CosellerDashboard("/coseller-dashboard"),
	CreateCart("/cart"),
	CreateUserEvent("/track/user-event"),
	CreateCheckout("/checkout"),
	WooCommercePlaceOrder("/wp-json/wc/v3/orders"),
	AwakeMoneyAccount("/awake-money/accounts"),
	NetworkDetails("/networks"),
	DeleteUser("/user/");

	private String resource;

	ApiResources(String resource) {
		this.resource = resource;
	}

	public String getResource() {
		return resource;

	}

}