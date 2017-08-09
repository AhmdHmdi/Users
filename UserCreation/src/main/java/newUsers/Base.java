package newUsers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

public class Base {
	
	public WebDriver driver = null;
	public WebDriver getDriver(){
		driver = LoadDriver(driver, "F");
		return driver;
	}
	public static WebDriver LoadDriver(WebDriver driver, String DriverName)
	{
		if (DriverName == "F")
		{
			driver = new FirefoxDriver();
			return driver;
		}
		else if (DriverName == "C")
		{
			driver = new ChromeDriver();
			return driver;
		}
		else if (DriverName == "ED")
		{
			driver = new EdgeDriver();
			return driver;
		}
		else if (DriverName == "IE")
		{
			driver = new InternetExplorerDriver();
			return driver;
		}
		else 
		{
			System.out.println("Please Enter Correct Driver Name: F, C, ED or IE");
			driver = null;
		}
		return driver;
	}
}
