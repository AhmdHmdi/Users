package newUsers;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class Base {
	
	public static String baseURL = "";
	public static WebDriver driver = null;
	private final String USER_AGENT = "Mozilla/5.0";
	private boolean CheckNameExists= false;
	private String strRandom = "";
	Boolean isPresent= false;

	// Setup FireFox and Chrome Driver. 
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
	
	// Use a generic method to fill in the data of user
	// Fill in User Data( Web Driver, User Name, User Email, Password, Confirm Password, Click on button Submit or Not) 
	public void FillUserData(WebDriver driver, String varName, String varEmail, String varPassword, String varConfirmPassword, Boolean varSubmit){
		driver.findElement(By.id("name")).sendKeys(varName);
		driver.findElement(By.id("email")).sendKeys(varEmail);
		driver.findElement(By.id("password")).sendKeys(varPassword);
		driver.findElement(By.id("confirmationPassword")).sendKeys(varConfirmPassword);
		if (varSubmit == true){
			driver.findElement(By.xpath("/html/body/div/div/div/form/fieldset/div[5]/button")).click();
		} else { /*Do Nothing*/}
	}

	public Boolean checkNameIsUnique(String varName) throws Exception{
		
		checkNameorEmailExists(baseURL+"/user/all/json", varName, "");
		return CheckNameExists;	
	}
	
	public Boolean checkEmailIsUnique(String varEmail) throws Exception{
		
		checkNameorEmailExists(baseURL+"/user/all/json", "", varEmail);
		return CheckNameExists;	
	}
	// HTTP GET request
	public void sendGet(String varURL) throws Exception {

			String url = varURL; 

			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();

			// optional default is GET
			con.setRequestMethod("GET");

			//add request header
			con.setRequestProperty("User-Agent", USER_AGENT);

			int responseCode = con.getResponseCode();
			System.out.println("\nSending 'GET' request to URL : " + url);
			System.out.println("Response Code : " + responseCode);

			BufferedReader in = new BufferedReader(
			        new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			//print result
			System.out.println(response.toString());

		}
	
	// HTTP GET request, Check name is included on the response.
	public boolean checkNameorEmailExists(String varURL, String varName, String varEmail) throws Exception {

			String url = varURL; 

			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();

			// optional default is GET
			con.setRequestMethod("GET");

			//add request header
			con.setRequestProperty("User-Agent", USER_AGENT);

			int responseCode = con.getResponseCode();
			System.out.println("\nSending 'GET' request to URL : " + url);
			System.out.println("Response Code : " + responseCode);

			BufferedReader in = new BufferedReader(
			        new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			//print result
			System.out.println(response.toString().toLowerCase()+"\n");
			
			if (varEmail == ""){
			System.out.println(varName.toLowerCase());
			return CheckNameExists= response.toString().toLowerCase().contains(varName.toLowerCase());
			}
			else if (varName == ""){
			System.out.println(varEmail.toLowerCase());
			return CheckNameExists= response.toString().toLowerCase().contains(varEmail.toLowerCase());
			}
			else
			{
				return CheckNameExists=false;
			}

		}
	
	// HTTP DELETE Request
	public void sendDelete(String varURL) throws Exception {

		String url = varURL; 

		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		con.setRequestMethod("DELETE");

		//add request header
		con.setRequestProperty("User-Agent", USER_AGENT);

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'Delete' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		//print result
		System.out.println(response.toString());

	}

	// Get Random Value
	public String RandomValue(int min, int max, String strPrefix, Boolean IfEmail){
		int ValueToAddedToEmailOne = ThreadLocalRandom.current().nextInt(min, max + 1);
		
		if (IfEmail){
			strRandom = strPrefix+"@R"+ValueToAddedToEmailOne+".com";
		}
		else
		{
			strRandom = strPrefix+"R"+ValueToAddedToEmailOne;
		}
				return strRandom;
	}

	// Assertion 
	public boolean AssertElement(String elementId, String ExpectedMessage)
	{
	Boolean Passed = false;	
	try{
		
		isPresent = driver.findElement(By.id(elementId)).getText().isEmpty();
		
	} catch(Exception e){
		isPresent = true;
	}
	
	if (isPresent)
	{
		Passed = false;
	}else
	{
		Passed = true;
		String ActualMessage = driver.findElement(By.id(elementId)).getText().toString();		
		Assert.assertEquals("Passed",ExpectedMessage, ActualMessage) ;
	}
	return Passed;
	}

}
