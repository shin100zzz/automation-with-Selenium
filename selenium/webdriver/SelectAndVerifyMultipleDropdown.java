package webdriver;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class SelectAndVerifyMultipleDropdown {
		WebDriver driver;
		String projectPath = System.getProperty("user.dir");
		String osName = System.getProperty("os.name");		
		
		@BeforeClass
		public void beforeClass() {
			if (osName.contains("Windows")) {
//				System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\chromedriver.exe");
				System.setProperty("webdriver.chrome.driver", projectPath + "\\browserDrivers\\chromedriver.exe");		
			} else {
//				System.setProperty("webdriver.gecko.driver", projectPath + "/browserDrivers/geckodriver");
				System.setProperty("webdriver.chrome.driver",projectPath + "/browserDrivers/chromedriver");
			}
			
//			driver = new FirefoxDriver();
			driver = new ChromeDriver();
			driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
			driver.manage().window().maximize();
			driver.get("https://www.facebook.com/");
		}


	@Test
	public void TC_01() throws InterruptedException {
		driver.get("https://automationfc.github.io/basic-form/index.html");
		
		String testing[] = {"Manual", "Mobile", "Security", "Perfomance"};

		select = new Select(driver.findElement(By.xpath("//select[@id='job2']")));
		Assert.assertTrue(select.isMultiple());
		
		for (String value : testing) {
			select.selectByVisibleText(value);
			Thread.sleep(500);
		}

		List<WebElement> selectedOption = select.getAllSelectedOptions();
		Assert.assertEquals(selectedOption.size(), 4);
		
		List<String> actualValues = new ArrayList<String>();

		for (WebElement option : selectedOption) {
			actualValues.add(option.getText());
		}
		
		List<String> expectedValues = Arrays.asList(testing);
		
		Assert.assertEquals(actualValues, expectedValues);
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}
}
