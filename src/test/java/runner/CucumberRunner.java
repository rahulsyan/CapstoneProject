package runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = "src/test/resources/Features",
				glue="stepDef",
				plugin = {"pretty", "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"},
				tags = "@VerifyImageText",
				dryRun = false)
public class CucumberRunner extends AbstractTestNGCucumberTests{
	
}
