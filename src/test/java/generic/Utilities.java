package generic;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class Utilities {
	
	public static String captureScreenShot(WebDriver driver)
	{
		return (String)((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);	
	}
}
