Feature: Vendor Level Attribution ( Intro and Close)

	@user_creation
  Scenario: Vendor Sign Up
    Given User opens the website and signs in as existing user
    When User enters all the required details to signin as "" "Vendor"
    And Clicks on "Vendor"
    Then User should be logged in as "Vendor"
    And Vendor id and user id should be saved for "" vendor
    And Authorization token of "" "Vendor" should be saved
	
	@user_creation
  Scenario: Coseller Sign Up
    Given User opens the website and signs in as existing user
    When User enters all the required details to signin as "" "Coseller"
    And Clicks on "Coseller"
    Then User should be logged in as "Coseller"
    And "Coseller" user id should be saved
    And Authorization token of "" "Coseller" should be saved 
 
  @api
  Scenario: Sync Shopify Products
  	Given Authorization token of "" vendor and the Shopify payload are obtained
  	When Shopify Sync Product API is hit for "" vendor
  	Then Products should added to catalog and the store should be synced
 
  @api	
  Scenario: Setting Vendor Attribution for Intro and Close
  	Given Authorization token of "" vendor and attribution payload for setting intro and close is obtained
  	When Create Vendor Attribution API is hit for "" vendor
  	Then Network commision should be set with Intro and Close Level for "" vendor
  
  #@api
  #Scenario: Setting Vendor Attribution for Intro, L1 and Close
  #	Given Authorization token and attribution payload for setting intro, l1 and close is obtained
  #	When Update Attribution API is hit
  #	Then Network commision should be set with Intro, L1 and Close Level
  	
  @api
  Scenario: Get product id to publish
		Given Authorization token of coseller, "" vendor id and currency is obtained
  	When Fetch product API is hit for "" vendor
  	Then Get a product id from the list of products for "" vendor
  	
  @api
	Scenario: Get publish slug url
		Given Authorization token of coseller and product id from "" vendor is obtained
		When Get Publish Slug API is hit with "" vendor product id
		Then Get the publish slug url for "" vendor product
		
	@api
	Scenario: Publish the product
		Given Authorization token, "" vendor id, coseller id, product id and link is obtained
		When Publish product API is hit
		Then Publish should be recorded for that coseller
		
	@api
	Scenario: Track User Event and Create Click Event 
		Given Device id, tracker id and url is obtained for "" vendor product
		When Track user event api is hit 
		Then Number of clicks should be recorded for coseller
	
	@api
	Scenario: Create Cart
		Given Shoptype api key is obtained  
		When Create empty cart api is hit
		Then Empty cart should be created and cart id should be obtained

	@api
	Scenario: Add items to cart
		Given Product details of "" vendor are obtained  
		When Add items to cart api is hit with "" vendor product
		Then Item should be added to cart and total quantity should be increased

	@api
	Scenario: Create Checkout
		Given Device Id and Cart id obtained   
		When Create Checkout is hit
		Then Checkout Id and Redirect Uri should be obtained

	Scenario: Add the checkout url to property file
		Given Checkout Url is obtained 
		Then "Vendor Attribution ( Intro and Close )" Checkout Url should be written to file
		