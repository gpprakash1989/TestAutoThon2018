package TestPackageDetail;

import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import TestPackageDetail.DriverFactory;
import TestPackageDetail.ThreadLocalDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;

public class Solution {
	AppiumDriverLocalService appiumService;
	WebDriver driver, driver2;

	@DataProvider(name = "DataJason", parallel = true)
	public Object[][] credentials() {

		return new Object[][] { { "blue Cap$emulator-5554", "4723" }, { "red cap$emulator-5556", "4724" } };

	}

	@BeforeTest
	public void beforeTest() {
	}

	@Test(dataProvider = "DataJason")
	public void TestMethod(String deviceName, String Port) throws InterruptedException, IOException {
		System.out.println(Thread.currentThread().getId());
		String device_Name = deviceName.split("\\$")[1];
		String port_Name = Port;
		driver = DriverFactory.createInstance("chrome", device_Name, port_Name, device_Name);
		ThreadLocalDriver.setThreadLocalDriver(driver);
		driver = ThreadLocalDriver.getThreadLocalDriver();
		runTest(driver, deviceName.split("\\$")[0]);

	}
	
	public synchronized void runTest(WebDriver driver ,String capColor) throws InterruptedException, IOException {	
		driver.get("https://www.google.com/shopping");
		driver.findElement(By.xpath("//*[@id=\"lst-ib\"]")).sendKeys(capColor);
		driver.findElement(By.xpath("//*[@id=\"tsbb\"]/div")).click();
		driver.findElement(By.xpath("//*[@id=\"rso\"]/div[2]/div[1]/div[2]/a[1]/div/div[1]")).click();
		Thread.sleep(5000);
		screenPrint(driver, capColor);
	}
	
	public void screenPrint(WebDriver dvr, String color) throws IOException {
		File scrFile = ((TakesScreenshot) dvr).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(scrFile, new File("c:\\tmp\\screenshot_" + color + ".png"));
	}

}
