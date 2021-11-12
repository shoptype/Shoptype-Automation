# Shoptype-Automation

# Resources required to run the test cases

1. Java ( > 1.8 )
2. Maven ( latest )

# Credentials required to run the test cases

1. Navigate to Shoptype-Automation/user_creds
2. Open the Excel file
3. Add the credentials in the same the order as mentioned
4. If you need specific products to be checkout add them in "Specific Product Sheet" 
5. If you need list of products to be checked out, please add the urls in the "List of Products Sheet"
# Please do not change the order in which the credentials are mentioned

# Running the automation scripts

1. cd Shoptype-Automation
2. mvn clean
3. mvn test

# Adding scenario's

1. Navigate to src/test/resources/features
2. Add a new feature ( or ) edit the existing the feature files to add scenarios

# Add step definitions

1. Navigate to src/test/java/step_defs
2. Add a new step definition file ( or ) edit the existing ones to add step definition

# Test Results

Based on the feature file, the checkout urls will be written to src/main/resources/props/checkoutUrls.properties file
