package scripts;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import generic.BaseClass;
import generic.Utilities;
import pages.LoginPage;

public class TestInvalidLogin extends BaseClass {

	
	@Test(dataProvider = "credentials")
	public void testInvalid(String name,String age)
	{
		test=extent.createTest("In-Valid Login");
		LoginPage lp=new LoginPage(driver);
		lp.enterUserName(name);
		test.log(Status.INFO, "Username entered");
		lp.enterPassword(age);
		test.log(Status.INFO, "Password entered successfully");	
//		Assert.assertEquals(lp.getMyAccountText(driver), pro.getMyAccount());
//		test.log(Status.INFO, "Logged in successfully");	
	}
	
	
	@DataProvider(name = "credentials")
	public String[][] testData()
	{
		String[][] testData = Utilities.getExcelData(System.getProperty("user.dir")+"/src/test/resources/input.xlsx", "InvalidData");
		return testData;		
	}
}
