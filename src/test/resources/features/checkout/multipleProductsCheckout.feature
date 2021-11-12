Feature: Mulitple products checkout

	Scenario: Checkout multiple products in a single cart
		Given The list of products are obtained
		When All the products are added to cart
		And A checkout happens
		Then The order should be placed
