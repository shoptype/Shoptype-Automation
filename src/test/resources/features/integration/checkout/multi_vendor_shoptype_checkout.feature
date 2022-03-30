Feature: Multi Vendor Checkout on Shoptype

	@api 
	Scenario: Vendor One Sign In 
		Given User logs in as "Vendor One"
		When User selects "Vendor One" as a profile
		Then user should be logged in as "Vendor One"
	
	@api 
	Scenario: Vendor Two Sign In 
		Given User logs in as "Vendor Two"
		When User selects "Vendor Two" as a profile 
		Then user should be logged in as "Vendor Two"
	
	@api 
	Scenario: Coseller Sign In 
		Given User logs in as "Coseller"
		When User selects "Coseller" as a profile 
		Then user should be logged in as "Coseller"
  
  @api
  Scenario: Get product id to publish from first vendor
  	Given Authorization token of coseller, "First" vendor id and currency is obtained
  	When Fetch product API is hit for "First" vendor
  	Then Get a product id from the list of products for "First" vendor

  @api
  Scenario: Get product id to publish from second vendor
  	Given Authorization token of coseller, "Second" vendor id and currency is obtained
  	When Fetch product API is hit for "Second" vendor
  	Then Get a product id from the list of products for "Second" vendor
 	
 	@api
	Scenario: Get publish slug url for product from first vendor
		Given Authorization token of coseller and product id from "First" vendor is obtained
		When Get Publish Slug API is hit with "First" vendor product id
		Then Get the publish slug url for "first" vendor product
 	
 	@api
	Scenario: Get publish slug url for product from second vendor
		Given Authorization token of coseller and product id from "Second" vendor is obtained
		When Get Publish Slug API is hit with "Second" vendor product id
		Then Get the publish slug url for "second" vendor product
	
	@api
	Scenario: Publish the product from first vendor
		Given Authorization token, "First" vendor id, coseller id, product id and link is obtained
		When Publish product API is hit
		Then Publish should be recorded for that coseller
	
	@api
	Scenario: Publish the product from second vendor
		Given Authorization token, "Second" vendor id, coseller id, product id and link is obtained
		When Publish product API is hit
		Then Publish should be recorded for that coseller

	@api
	Scenario: Track User Event and Create Click Event for first vendor product
		Given Device id, tracker id and url is obtained for "First" vendor product 
		When Track user event api is hit
		Then Number of clicks should be recorded for coseller
	
	@api
	Scenario: Track User Event and Create Click Event for second vendor product
		Given Device id, tracker id and url is obtained for "Second" vendor product 
		When Track user event api is hit 
		Then Number of clicks should be recorded for coseller	

	@api
	Scenario: Create Cart
		Given Shoptype api key is obtained  
		When Create empty cart api is hit
		Then Empty cart should be created and cart id should be obtained

	@api
	Scenario: Add first vendor items to cart
		Given Product details of "First" vendor are obtained  
		When Add items to cart api is hit with "First" vendor product
		Then Item should be added to cart and total quantity should be increased

	@api
	Scenario: Add second vendor items to cart
		Given Product details of "second" vendor are obtained  
		When Add items to cart api is hit with "Second" vendor product
		Then Item should be added to cart and total quantity should be increased		

	@api
	Scenario: Create Checkout
		Given Device Id and Cart id obtained   
		When Create Checkout API is hit
		Then Checkout Id and Redirect Uri should be obtained

	@ui
	Scenario: Checkout on shoptype
		Given Checkout Url is obtained
		When A checkout happens
		Then The order should be placed

	@api
	Scenario: Check for taxes at vendor and product level
		Given Checkout id has been obtained 
		When Checkout details for that order is requested 
		Then Response should contain the shipping and taxes both at vendor and product level 
