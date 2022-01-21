Feature: Payment and Payout Configuartion
	
	@api
  Scenario: Network Sign Up
    Given User signs up as "Network"
    When User chooses "Network" as a profile
    And Enters the onboarding details for "Network"
    Then User should be signed in as "Network"

	@api
  Scenario: Vendor Sign Up
    Given User signs up as "Vendor"
    When User chooses "Vendor" as a profile
    And Enters the onboarding details for "Vendor"
    Then User should be signed in as "Vendor"
  
  @api
	Scenario: Import product from shopify
		Given "Vendor" auth token is obtained
		When Import product from shopify api is hit
		Then Products should be imported from shopify to the vendor account
	
	@api
	Scenario: Add payment config and payout config
		Given "Network" auth token is obtained
		When Payment and payout details are added for the network
		Then Payment configuration should be saved for that network
		And Payout configuration should be saved for that network

	@api
	Scenario: Accept network connection from vendor
		Given Network sends an invite to vendor 
		When The vendor accepts the connection request
		Then The network should be able to see the added vendor to the connection
		And Vendor should be able to see the network added to connection
	
	@api
	Scenario: Update attribution details
		Given "Vendor" auth token is obtained
		When Update attribution api is hit
		Then Attribution details should be updated for that vendor account






# ADD ALL THE STEP DEFINITION BEFORE THIS
	
	@api
	Scenario: Delete Network
		Given All the scenarios are executed
		Then Delete "Network" account
	
	@api
	Scenario: Delete Vendor
		Given All the scenarios are executed
		Then Delete "Vendor" account
