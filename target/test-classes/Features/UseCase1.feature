#Author: rahul.syan@hcl.com
#Project: SDET Capstone Project
#Use Case: 1

@UseCase1
Feature: Use Case 1
 	Case Study on : https://www.hcltech.com/​

  @UseCase1 @VerifyMenu
  Scenario: Verify the Menu items available on HCLTech Home page
    Given User is on "useCase1URL" Home page 
    When User reads the Menu names on the home page
    Then User validates the menu names
    	|Digital;Engineering;Cloud;AI;Services;Industries;Ecosystem;About Us;Trends and Insights;Careers|
    And Verify the count of the menu items available
    	|10|

  @UseCase1 @MatchScreenshots
  Scenario: Verify the homepage view is as expected
  	Given User is on "useCase1URL" Home page
  	When User takes the screenshot of the page
  	Then Verify the current screenshot with existing screenshot
  	
  @UseCase1 @ContactUs
  Scenario: Navigate to Contact Us page and Validate the fields
  	Given User is on "useCase1URL" Home page
  	When User navigates to "Contact Us" page
  	And User fills all the fields
  	Then user verifies that the file is uploaded successfully