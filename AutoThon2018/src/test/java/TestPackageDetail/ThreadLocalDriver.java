package TestPackageDetail;

import org.openqa.selenium.WebDriver;
public class ThreadLocalDriver {

    public static ThreadLocal<WebDriver> threadLocalDriver = new ThreadLocal<WebDriver>();

    public static WebDriver getThreadLocalDriver() throws InterruptedException{
        return threadLocalDriver.get();
    }

    public static void setThreadLocalDriver(WebDriver driver) throws InterruptedException{
        threadLocalDriver.set(driver);
    }

}