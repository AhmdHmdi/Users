package newUsers;

import org.junit.Test;
import org.openqa.selenium.WebDriver;

public class CreateUser extends Base {

	WebDriver driver = null;
	/*@Test
	public void test() {
		fail("Not yet implemented");
	}*/
	@Test
	public void Load(){
		driver = LoadDriver(driver, "C");
		driver.get(baseURL);
	}
}
