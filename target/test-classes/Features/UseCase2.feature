#Author: rahul.syan@hcl.com
#Project: SDET Capstone Project
#Use Case: 2

@UseCase2
Feature: Use Case 2
 	Case Study on : https://www.hcltech.com/hcl-foundation

  @UseCase2 @VerifyText
  Scenario: Save the HCL foundation page text
	  Given User is on "useCase2URL" Home page
  	When User saves the paragraph text
  	Then Verify that the text matches with UI
  	
  	
   @UseCase2 @VerifyImageText
  Scenario: Verify HCL foundation page text from Screenshot
	  Given User is on "useCase2URL" Home page
  	When User takes the screenshot of the Element
  	Then Verify that the screenshpt text matches with text file