package TestPackageDetail;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import java.net.MalformedURLException;
import java.net.URL;

public class DriverFactory {

	public static WebDriver driver = null;
	
	public static WebDriver createInstance(String browser) {
		if (browser.equalsIgnoreCase("chrome")) {
			System.setProperty("webdriver.chrome.driver",
					"C:\\Users\\ab36194\\git\\TestAutothon\\Drivers\\chromedriver.exe");
			driver = new ChromeDriver();
			return driver;
		} else if (browser.equalsIgnoreCase("firefox")) {
			System.setProperty("webdriver.gecko.driver",
					"C:\\Users\\ab36194\\git\\TestAutothon\\Drivers\\geckodriver.exe");
			driver = new FirefoxDriver();
			return driver;
		} else {
			System.setProperty("webdriver.gecko.driver",
					"C:\\Users\\ab36194\\git\\TestAutothon\\Drivers\\geckodriver.exe");
			driver = new FirefoxDriver();
			return driver;
		}
	}

	public static WebDriver createInstanceAndroid(String browser, String device_name, String port, String udid)
			throws MalformedURLException, InterruptedException {
		{
			DesiredCapabilities capabilities = DesiredCapabilities.android();
			String browserType = browser.toLowerCase();
			capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, browserType);
			capabilities.setCapability("udid", udid);
			capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
			capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, device_name);
			capabilities.setCapability(MobileCapabilityType.VERSION, "8.1.0");
			{
				URL url = null;
				try {
					url = new URL("http://0.0.0.0:" + port + "/wd/hub"); // 16265 4723
				} catch (MalformedURLException e) {
					e.printStackTrace();
				}
				WebDriver driver = new AndroidDriver<MobileElement>(url, capabilities);
				return driver;
			}
		}
	}
}
