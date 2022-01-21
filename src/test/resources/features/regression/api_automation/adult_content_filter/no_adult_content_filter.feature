Feature: No Adult Content Filter Set

	@api
  Scenario: Vendor Sign Up
    Given User signs up as "Vendor"
    When User chooses "Vendor" as a profile
    And Enters the onboarding details for "Vendor"
    Then User should be signed in as "Vendor"

	@api
  Scenario: Coseller Sign Up
    Given User signs up as "Coseller"
    When User chooses "Vendor" as a profile
    Then User should be signed in as "Coseller"

	@api
	Scenario: Import product from shopify with no adult content filters
		Given "Vendor" auth token is obtained
		When Import product from shopify api is hit with no adult content filters are selected
		Then Products should be imported from shopify to the vendor account
		And No adult content filters should be updated
	
	@api
	Scenario: Search for the vendor from coseller account
		Given "Coseller" auth token is obtained
		When Search vendor api is hit with no adult content filters
		Then Coseller should be able to see the list of products with "false:false,true" restriction value
	
	
	
	
	
	
	# ADD ALL THE STEP DEFINITION BEFORE THIS
	
	@api
	Scenario: Delete Vendor
		Given All the scenarios are executed
		Then Delete "Vendor" account
	
	@api
	Scenario: Delete Coseller
		Given All the scenarios are executed
		Then Delete "Coseller" account
	
