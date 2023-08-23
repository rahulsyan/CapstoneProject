package pageFunctions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.nio.charset.Charset;
import java.util.Properties;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;

public class UseCase2PO extends Common{
	public static Properties locators = new Properties();
	public static Properties propFile = new Properties();
	public static Boolean addScreenshot=false;
	
	////////////////////////////////Constructor to Initialize properties files////////////////////////////////
	public UseCase2PO() {
		try {
			propFile.load(new FileReader("src\\test\\resources\\config.properties"));
			locators.load(new FileReader("src\\test\\resources\\UseCase2.properties"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	////////////////////////////////////Get Elements via Locators/////////////////////////////////////////////
	
	public By getParagraph() {
		return By.xpath(getProperty(locators, "hclFoundation_paragraph"));
	}
	
	public By getParagraphScreenshot() {
		return By.xpath(getProperty(locators, "hclFoundation_textScr"));
	}
	
	
	/////////////////////////////////////Step Definitions Methods/////////////////////////////////////////////
	
	public void saveTextFile() {
		try {
			scrollToElement(getParagraph());
			WebElement para = driver.findElement(getParagraph());
			String text = para.getText();
			String filePath = System.getProperty("user.dir")+getProperty(locators, "hclFoundation_textFile");
			System.out.println(filePath);
			File textFile = new File(filePath);
			FileUtils.writeStringToFile(textFile, text, Charset.defaultCharset());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void verifyTextFromFile() {
		try {
			String filePath = System.getProperty("user.dir")+getProperty(locators, "hclFoundation_textFile");
			FileInputStream inputStream = new FileInputStream(filePath);
			String everything = IOUtils.toString(inputStream);
			System.out.println(everything);
			String textFromUI = driver.findElement(getParagraph()).getText();
			
			Assert.assertEquals(textFromUI, everything);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public void getElementScreenshot() {
		try {
			//scrollToElement(getParagraphScreenshot());
			//Thread.sleep(4000);
			Actions act = new Actions(driver);
			act.scrollByAmount(0, 400).build().perform();
			//act.moveToElement(driver.findElement(getParagraph())).build().perform();
			highlightElement(getParagraphScreenshot());
			String filePath = getProperty(locators, "hclFoundation_textScreenshot");
			takeElementScreenshot(getParagraphScreenshot(), filePath);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void verifyImageFromText() {
		// TODO Auto-generated method stub
		
	}
	
}
