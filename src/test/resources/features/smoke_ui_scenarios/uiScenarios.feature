Feature: Smoke UI Scenario
	
	@user_creation
  Scenario: Network Sign Up
    Given User opens the website and signs in as existing user
    When User enters all the required details to signup as "Network"
    And Clicks on "Network"
    And Enters the onboarding details for "Network"
    Then User should be signed in as "Network"

	@user_creation
  Scenario: Vendor Sign Up
    Given User opens the website and signs in as existing user
    When User enters all the required details to signup as "Vendor"
    And Clicks on "Vendor"
    And Enters the onboarding details for "Vendor"
    Then User should be signed in as "Vendor"

	@user_creation
  Scenario: Coseller Sign Up
    Given User opens the website and signs in as existing user
    When User enters all the required details to signup as "Coseller"
    And Clicks on "Coseller"
    Then User should be signed in as "Coseller"

	@user_creation
	Scenario: Add payment and payouts for network
		Given User login using the "network" credentials
		When The Adds payment and payout config for a network
		Then The configurations should be saved

	@user_creation
	Scenario: Approve the payout config from shoptype admin
		Given User login using the "shoptype admin" credentials
		When The admin completes the KYC for that network
		Then The payout config should be approved for that network

	@user_creation
	Scenario: Import product from shopify and add attribution details
		Given User login using the "vendor" credentials
		When The user imports product from shopify
		Then The user should be able to see the products imported from vendor account
		
	@user_creation
	Scenario: Connect network and vendor
		Given User login using the "network" credentials
		When The network sends the connection request to a vendor
		Then The network should be able to see the connected vendor
		When The network searches for the product of that vendor  
		Then The network should be able to see the list of products for that vendor

	@user_creation
	Scenario: Accept network connection from vendor
		Given User login using the "vendor" credentials
		When The vendor accepts the connection request
		Then The network should be added to connection
		When The user removes that network from connection
		Then The network connection should be removed
	
	@user_creation
	Scenario: Search for the vendor products from coseller account 
		Given User login using the "coseller" credentials
		When The coseller searches for the vendor product
		Then The coseller should be able to see the list of products for that vendor
	
#	@user_creation
#	Scenario: Delete the vendor from network account
#		Given User login using the "network" credentials
#		When The user removes that vendor from connection
#		Then The vendor connection should be removed




# ADD ALL THE STEP DEFINITION BEFORE THIS
	
	@api
	Scenario: Delete Network
		Given All the scenarios are executed
		Then Delete "Network" account
	
	@api
	Scenario: Delete Vendor
		Given All the scenarios are executed
		Then Delete "Vendor" account
	
	@api
	Scenario: Delete Coseller
		Given All the scenarios are executed
		Then Delete "Coseller" account
