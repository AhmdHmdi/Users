package newUsers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
public class Base {
	
	public static String baseURL = "";
	public static WebDriver driver = null;
	
	public WebDriver getDriver(){
		driver = LoadDriver(driver, "F");
		return driver;
	}
	
	public static WebDriver LoadDriver(WebDriver driver, String DriverName)
	{
		if (DriverName == "F")
		{
			System.setProperty("webdriver.gecko.driver", "src/drivers/geckodriver.exe");
			driver = new FirefoxDriver();
			return driver;
		}
		else if (DriverName == "C")
		{
			System.setProperty("webdriver.chrome.driver", "src/drivers/chromedriver.exe");
			driver = new ChromeDriver();
			return driver;
		}
		else 
		{
			System.out.println("Please Enter Correct Driver Name: F or C");
			driver = null;
		}
		return driver;
	}
	

}
