Feature: Email Verification
	
	@user_creation
	Scenario: Check for verify email notification 
		Given A new user comes to shoptype
		When The user signs up
		Then The user should receive a notification regarding "verify email"

	@user_creation
	Scenario: Check for forgot password email notification 
		Given A user clicks on forgot password 
		When The user enters email
		Then The user should receive a notification regarding "reset password"

	@user_creation
	Scenario: Check for verify email notification after automatic coseller registration 
		Given A user has placed an order using email address
		When The user opens the email
		Then The user should receive a notification regarding "auto registered email"

	@user_creation
	Scenario: Check for order confirm notification 
		Given A user has placed an order using email address
		When The user opens the email
		Then The user should receive a notification regarding "order confirmation"

	@user_creation
	Scenario: Check for vendor notification 
		Given A user has placed an order using email address
		When The user opens the vendor email id
		Then The user should receive a notification regarding "vendor orders"

	@user_creation
	Scenario: Check for otp email notification 
		Given A user wants to cosell a product
		When The user clicks on cosell and registers as a  new user
		Then The user should receive a notification regarding "otp verification"

	@user_creation
	Scenario: Check for referral email
		Given A user wants to refer a person to shoptype
		When The user sends an invite
		Then The user should receive a notification regarding "referral"		
		