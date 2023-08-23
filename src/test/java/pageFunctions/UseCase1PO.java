package pageFunctions;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;


public class UseCase1PO extends Common{
	public static Properties locators = new Properties();
	WebDriverWait wait;
	public static Properties propFile = new Properties();
	List<String> menuItems = null;
	public static Boolean addScreenshot=false;

////////////////////////////////Constructor to Initialize properties files////////////////////////////////
	public UseCase1PO() {
		try {
			propFile.load(new FileReader("src\\test\\resources\\config.properties"));
			locators.load(new FileReader("src\\test\\resources\\UseCase1.properties"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
////////////////////////////////////Get Elements via Locators/////////////////////////////////////////////
	public List<WebElement> getMenuItems() {
		return driver.findElements(By.xpath(getProperty(locators,"home_menuItems")));
	}
	public By getContactUsLink() {
		return By.linkText(getProperty(locators,"home_ContactUs"));
	}
	public By getFirstNameField() {
		return By.id(getProperty(locators, "contactUs_FullNameField"));
	}
	public By getBusinessEmailField() {
		return By.id(getProperty(locators, "contactUs_BusinessEmailField"));
	}
	public By getOrgField() {
		return By.id(getProperty(locators, "contactUs_OrganizationField"));
	}
	public By getPhoneField() {
		return By.id(getProperty(locators, "contactUs_PhoneField"));
	}
	public By getCountryField() {
		return By.id(getProperty(locators, "contactUs_CountryField"));
	}
	public By getRelToHCLField() {
		return By.id(getProperty(locators, "contactUs_RelationshipToHCLField"));
	}
	public By getJobTitleField() {
		return By.id(getProperty(locators, "contactUS_JobTitleField"));
	}
	public By getMessageField() {
		return By.id(getProperty(locators, "contactUS_MessageField"));
	}
	public By getFileUploadField() {
		return By.id(getProperty(locators, "contactUS_FileUploadField"));
	}
	public By getPrivacyCheckbox() {
		return By.id(getProperty(locators, "contactUS_PrivacyPolicyCheckbox"));
	}
	public By getFileUploadXpath() {
		return By.xpath(getProperty(locators, "contactUS_FileNameXapth"));
	}
	public By getBannerForScreenshot() {
		return By.xpath(getProperty(locators, "home_Banner"));
	}
/////////////////////////////////////Step Definitions Methods/////////////////////////////////////////////
	
	public void readMenuItems() {
		try {
			Thread.sleep(3000);
			List<WebElement> items = getMenuItems();
			if (items.size()>0) {
				menuItems =  new ArrayList<String>();
				for (WebElement menu : items) {
					menuItems.add(menu.getText());
				}
			}else {
				Assert.fail("Menu Items not present");
			}
			printSubMenuItems();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void printSubMenuItems() {
		String xpathExpandableMenuItems = getProperty(locators, "home_menuItems")+"[contains(@class,'expanded dropdown')]";
		List<WebElement> expandMenu = driver.findElements(By.xpath(xpathExpandableMenuItems));
		Actions action = new Actions(driver);
		int headingCounter = 0;
		int subMenuCounter = 0; 
		if(expandMenu.size()>0) {
			int i=1;
			for (WebElement menu : expandMenu) {
				System.out.println("\nSubMenu Items under Menu : '"+menu.getText()+"'");
				action.clickAndHold(menu).build().perform();
				String xpathSubMenuHeadings = xpathExpandableMenuItems+"["+i+"]/ul//li[contains(@class,'header-submenu-column dropdown-item') or contains(@class,'menu-heading-link dropdown-item')]/a[not(contains(@class,'hide'))]";
				//System.out.println(xpathSubMenuHeadings);
				List<WebElement> subMenuHeadings = driver.findElements(By.xpath(xpathSubMenuHeadings));
				if(subMenuHeadings.size()>0) {
					headingCounter+=subMenuHeadings.size();
					System.out.println("Headings : ");
					for (WebElement headings : subMenuHeadings) {
						System.out.println("\t"+headings.getAttribute("title").trim());
						//Common.highlightElement(headings);
					}
				}
				String xpathSubMenuItems = xpathExpandableMenuItems+"["+i+"]/ul//li[@class='dropdown-item' or @class='header-external-link dropdown-item']/a";
				List<WebElement> subMenuItems = driver.findElements(By.xpath(xpathSubMenuItems));
				if(subMenuItems.size()>0)
				{
					subMenuCounter+=subMenuItems.size();
					System.out.println("\nSubMenu links: ");
					for (WebElement subMenuItem : subMenuItems) {
						System.out.println("\t"+subMenuItem.getAttribute("title").trim());
						//Common.highlightElement(subMenuItem);
					}
				}
				i++;
			}
		}
		System.out.println("\nSub Menu Heading Count: "+headingCounter);
		System.out.println("Sub Menu Items Count: "+subMenuCounter);
	}
	
	public void menuCount(int expCount) {
		try {
			if (menuItems.size() > 0 ) {
				Assert.assertEquals(menuItems.size(), expCount,"Menu Count did not match");
			}else {
				Assert.fail("Menu Items not present");
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void validateMenuItems(String expValues) {
		try {
			String notAvailable="";
	    	String []values = expValues.split(";");
	    	for (String expected : values) {
				if (menuItems.contains(expected.trim())) {
					continue;
				}else {
					notAvailable+=expected+";";
				}
			}
	    	Assert.assertTrue((notAvailable.isEmpty()), "Following Menu items not available: "+notAvailable);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void navigateToPage(String title) {
		try {
			if(title.equals("Contact Us")) {
				waitForElementClickable(getContactUsLink());
				clickElement(getContactUsLink());
			}else {
				driver.navigate().to(title);
			}
				
		}catch (Exception e) {
			e.printStackTrace();
		}
	}


	public void submitRequestForServices() {
		try {
			typeValue(getFirstNameField(),getProperty(locators, "contactUs_FullNameValue"));
			typeValue(getBusinessEmailField(), getProperty(locators, "contactUs_BusinessEmailValue"));
			typeValue(getOrgField(), getProperty(locators, "contactUs_OrganizationValue"));
			typeValue(getPhoneField(),getProperty(locators, "contactUs_PhoneValue"));
			selectFromDropdown(getCountryField(), getProperty(locators, "contactUs_CountryValue"), "Value");
			selectFromDropdown(getRelToHCLField(), getProperty(locators, "contactUs_RelationshipToHCLValue"), "Value");
			selectFromDropdown(getJobTitleField(), getProperty(locators, "contactUS_JobTitleValue"), "Value");
			typeValueWithAction(getMessageField(),getProperty(locators, "contactUS_MessageValue"),"TAB");

			Robot bot = new Robot();
			bot.keyPress(KeyEvent.VK_ENTER);
			bot.keyRelease(KeyEvent.VK_ENTER);
			
			
			File file = new File(getProperty(locators, "contactUS_FileUploadValue"));
			String path = System.getProperty("user.dir")+file.getPath();
			fileUploadByFileSelector(path);
			
			selectCheckbox(getPrivacyCheckbox());
			Thread.sleep(5000);
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void verifyFileUploadStatus() {
		try {
			waitForElementClickable(getFileUploadXpath());
			addScreenshot=true;
		} catch (Exception e) {
			Assert.assertEquals(driver.findElement(getFileUploadXpath()).isDisplayed(), true);
		}		
	}
	public void saveElementScreenshot() {
		try {
			String filePath = getProperty(locators, "home_ActualScreenshot");
			takeElementScreenshot(getBannerForScreenshot(), filePath);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void doPartialImgComparision() {
		try {
			String expImgPath = getProperty(locators, "home_expectedScreenshot");
			String actImgPath = getProperty(locators, "home_ActualScreenshot");
			comparePartialScreenshot(expImgPath, actImgPath);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
