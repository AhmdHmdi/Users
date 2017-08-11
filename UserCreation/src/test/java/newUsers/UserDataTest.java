package newUsers;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;

public class UserDataTest extends Base {

	String NameErrorRequired, NameErrorUnique, UserNameOne, UserNameOneRepeated,
	strValueToAddToEmailOne, strValueToAddToEmailTwo
	
	,EmailErrorRequired,EmailErrorUnique, strEmail, strEmailRepeated, 
	strValueToAddToNameOne, strValueToAddToNameTwo
	= null;
	
	
	
	// BE CAUTIOUS, IF THIS VALUE IS TRUE, ALL USERS WILL BE DELETED WHEN RUNNING TEST CASES. 
	Boolean UsingWebServiceDelete = false; 
	
	public void LoadBrowser(){
		// Load WebDriver C for Chrome and F for FireFox 
		driver = LoadDriver(driver, "C");
		// Open the URL
		driver.get(baseURL);
		driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
	}
	
	@Test
	public void UserNameIsRequired(){
				// Open Browser
				LoadBrowser();
				// Test Case #1: User Name is required. 
				// Fill in all data but set user name to be NULL
				FillUserData(driver, "", "Email@Domain.com", "12p@ssw3@rd", "12p@ssw3@rd", true);
				
				//Assertion of 'User Name is required'
				NameErrorRequired = driver.findElement(By.id("user.name.error")).getText().toString();		
				Assert.assertEquals("Assertion of 'User Name is required' is done successfully.",NameErrorRequired, "Required") ;
	}

	@Test 
	public void UserNameIsUnique() throws Exception{
		
		// Open Browser
		LoadBrowser();
		
		// Test Case #2: User Name Must be unique. 
		// Fill in all data twice with same values of User Name
		
		// Check if we don't need data of all users, we will set UsingWebServiceDelete to true,
		//else we will check if name is existed on users or need to generate same user twice.
		
		if (UsingWebServiceDelete == true){
			// Delete All Users
			sendDelete(baseURL+"/user/all");
			System.out.print("All Users are deleted.");
			FillUserData(driver, "UserUnique", "Email1@Domain.com", "12p@ssw3@rd", "12p@ssw3@rd", true);
			
			Thread.sleep(1000);
			driver.navigate().to(baseURL);
			FillUserData(driver, "UserUnique", "Email2@Domain.com", "12p@ssw3@rd", "12p@ssw3@rd", true);
			
			}
		else {
			
			// We have two cases as we don't delete all users: If user exists and if not. 
			// As Email should be unique too, we will create a random value twice and added them to email accounts
			int ValueToAddedToEmailOne = ThreadLocalRandom.current().nextInt(5, 1000 + 1);
			strValueToAddToEmailOne = ""+ValueToAddedToEmailOne;
			int ValueToAddedToEmailTwo = ThreadLocalRandom.current().nextInt(1005, 2000 + 1);
			strValueToAddToEmailTwo = ""+ValueToAddedToEmailTwo;
			
			// Use Random Values
			int ValueToAddToName = ThreadLocalRandom.current().nextInt(5, 1000 + 1);
						
			UserNameOne = "Name"+ValueToAddToName;
			UserNameOneRepeated = UserNameOne;
			
			boolean NameExists = checkNameIsUnique(UserNameOneRepeated);
			if (NameExists){  
				// As the name is existing, so we will need to create a user with same data
				System.out.print("User Name Found");
				FillUserData(driver, UserNameOneRepeated, ValueToAddedToEmailOne+"Email@Domain.com", "12p@ssw3@rd", "12p@ssw3@rd", true);
			}
			else
			{
				// User is not existing, we will create same name twice. 
				// First Time	
				FillUserData(driver, UserNameOne, "E"+strValueToAddToEmailOne+"@Domain.com", "12p@ssw3@rd", "12p@ssw3@rd", true);
				
				Thread.sleep(2000);
				driver.navigate().to(baseURL);
				// Second Time
				FillUserData(driver, UserNameOneRepeated, "E"+strValueToAddToEmailTwo+"@Domain.com", "12p@ssw3@rd", "12p@ssw3@rd", true);
			}
		
		// Assertion of 'User Name Must be unique' 
		Thread.sleep(2000);
		NameErrorUnique = driver.findElement(By.id("user.name.error")).getText().toString();
		Assert.assertEquals("Assertion of 'User Name Must be unique' is done successfully.",NameErrorUnique, "Must be unique") ;
		}
	}

	@Test
	public void UserEmailIsRequired(){
				// Open Browser
				LoadBrowser();
				// Test Case #3: Email is required. 
				// Fill in all data but set Email to be NULL
				FillUserData(driver, "EmailIsRequired", "", "12p@ssw3@rd", "12p@ssw3@rd", true);
				
				//Assertion of 'Email is required'
				EmailErrorRequired = driver.findElement(By.id("user.email.error")).getText().toString();		
				Assert.assertEquals("Assertion of 'Email is required' is done successfully.",EmailErrorRequired, "Required") ;
	}
	
	@Test 
	public void UserEmailIsUnique() throws Exception{
		
		// Open Browser
		LoadBrowser();
		
		// Test Case #4: Email Must be unique. 
		// Fill in all data twice with same values of Email
		
		// Check if we don't need data of all users, we will set UsingWebServiceDelete to true,
		//else we will check if email is existed on users or need to generate same email twice.
		
		if (UsingWebServiceDelete == true){
			// Delete All Users
			sendDelete(baseURL+"/user/all");
			System.out.print("All Users are deleted.");
			FillUserData(driver, "EmailUnique1", "Email@Domain.com", "12p@ssw3@rd", "12p@ssw3@rd", true);
			
			Thread.sleep(1000);
			driver.navigate().to(baseURL);
			FillUserData(driver, "EmailUnique2", "Email@Domain.com", "12p@ssw3@rd", "12p@ssw3@rd", true);
			
			}
		else {
			
			// We have two cases as we don't delete all users: If email exists and if not. 
			// As Name should be unique too, we will create a random value twice and added them to user Name
			int ValueToAddedToNameOne = ThreadLocalRandom.current().nextInt(5, 1000 + 1);
			strValueToAddToNameOne = ""+ValueToAddedToNameOne;
			int ValueToAddedToNameTwo = ThreadLocalRandom.current().nextInt(1005, 2000 + 1);
			strValueToAddToNameTwo = ""+ValueToAddedToNameTwo;
			
			// Use Random Values
			int ValueToAddToEmail = ThreadLocalRandom.current().nextInt(5, 1000 + 1);
						
			strEmail = "E"+ValueToAddToEmail+"@Domain.com";
			strEmailRepeated = strEmail;
			
			boolean EmailExists = checkEmailIsUnique(strEmailRepeated);
			if (EmailExists){  
				// As the Email is existing, so we will need to create a user with same data
				System.out.print("User Email Found");
				FillUserData(driver, "EmailIsUnique"+strValueToAddToNameOne, strEmailRepeated, "12p@ssw3@rd", "12p@ssw3@rd", true);
			}
			else
			{
				// User is not existing, we will create same email twice. 
				// First Time	
				FillUserData(driver, "EmailIsUnique"+strValueToAddToNameOne, strEmail, "12p@ssw3@rd", "12p@ssw3@rd", true);
				
				Thread.sleep(2000);
				driver.navigate().to(baseURL);
				// Second Time
				FillUserData(driver, "EmailIsUnique"+strValueToAddToNameTwo, strEmailRepeated, "12p@ssw3@rd", "12p@ssw3@rd", true);
			}
		
		// Assertion of 'User Email Must be unique' 
		Thread.sleep(2000);
		EmailErrorUnique = driver.findElement(By.id("user.email.error")).getText().toString();
		Assert.assertEquals("Assertion of 'User Email Must be unique' is done successfully.",EmailErrorUnique, "Must be unique") ;
		}
	}

}
