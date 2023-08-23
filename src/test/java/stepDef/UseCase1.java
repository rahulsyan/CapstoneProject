package stepDef;

import java.util.List;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pageFunctions.Common;
import pageFunctions.UseCase1PO;

public class UseCase1 {
	
	UseCase1PO functions = new UseCase1PO();
	
	@Given("User is on {string} Home page")
	public void user_is_on_home_page(String URL) {
	    System.out.println(URL);
	    Common.loadBrowserAndNavigate(URL); 
	}
	
	@When("User takes the screenshot of the page")
	public void user_takes_the_screenshot_of_the_page() {
	    try {
			functions.saveElementScreenshot();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Then("Verify the current screenshot with existing screenshot")
	public void verify_the_current_screenshot_with_existing_screenshot() {
	   System.out.println("Comparing screenshots");
	   functions.doPartialImgComparision();
	}
	
	@When("User reads the Menu names on the home page")
	public void user_reads_the_menu_names_on_the_home_page() {
	    try {
	    	functions.readMenuItems();
	    }catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Then("User validates the menu names")
	public void user_validates_the_menu_names(DataTable table) {
	    try{
	    	List<String> itemList = table.asList(String.class);
	    	String values = itemList.get(0);
	    	functions.validateMenuItems(values);	  	    	
	    }catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Then("Verify the count of the menu items available")
	public void verify_the_count_of_the_menu_items_available(DataTable table) {
	    try {
	    	List<String> itemList = table.asList(String.class);
	    	String count = itemList.get(0);
			functions.menuCount(Integer.valueOf(count));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@When("User navigates to {string} page")
	public void user_navigates_to_page(String page) {
	    try {
			functions.navigateToPage(page);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@When("User fills all the fields")
	public void user_fills_all_the_fields() {
	    try {
	    	functions.submitRequestForServices();
	    }catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Then("user verifies that the file is uploaded successfully")
	public void user_verifies_that_the_file_is_uploaded_successfully() {
	    try {
	    	functions.verifyFileUploadStatus();
	    }catch (Exception e) {
			e.printStackTrace();
		}
	}
}
