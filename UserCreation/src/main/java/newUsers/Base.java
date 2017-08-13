package newUsers;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ThreadLocalRandom;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.remote.DesiredCapabilities;

public class Base {
	
	public static String baseURL = "";
	public static WebDriver driver = null;
	private final String USER_AGENT = "Mozilla/5.0";
	private boolean CheckNameExists= false;
	private String strRandom = "";
	Boolean isPresent= false;

	public static WebDriver LoadDriver(WebDriver driver, String DriverName)
	{
		// Setup FireFox and Chrome Driver. 
		if (DriverName == "F")
		{
			System.setProperty("webdriver.gecko.driver", "src/drivers/geckodriver.exe");
			ProfilesIni profile = new ProfilesIni();
			FirefoxProfile Uprofile = profile.getProfile("default");
			Uprofile.setAcceptUntrustedCertificates(true);
			Uprofile.setAssumeUntrustedCertificateIssuer(true);
			Uprofile.setPreference("security.insecure_field_warning.contextual.enabled", false);
			DesiredCapabilities cap = DesiredCapabilities.firefox();
			cap.setCapability(FirefoxDriver.PROFILE, Uprofile);
			cap.setCapability("marionette", true);
			driver = new FirefoxDriver(cap);
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
	
	public void FillUserData(WebDriver driver, String varName, String varEmail, String varPassword, String varConfirmPassword, Boolean varSubmit){
		// Fill in User Data( Web Driver, User Name, User Email, Password, Confirm Password, Click on button Submit or Not) 

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

	public String sendGet(String varURL) throws Exception {

		// Use Web Service Action GET, to retrieve all Users data 
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

			return response.toString();

		}
	
	public boolean checkNameorEmailExists(String varURL, String varName, String varEmail) throws Exception {

			// Select data of All Users
			String strResponse=sendGet(varURL);
			

			//print result
			System.out.println(strResponse.toLowerCase()+"\n");
			
			// Check Email or Name exists according to Method parameter 
			
			if (varEmail == ""){
			System.out.println(varName.toLowerCase());
			return CheckNameExists= strResponse.toLowerCase().contains(varName.toLowerCase());
			}
			else if (varName == ""){
			System.out.println(varEmail.toLowerCase());
			return CheckNameExists= strResponse.toLowerCase().contains(varEmail.toLowerCase());
			}
			else
			{
				return CheckNameExists=false;
			}

		}
	
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
		String ActualMessage = driver.findElement(By.id(elementId)).getText().toString();
		if (ExpectedMessage.equals(ActualMessage))
		{
			Passed = true;
		}else
		{
			Passed = false;
		}	
	}
	return Passed;
	}

}
