package newUsers;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CreateUser extends Base {

	Boolean AssertorNot = false;
	String UserError = null;
	/*@Test
	public void test() {
		fail("Not yet implemented");
	}*/
	@Test 
	public void Load(){
		// Load WebDriver C for Chrome and F for FireFox 
		driver = LoadDriver(driver, "F");
		// Open the URL
		driver.get(baseURL);
		// Fill in User Data: Name, Email, Password. 
		FillData(driver);
		// Assertion
		if (AssertorNot == true)
		{
		UserError = driver.findElement(By.id("user.name.error")).getText().toString();		//System.out.print(UserError);
		Assert.assertEquals("Assertion Failed",UserError, "Required") ;
		}
		else 		{			/* Do nothing*/		}
	}
	
	public void FillData(WebDriver driver){
		driver.findElement(By.id("name")).sendKeys("TestName");
		driver.findElement(By.id("email")).sendKeys("Test@Name.com");
		driver.findElement(By.id("password")).sendKeys("12p@ssw5@rd");
		driver.findElement(By.id("confirmationPassword")).sendKeys("12p@ssw5@rd");
		driver.findElement(By.xpath("/html/body/div/div/div/form/fieldset/div[5]/button")).click();
	}
}
