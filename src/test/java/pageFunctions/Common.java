package pageFunctions;

import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.List;
import java.util.Properties;
import javax.imageio.ImageIO;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.sikuli.script.Finder;
import org.sikuli.script.Image;
import org.sikuli.script.Match;
import org.sikuli.script.Pattern;
import org.testng.Assert;
import io.github.bonigarcia.wdm.WebDriverManager;

public class Common {
public static WebDriver driver;
static Properties propFile = new Properties();
static Properties locators = new Properties();

	public Common() {
			try {
				propFile.load(new FileReader("src\\test\\resources\\config.properties"));
				locators.load(new FileReader("src\\test\\resources\\UseCase1.properties"));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	public static By getAcceptCookiesButton() {
		return By.xpath(getProperty(locators, "home_AcceptCookies"));
	}
	
	public static void createInstance(String browserType) {
		try {
			switch (browserType.toLowerCase()) {
			case "chrome":
				System.out.println("----------------Inside Chrome----------------");
				WebDriverManager.chromedriver().setup();
				ChromeOptions options = new ChromeOptions();
				options.addArguments("--remote-allow-origins=*");
				driver = new ChromeDriver(options);
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
				break;
			case "edge":
				WebDriverManager.edgedriver().setup();
				driver = new EdgeDriver();
				break;
				
			case "firefox":
				WebDriverManager.firefoxdriver().setup();
				driver = new FirefoxDriver();
				break;
			default:
				WebDriverManager.iedriver().setup();
				driver = new InternetExplorerDriver();
				break;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public static WebDriver getDriver() {
		return driver; 
	}
	
	public static void loadBrowserAndNavigate(String URL) {
		try {
			Common.createInstance(getProperty(propFile, "browser"));
			driver.manage().window().maximize();
			System.out.println(URL);
			driver.get(getProperty(propFile,URL));
			if(getProperty(propFile,URL).contains("hcltech")) {
				Thread.sleep(2000);
				clickElement(getAcceptCookiesButton());
			}
		}catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Could not load browser!!!");
		}
	}
		
	public static void clickElement(By element) {
		try {
			highlightElement(element);
			driver.findElement(element).click();
			unHighlightElement(element);
		}catch (Exception e) {
			e.getMessage();
		}
	}
	
	public static void waitForElementClickable(By element) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofMillis(5000));
			wait.until(ExpectedConditions.elementToBeClickable(element));
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void highlightElement(By element) {
		try {
			waitForElementClickable(element);
			JavascriptExecutor jsExec = (JavascriptExecutor)driver;
			jsExec.executeScript("arguments[0].style.background='Yellow'", driver.findElement(element));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static  void unHighlightElement(By element) {
		try {
			waitForElementClickable(element);
			JavascriptExecutor jsExec = (JavascriptExecutor)driver;
			jsExec.executeScript("arguments[0].style.background=''", driver.findElement(element));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void typeValue(By element, String value) {
		try {
			highlightElement(element);
			driver.findElement(element).sendKeys(value);
			unHighlightElement(element);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void typeValueWithAction(By element, String value, String action) {
		try {
			highlightElement(element);
			switch (action.toLowerCase()) {
			case "tab":
				driver.findElement(element).sendKeys(value+Keys.TAB);
				break;
			case "enter":
				driver.findElement(element).sendKeys(value+Keys.ENTER);
				break;
			default:
				System.out.println("Entering only value as action is undefined");
				driver.findElement(element).sendKeys(value);
				break;
			}
			unHighlightElement(element);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void selectFromDropdown(By element, String value, String selectBy) {
		try {
			waitForElementClickable(element);
			Select select = new Select(driver.findElement(element));
			switch (selectBy.toLowerCase()) {
			case "index":
				select.selectByIndex(Integer.valueOf(value));
				break;
			case "value":
				select.selectByValue(value);
				break;
			case "visibleText":
				select.selectByVisibleText(value);
				break;
			default:
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void searchAndSelectValue(By element, String searchKey, String valueToSelect) {
		highlightElement(element);
		driver.findElement(element).sendKeys(searchKey);
		
		List<WebElement> ele = driver.findElements(By.xpath("//*[contains(*,'"+valueToSelect+"')]"));
		for (WebElement li : ele) {
			li.click();
		}
	}
	
	@SuppressWarnings("finally")
	public static String getProperty(Properties prop, String key) {
		String value="";
		try {
			value = prop.getProperty(key);
			if(value.contains("{{XS}}"))
				value=value.replace("{{XS}}", getProperty(propFile,"suffix"));
			return value;
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			return value;
		}
	}
	
	public static void fileUploadByFileSelector(String filePath) {
		try {
			StringSelection str = new StringSelection(filePath);
			
			// copying File path to Clipboard
			Toolkit.getDefaultToolkit().getSystemClipboard().setContents(str, null);
			Robot bot = new Robot();
			
			Thread.sleep(5000);
		    // press Control+V for pasting
		    bot.keyPress(KeyEvent.VK_CONTROL);
		    bot.keyPress(KeyEvent.VK_V);
		 
		    // release Control+V for pasting
		    bot.keyRelease(KeyEvent.VK_CONTROL);
		    bot.keyRelease(KeyEvent.VK_V);
		    
		    Thread.sleep(5000);
		    // for pressing and releasing Enter
		    bot.keyPress(KeyEvent.VK_ENTER);
		    bot.keyRelease(KeyEvent.VK_ENTER);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void selectCheckbox(By element) {
		try {
			if(!driver.findElement(element).isSelected()) {
				clickElement(element);
			}
			else {
				System.out.println("Checkbox already Checked");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void unSelectCheckbox(By element) {
		try {
			if(driver.findElement(element).isSelected()) {
				clickElement(element);
			}
			else {
				System.out.println("Checkbox already un-Checked");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void addScreenshot(io.cucumber.java.Scenario scenario) {
		try {
			final byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
			scenario.attach(screenshot, "image/png", scenario.getName());
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void takeElementScreenshot(By element, String screenshotFilePath) {
		try {
			System.out.println(screenshotFilePath);
			String fileFormat= screenshotFilePath.split("\\.")[1].toString();
			WebElement ele = driver.findElement(element);
			
			// Get entire page screenshot
			File screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			BufferedImage  fullImg = ImageIO.read(screenshot);
			
			Point point = ele.getLocation();

			// Get width and height of the element
			int eleWidth = ele.getSize().getWidth();
			int eleHeight = ele.getSize().getHeight();

			System.out.println(eleWidth + "   "+ eleHeight);
			// Crop the entire page screenshot to get only element screenshot
			BufferedImage eleScreenshot= fullImg.getSubimage(point.getX(), point.getY(), eleWidth, eleHeight);
			ImageIO.write(eleScreenshot, "png" , screenshot);

			// Copy the element screenshot to disk
			//File partialScreenshot = new File(screenshotFilePath);
			System.out.println(System.getProperty("user.dir")+screenshotFilePath);
			Path partialScreenshot = Paths.get(System.getProperty("user.dir")+screenshotFilePath);
			FileUtils.copyFile(screenshot, partialScreenshot.toFile());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static Boolean comparePartialScreenshot(String expectedFilePath, String actualFilePath) {
		try {
			Pattern searchImage = new Pattern(System.getProperty("user.dir")+actualFilePath).similar((float)0.9);
			String ScreenImage = System.getProperty("user.dir")+expectedFilePath; //In this case, the image you want to search
			Finder objFinder = null;
			Match objMatch = null;
			objFinder = new Finder(Image.create(ScreenImage));
			objFinder.find(searchImage); //searchImage is the image you want to search within ScreenImage
			int counter = 0;
			while(objFinder.hasNext())
			{
			    objMatch = objFinder.next(); //objMatch gives you the matching region.
			    counter++;
			}
			if(counter!=0)
				System.out.println("Match Found!");
			else
				System.out.println("Match not found");
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			// TODO: handle finally clause
		}
		return true;
	}
	
	public static void scrollToElement(By element) {
		try {
			waitForElementClickable(element);
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(element));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}