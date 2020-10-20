package scripts;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import generic.BaseClass;
import pages.LoginPage;

/**
 * 
 * @author suman
 *
 */

public class TestValidLogin extends BaseClass{
	
	@Test
	public void validLogin()
	{
		test=extent.createTest("Valid Login");
		LoginPage lp=new LoginPage(driver);
		lp.enterUserName(pro.getEmailId());
		test.log(Status.INFO, "Username entered");
		lp.enterPassword(pro.getPassword());
		test.log(Status.INFO, "Password entered successfully");	
		test.log(Status.INFO, "Logged n successfully");	
		Assert.assertEquals(lp.getMyAccountText(driver), pro.getMyAccount());
	}
}
