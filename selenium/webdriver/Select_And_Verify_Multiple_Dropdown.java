package webdriver;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Select_And_Verify_Multiple_Dropdown {
	WebDriver driver;
	long timeinsecond = 2;
	WebDriverWait explicitWait = new WebDriverWait(driver, 30);
	JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;

	String projectPath = System.getProperty("user.dir");
	String osName = System.getProperty("os.name");

	@BeforeClass
	public void beforeClass() {
		if (osName.contains("Windows")) {
			// System.setProperty("webdriver.gecko.driver", projectPath +
			// "\\browserDrivers\\chromedriver.exe");
			System.setProperty("webdriver.chrome.driver", projectPath + "\\browserDrivers\\chromedriver.exe");
		} else {
			// System.setProperty("webdriver.gecko.driver", projectPath +
			// "/browserDrivers/geckodriver");
			System.setProperty("webdriver.chrome.driver", projectPath + "/browserDrivers/chromedriver");
		}

		// driver = new FirefoxDriver();
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.get("https://www.egov.danang.gov.vn/reg");
	}

	// Select item in drop down
	public void selectItemDrodown(String parentLocator, String itemLocator, String expectedItem) {
		// 1 - click on 1 thẻ bất kỳ để hiện tất cả item trong drodown a
		explicitWait.until(ExpectedConditions.elementToBeClickable(By.xpath(parentLocator)));
		driver.findElement(By.xpath(parentLocator)).click();
		sleepinSeconds(timeinsecond);

		// 2- Chờ cho tất cả item được có trong HTML DOM
		explicitWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(itemLocator)));

		// 3 - Lấy hết
		List<WebElement> allItems = driver.findElements(By.xpath(itemLocator));

		// Tổng số lượng item trong 1 dropdown bằng bao nhiêu
		System.out.println("Item size = " + allItems.size());

		// 4 - Duyệt qua từng cái item
		for (WebElement item : allItems) {
			String actualItem = item.getText();
			System.out.println(actualItem);

			if (actualItem.equals(expectedItem)) {
				jsExecutor.executeScript("arguments[0].scrollIntoView(true);", item);
				sleepinSeconds(timeinsecond);

				// Wait for element clickable
				explicitWait.until(ExpectedConditions.elementToBeClickable(item));
				item.click();
				sleepinSeconds(timeinsecond);
				break;
			}
		}
	}

	// Select one item in custom drop down
	public void selectItemCustomDropdown(String parentXpath, String allItemXpath, String expectedValueItem)
			throws Exception {
		// Click on dropdown to show all items
		WebElement parentDropdown = driver.findElement(By.xpath(parentXpath));
		Thread.sleep(1000);
		// Wait for all item in dropdown is loaded successfully
		explicitWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(allItemXpath)));

		List<WebElement> allItems = driver.findElements(By.xpath(allItemXpath));
		System.out.println("Total item in dromdown is: " + allItems.size());

		for (WebElement chiElement : allItems) {
			System.out.println("Text each get: " + chiElement.getText());

			if (chiElement.getText().equals(expectedValueItem)) {
				// Click on item need to choice
				if (chiElement.isDisplayed()) {
					System.out.println("Click by Selenium: " + chiElement.getText());
					chiElement.click();
				} else {
					jsExecutor.executeScript("arguments[0].scrollIntoView(true);", chiElement);
					Thread.sleep(1000);
					System.out.println("Click by JS= " + chiElement.getText());
				}
			}
		}
	}

	// Get hidden text
	public String getHiidenText(String cssLocator) {
		return (String) jsExecutor.executeScript("return document.querySelector(\"" + cssLocator + "\").value");
	}

	// Function select editable drop down
	public void selectTheItemEditableCustomDropdown(String parentXpath, String childXpath, String expectedItem)
			throws Exception {
		// Click on dropdown to show all items
		driver.findElement(By.xpath(parentXpath)).clear();
		Thread.sleep(1000);

		driver.findElement(By.xpath(parentXpath)).sendKeys(expectedItem);
		Thread.sleep(1000);

		explicitWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(childXpath)));

		List<WebElement> childItems = driver.findElements(By.xpath(childXpath));
		System.out.println("Total item in dromdown is: " + childItems.size());

		for (WebElement actualItem : childItems) {
			System.out.println("Text each get: " + actualItem.getText());

			if (actualItem.getText().trim().equals(expectedItem)) {

				jsExecutor.executeScript("arguments[0].scrollIntoView(true);", actualItem);
				Thread.sleep(1000);

				// Click on this item
				jsExecutor.executeScript("arguments[0].scrollIntoView(true);", actualItem);

				break;
			}
		}
	}

	// Function select multiple item in drop down
	public void selectMultiItemInDropdown(String parentXpath, String childXpath, String[] expectedValueItem)
			throws Exception {
		// Click on dropdown to show all items
		driver.findElement(By.xpath(parentXpath)).click();
		Thread.sleep(1000);

		explicitWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(childXpath)));

		List<WebElement> allItems = driver.findElements(By.xpath(childXpath));
		System.out.println("Total item in dromdown is: " + allItems.size());

		for (WebElement childElemnt : allItems) {
			System.out.println("Text each get: " + childElemnt.getText());
			for (String item : expectedValueItem) {
				if (childElemnt.getText().equals(item)) {

					jsExecutor.executeScript("arguments[0].scrollIntoView(true);", childElemnt);
					Thread.sleep(1000);

					childElemnt.click();
					Thread.sleep(1000);

					List<WebElement> itemSelected = driver.findElements(By.xpath("//li[@class='selected']//input"));
					if (expectedValueItem.length == itemSelected.size()) {
						break;
					}
				}
			}
		}
	}

	// Check item selected
	public boolean areItemSelected(String[] months) {
		List<WebElement> itemSelected = driver.findElements(By.xpath("xpath_expected"));
		int numberItemSelected = itemSelected.size();

		String allItemSelectedText = driver.findElement(By.xpath("")).getText();

		if (numberItemSelected <= 3 && numberItemSelected > 0) {
			boolean status = true;
			for (String item : months) {
				if (!allItemSelectedText.contains(item)) {
					status = false;
					return status;
				}
			}
			return status;
		} else if (numberItemSelected >= 12) {
			return driver.findElement(By.xpath("")).isDisplayed();
		} else if (numberItemSelected > 3 && numberItemSelected < 12) {
			return driver.findElement(By.xpath("")).isDisplayed();
		} else {
			return false;
		}
	}

	@Test
	void test() {

	}

	@AfterClass
	public void afterClass() throws Exception {

	}

	public void sleepinSeconds(long timeoutinsecond) {
		try {
			Thread.sleep(timeoutinsecond * 1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
