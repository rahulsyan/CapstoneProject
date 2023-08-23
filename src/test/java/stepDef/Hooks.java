package stepDef;

import java.io.File;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Scenario;
import pageFunctions.Common;
import pageFunctions.UseCase1PO;

public class Hooks extends Common{
	
	@AfterStep
	public void getScenario(Scenario scenario) {
		try{
			if(UseCase1PO.addScreenshot) {
				File screenshot = ((TakesScreenshot) Common.driver).getScreenshotAs(OutputType.FILE);
				byte[] fileContent = FileUtils.readFileToByteArray(screenshot);
				scenario.attach(fileContent, "image/png", "screenshot");
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			UseCase1PO.addScreenshot=false;
		}
	}
	
	@After
	public static void closeBrowser() {
		try {
			driver.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
