package stepDef;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pageFunctions.UseCase2PO;

public class UseCase2 {
	
	UseCase2PO functions = new UseCase2PO();
	
	@When("User saves the paragraph text")
	public void user_saves_the_paragraph_text() {
	    functions.saveTextFile();
	}
	
	
	@Then("Verify that the text matches with UI")
	public void verify_that_the_text_matches_with_ui() {
	    functions.verifyTextFromFile();
	}
	
	@When("User takes the screenshot of the Element")
	public void user_takes_the_screenshot_of_the_element() {
	    functions.getElementScreenshot();
	}
	
	@Then("Verify that the screenshpt text matches with text file")
	public void verify_that_the_screenshpt_text_matches_with_text_file() {
	    functions.verifyImageFromText();
	}
	
}
