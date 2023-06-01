package webdriver;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.openqa.selenium.JavascriptExecutor;

public class Textbox_Textarea {
	WebDriver driver;

	String customerID;
	String username = "mngr506509";
	String password = "YdyrYde";

	// Input in New Customer (user)/ Output (server) data
	String customerName = "Jason Staham";
	String gender = "male";
	String dateOfBirth = "1010-10-10";
	String address = "231 HaVo Halum";
	String city = "Hawili";
	String state = "NewYork";
	String pin = "999888";
	String phone = "0908212133";
	String email = "jisss" + randomNumber() + "@gmail.com";

	// Input in Edit Customer
	String editAddress = "255 PO Boxing";
	String editCity = "New Jesey";
	String editState = "Stock";
	String editPin = "888666";
	String editPhone = "0958666777";
	String editEmail = "jsstaham" + randomNumber() + "@gmail.com";

	// Locator for New/Edit Customer form
	By nameTextbox = By.name("name");
	By genderRadio = By.xpath("//input[@value='m']");
	By genderTextbox = By.name("gender");
	By dobTextbox = By.name("dob");
	By addressTextArea = By.name("addr");
	By cityTextbox = By.name("city");
	By stateTextbox = By.name("state");
	By pinTextbox = By.name("pinno");
	By phoneTextbox = By.name("telephoneno");
	By emailTextbox = By.name("emailid");
	By passwordTextbox = By.name("password");

	String projectPath = System.getProperty("user.dir");
	String osName = System.getProperty("os.name");

	@BeforeClass // Pre-condition
	public void beforeClass() {
		if (osName.contains("Windows")) {
			//			System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\chromedriver.exe");
			System.setProperty("webdriver.chrome.driver", projectPath + "\\browserDrivers\\chromedriver.exe");
		} else {
			//			System.setProperty("webdriver.gecko.driver", projectPath + "/browserDrivers/geckodriver");
			System.setProperty("webdriver.chrome.driver", projectPath + "/browserDrivers/chromedriver");
		}

		driver = new ChromeDriver();
		//		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.get("https://demo.guru99.com/v4/index.php");

		driver.findElement(By.name("uid")).sendKeys(username);
		driver.findElement(By.name("password")).sendKeys(password);
		driver.findElement(By.name("btnLogin")).click();

		Assert.assertTrue(
				driver.findElement(By.xpath("//tr[@class='heading3']/td[text()='Manger Id : " + username + "']"))
				.isDisplayed());
	}

	@Test
	public void Add_New_Customer() {
		driver.findElement(By.xpath("//a[text()='New Customer']")).click();
		// Input data to New Customer form
		driver.findElement(nameTextbox).sendKeys(customerName);
		driver.findElement(genderRadio).click();
		driver.findElement(dobTextbox).sendKeys(dateOfBirth);
		driver.findElement(addressTextArea).sendKeys(address);
		driver.findElement(cityTextbox).sendKeys(city);
		driver.findElement(stateTextbox).sendKeys(state);
		driver.findElement(pinTextbox).sendKeys(pin);
		driver.findElement(phoneTextbox).sendKeys(phone);
		driver.findElement(emailTextbox).sendKeys(email);
		driver.findElement(passwordTextbox).sendKeys(password);
		driver.findElement(By.name("sub")).click();

		Assert.assertTrue(
				driver.findElement(By.xpath("//p[@class='heading3' and text()='Customer Registered Successfully!!!']"))
				.isDisplayed());

		// Verify output data = input data
		Assert.assertEquals(customerName,
				driver.findElement(By.xpath("//td[text()='Customer Name']/following-sibling::td")).getText());
		Assert.assertEquals(gender,
				driver.findElement(By.xpath("//td[text()='Gender']/following-sibling::td")).getText());
		Assert.assertEquals(dateOfBirth,
				driver.findElement(By.xpath("//td[text()='Birthdate']/following-sibling::td")).getText());
		Assert.assertEquals(address,
				driver.findElement(By.xpath("//td[text()='Address']/following-sibling::td")).getText());
		Assert.assertEquals(city, driver.findElement(By.xpath("//td[text()='City']/following-sibling::td")).getText());
		Assert.assertEquals(state,
				driver.findElement(By.xpath("//td[text()='State']/following-sibling::td")).getText());
		Assert.assertEquals(pin, driver.findElement(By.xpath("//td[text()='Pin']/following-sibling::td")).getText());
		Assert.assertEquals(phone,
				driver.findElement(By.xpath("//td[text()='Mobile No.']/following-sibling::td")).getText());
		Assert.assertEquals(email,
				driver.findElement(By.xpath("//td[text()='Email']/following-sibling::td")).getText());

		customerID = driver.findElement(By.xpath("//td[text()='Customer ID']/following-sibling::td")).getText();
		System.out.println(customerID);
	}

	//	@Test
	//	public void JSExecutorRemoveAttributeDisabled() {
	////		Creating the JavascriptExecutor interface object by Type casting		
	////      JavascriptExecutor js = (JavascriptExecutor)driver;
	//		JavascriptExecutor jsExecutor;
	//		jsExecutor = (JavascriptExecutor) driver;
	//
	//		WebElement dateOfBirthTextbox = driver.findElement(By.name("dob"));
	//		jsExecutor.executeScript("arguments[0].removeAttribute('disabled')", dateOfBirthTextbox);
	//		dateOfBirthTextbox.sendKeys(dateOfBirth);
	//	}
	
	@Test
	public void Edit_Customer() {
		driver.findElement(By.xpath("//a[text()='Edit Customer']")).click();

		driver.findElement(By.name("cusid")).sendKeys(customerID);
		System.out.println("Customer ID at Edit Customer form = "+ customerID);

		driver.findElement(By.name("AccSubmit")).click();

		// Verify Name/ Gender/ DOB is disabled fields
		Assert.assertFalse(driver.findElement(nameTextbox).isEnabled());
		Assert.assertFalse(driver.findElement(genderTextbox).isEnabled());
		Assert.assertFalse(driver.findElement(dobTextbox).isEnabled());

		// Verify output at Edit Customer form = input at New Customer form
		Assert.assertEquals(customerName, driver.findElement(nameTextbox).getAttribute("value"));
		Assert.assertEquals(gender, driver.findElement(genderTextbox).getAttribute("value"));
		Assert.assertEquals(dateOfBirth, driver.findElement(dobTextbox).getAttribute("value"));
		Assert.assertEquals(address, driver.findElement(addressTextArea).getText());
		Assert.assertEquals(city, driver.findElement(cityTextbox).getAttribute("value"));
		Assert.assertEquals(state, driver. findElement(stateTextbox).getAttribute("value"));
		Assert.assertEquals(pin, driver.findElement(pinTextbox).getAttribute("value"));
		Assert.assertEquals(phone, driver.findElement(phoneTextbox).getAttribute("value"));
		Assert.assertEquals(email, driver.findElement(emailTextbox).getAttribute("value"));
		
//	Creating the JavascriptExecutor interface object by Type casting		
		JavascriptExecutor js = (JavascriptExecutor)driver;
		JavascriptExecutor jsExecutor;
		jsExecutor = (JavascriptExecutor) driver;

		WebElement dateOfBirthTextbox = driver.findElement(By.name("dob"));
		jsExecutor.executeScript("arguments[0].removeAttribute('disabled')", dateOfBirthTextbox);
		driver.findElement(dobTextbox).clear();
		dateOfBirthTextbox.sendKeys("1111-11-11");
	
		
		// Edit data at Edit Customer form
		driver.findElement(addressTextArea).clear();
		driver.findElement(addressTextArea).sendKeys(editAddress);
		driver.findElement(cityTextbox).clear();
		driver.findElement(cityTextbox).sendKeys(editCity); 
		driver.findElement(stateTextbox).clear();
		driver.findElement(stateTextbox).sendKeys(editState); 
		driver.findElement(pinTextbox).clear();
		driver.findElement(pinTextbox).sendKeys(editPin);
		driver.findElement(phoneTextbox).clear();
		driver.findElement(phoneTextbox).sendKeys(editPhone); 
		driver.findElement(emailTextbox).clear();
		driver.findElement(emailTextbox).sendKeys(editEmail);
		driver.findElement(By.name("sub")).click();

		Assert.assertTrue(driver.findElement(By.xpath("//p[@class-'heading3' and text()='Customer details updated Successfully!']")).isDisplayed());

		// Verify output data = input data
		Assert.assertEquals(customerID, driver.findElement(By.xpath("//td[(text() = 'Customer ID']/following-sibling::td")).getText());
		Assert.assertEquals(customerName, driver.findElement(By.xpath("//td[text() ='Customer Name']/following-sibling::td")).getText());
		Assert.assertEquals(gender, driver.findElement(By.xpath("//td|text() = 'Gender']/following-sibling::td")).getText());
		Assert.assertEquals("1111-11-11", driver.findElement(By.xpath("//td[(text()='Birthdate']/following-sibling::td")).getText());
		Assert.assertEquals(editAddress, driver.findElement(By. xpath("//td[text()='Address']/following- sibling td")).getText());
		Assert.assertEquals(editCity, driver.findElement(By.xpath("//td[(text()='City']/following-sibling::td")).getText());
		Assert.assertEquals(editState, driver.findElement(By.xpath("//td[(text() = 'State']/following-sibling::td")).getText());
		Assert.assertEquals(editPin, driver.findElement (By.xpath("//td[text()= 'Pin']/following-sibling::td")).getText());
		Assert.assertEquals(editPhone, driver.findElement (By.xpath("//td[text() ='Mobile No.']/following-sibling::td")).getText());
		Assert.assertEquals(editEmail,driver.findElement(By.xpath("/td[text()='Email']/following-sibling::td")).getText());
	}

	@AfterClass
	public void afterClass() throws Exception {
		//		driver.quit();
	}

	public int randomNumber() {
		Random rand = new Random();
		return rand.nextInt(100000);
	}

}