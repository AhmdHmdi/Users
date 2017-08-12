package newUsers;

import static org.junit.Assert.fail;


import java.util.concurrent.TimeUnit;

import org.junit.Test;

public class UserDataTest extends Base {

	String NameErrorRequired, NameErrorUnique, UserNameOne, UserNameOneRepeated,
	strValueOfEmailOne, strValueOfEmailTwo
	
	,EmailErrorRequired,EmailErrorUnique, strEmail, strEmailRepeated, 
	strValueToAddToNameOne, strValueToAddToNameTwo
	
	,strValueToMakeUniqueValues

	,strValueOfName,strValueOfEmail
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
				FillUserData(driver, "", "Email@TestDomain.com", "12p@ssw3@rd", "12p@ssw3@rd", true);
				
				//Assertion of 'User Name is required'
				Boolean Passed= AssertElement("user.name.error","Required");
				if (Passed)
				{
					System.out.println("Assertion of 'User Name is required' is done successfully.");
				}else
				{
					fail("Assertion of 'User Name is required' is failed.");
				}
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
			strValueOfEmailOne = RandomValue(10, 10000, "EmailOne", true);
			strValueOfEmailTwo = RandomValue(10010, 20000, "EmailTwo", true);
			
			// Use Random Values
			UserNameOne = RandomValue(100, 10000, "Name", false);
			UserNameOneRepeated = UserNameOne;
			
			boolean NameExists = checkNameIsUnique(UserNameOneRepeated);
			if (NameExists){  
				// As the name is existing, so we will need to create a user with same data
				System.out.print("User Name Found");
				FillUserData(driver, UserNameOneRepeated, strValueOfEmailOne, "12p@ssw3@rd", "12p@ssw3@rd", true);
			}
			else
			{
				// User is not existing, we will create same name twice. 
				// First Time	
				FillUserData(driver, UserNameOne, strValueOfEmailOne, "12p@ssw3@rd", "12p@ssw3@rd", true);
				Thread.sleep(2000);
				driver.navigate().to(baseURL);
				// Second Time
				FillUserData(driver, UserNameOneRepeated, strValueOfEmailTwo, "12p@ssw3@rd", "12p@ssw3@rd", true);
			}
		
		// Assertion of 'User Name Must be unique' 
		Thread.sleep(2000);
		Boolean Passed= AssertElement("user.name.error","Must be unique");
		if (Passed)
		{
			System.out.println("Assertion of 'User Name is required' is done successfully.");
		}else
		{
			fail("Assertion of 'User Name is required' is failed.");
		}
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
				Boolean Passed= AssertElement("user.email.error","Required");
				if (Passed)
				{
					System.out.println("Assertion of 'Email is required' is done successfully.");
				}else
				{
					fail("Assertion of 'Email is required' is failed.");
				}
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
			strValueToAddToNameOne = RandomValue(5, 10000, "EmailUnique", false);
			strValueToAddToNameTwo = RandomValue(10050, 50000, "EmailUnique", false);
			
			// Use Random Values
			strEmail = RandomValue(10000, 999999, "EmailUnique", true);
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
		//Assertion of 'Email is required'
		Boolean Passed= AssertElement("user.email.error","Must be unique");
		if (Passed)
		{
			System.out.println("Assertion of 'Email is required' is done successfully.");
		}else
		{
			fail("Assertion of 'Email is required' is failed.");
		}
		}
	}

	@Test
	public void UserPasswordIsRequired() throws InterruptedException{
				// Open Browser
				LoadBrowser();

				strValueOfName = RandomValue(10, 10000, "Pass", false);
				strValueOfEmail = RandomValue(10010, 20000, "Pass", true);
				// Test Case #5: Password is required. 
				// Fill in all data but set Password to be NULL
				FillUserData(driver, strValueOfName, strValueOfEmail, "", "", true);
				
				//Assertion of 'Password is required'
				Boolean Passed= AssertElement("user.password.error","Required");
				if (Passed)
				{
					System.out.println("Assertion of 'Password is required' is done successfully.");
				}else
				{
					fail("Assertion of 'Password is required' is failed.");
				}
				
	}
	
	
	
}
