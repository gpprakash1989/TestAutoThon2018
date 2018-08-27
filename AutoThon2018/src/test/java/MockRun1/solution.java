package MockRun1;

import static com.jayway.restassured.RestAssured.given;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.apache.commons.lang3.ArrayUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import TestPackageDetail.DriverFactory;
import TestPackageDetail.ThreadLocalDriver;

public class solution {

	static String[] mobiles = { "Samsung j7", "Samsung a8", "Samsung galaxy j8", "vivo v9", "vivo v7", "vivo v9 youth",
			"iPhone x", "iPhone 8", "nokia 7 plus", "nokia 3310" };
	static String[] browsers = { "chrome", "chrome", "chrome", "chrome", "chrome" };
	static Object[][] obj;

	WebDriver driver;
	String browserType;
	ArrayList<String> tempData;
	HashMap<String, String> sendData;
	static HashMap<Long, ArrayList<String>> collectData = new HashMap<Long, ArrayList<String>>();

	@SuppressWarnings("rawtypes")
	@DataProvider(name = "DataJason", parallel = true)
	public Object[][] credentials() {
		obj = new Object[browsers.length][2];
		for (int i = 0; i <= browsers.length - 1; i++) {
			obj[i][0] = browsers[i];
			String mob1 = "";
			String mob2 = "";
			mob1 = mobiles[generateRandomNumberWithRange(0, mobiles.length - 1)];
			for (;;) {
				mob2 = mobiles[generateRandomNumberWithRange(0, mobiles.length - 1)];
				if (!mob1.substring(0, 4).equals(mob2.substring(0, 4))) {
					break;
				}
			}
			mobiles = ArrayUtils.removeElement(mobiles, mob1);
			mobiles = ArrayUtils.removeElement(mobiles, mob2);
			List<String> obj1 = new ArrayList<String>();
			obj1.add(mob1);
			obj1.add(mob2);
			obj[i][1] = obj1;
		}

		return obj;
	}

	public static int generateRandomNumberWithRange(int minimum, int maximum) {
		Random rn = new Random();
		int range = maximum - minimum + 1;
		int number = rn.nextInt(range) + minimum;
		return number;
	}

	@Test(dataProvider = "DataJason")
	public synchronized void webMobileTest(String browserType, ArrayList<String> brandNames) throws MalformedURLException, InterruptedException {
		// public void webMobileTest() {
		ArrayList<String> description = new ArrayList<String>();
		
		driver = DriverFactory.createInstance(browserType);
		ThreadLocalDriver.setThreadLocalDriver(driver);
		driver = ThreadLocalDriver.getThreadLocalDriver();
		driver.get("https://www.flipkart.com");
		waitAWhile(3000);
		getElement("/html/body/div[2]/div/div/button").sendKeys(Keys.ESCAPE);
		for (int i = 0; i < brandNames.size(); i++) {
			
			getElement("//*[@id='container']/div/header/div[1]/div/div/div/div[2]/form/div/div[1]/div/input").clear();
			getElement("//*[@id='container']/div/header/div[1]/div/div/div/div[2]/form/div/div[1]/div/input")
					.sendKeys(brandNames.get(i));
			getElement("//*[@id='container']/div/header/div[1]/div/div/div/div[2]/form/div/div[1]/div/input")
					.sendKeys(Keys.ENTER);
			waitAWhile(5000);

			String title = getElement(
					"//*[@id='container']/div/div[1]/div[2]/div/div[1]/div[2]/div[2]/div/div/div/a/div[3]/div[1]/div[1]")
							.getText().toString();

			List<WebElement> descriptionList = driver.findElements(By.xpath(
					"//*[@id='container']/div/div[1]/div[2]/div/div[1]/div[2]/div[2]/div/div/div/a/div[3]/div[1]/div[3]/ul/li"));
			String rowData = title + "-";
			System.out.println(rowData + "++++++++++++++++");
			for (WebElement element2 : descriptionList) {
				rowData = rowData + element2.getText().toString() + "_";
			}
			description.add(rowData);
		}
		collectData.put(Thread.currentThread().getId(), description);
		waitAWhile(5000);
		System.out.println("Result::  " + collectData);
	}

	@AfterClass
	public void postJson() throws MalformedURLException, InterruptedException {
		/*
		 * Map<String, ArrayList<String>> postDataRow = new HashMap<String,
		 * ArrayList<String>>();
		 * 
		 * ArrayList<String> test1 = new ArrayList<String>();
		 * test1.add("samsung1-lin1_lin2"); test1.add("samsung2-lin1_lin2");
		 * 
		 * ArrayList<String> test2 = new ArrayList<String>();
		 * test2.add("vivo1-lin1_lin2"); test2.add("vivo2-lin1_lin2");
		 * 
		 * postDataRow.put("thread3", test1); postDataRow.put("thread4", test2);
		 */

		Map<String, Object> jsonAsMap = new HashMap<>();
		Iterator it = collectData.entrySet().iterator();
		
		System.out.println("Size of Map"+collectData.size());
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			Long ThreadID = (Long) pair.getKey();
			jsonAsMap.put("ThreadID", ThreadID);

			ArrayList<String> listOfMobileInfo = (ArrayList<String>) pair.getValue();
			int i = 1;
			for (String rowDetail : listOfMobileInfo) {
				String MobileName = rowDetail.split("-")[0];
				String Description = rowDetail.split("-")[1].replaceAll("_", ",");
				jsonAsMap.put("MobileName" + i, MobileName);
				jsonAsMap.put("Description" + i, Description);
				i++;
			}
			given().contentType("application/json; charset=UTF-16").body(jsonAsMap).when()
					.post("http://localhost:3000/posts");
		}
	}

	private HashMap<String, String> readData() {
		sendData = new HashMap<String, String>();
		return sendData;
	}

/*	private WebDriver openBrowser(String browserType) throws MalformedURLException, InterruptedException {
		if (browserType == "chrome") {
						
			driver = DriverFactory.createInstance("chrome");
			ThreadLocalDriver.setThreadLocalDriver(driver);
			driver = ThreadLocalDriver.getThreadLocalDriver();
			
		} else if (browserType == "firefox") {
			System.setProperty("webdriver.gecko.driver",
					"C:\\Users\\ab36194\\git\\TestAutothon\\Drivers\\geckodriver.exe");
			driver = new FirefoxDriver();
		} else {
			System.out.println("Add mobile driver");
		}
		return driver;
	}*/

	private WebElement getElement(String locator) {
		WebElement element = driver.findElement(By.xpath(locator));
		return element;
	}

	private void waitAWhile(int num) {
		try {
			Thread.sleep(num);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
