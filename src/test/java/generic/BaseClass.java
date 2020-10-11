package generic;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseClass {
	
	public static ExtentHtmlReporter reporter;
	
	public static ExtentReports extent;
	
	public static WebDriver driver;
	
	public static ExtentTest test;
	
	@BeforeSuite
	public void setUp()
	{
		reporter=new ExtentHtmlReporter(System.getProperty("user.dir")+"/Reports/ExtentReports.html");
		reporter.config().setDocumentTitle("Automation report");
		reporter.config().setReportName("Test Report");		
		reporter.config().setTheme(Theme.DARK);
		
		extent=new ExtentReports();
		extent.attachReporter(reporter);
		reporter.loadXMLConfig(System.getProperty("user.dir")+"/Extent-Config.xml");
		
		System.setProperty(ChromeDriverService.CHROME_DRIVER_SILENT_OUTPUT_PROPERTY, "true");
		WebDriverManager.chromedriver().setup();
		WebDriverManager.firefoxdriver().setup();
		WebDriverManager.edgedriver().setup();
	}
	
	@BeforeClass
	public void setBrowser(String browser)
	{
		if (browser.equalsIgnoreCase("chrome")) {
			driver=new ChromeDriver();
		} 
		else if(browser.equalsIgnoreCase("firefox"))
		{
			driver=new FirefoxDriver();
		}
		else if (browser.equalsIgnoreCase("edge")) {
			driver=new EdgeDriver();
			
		}
		
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get("URL");
	}
	
	@AfterMethod
	public void result(ITestResult result)
	{
		if (result.getStatus()==ITestResult.SUCCESS) {
			test.createNode(result.getName());
			test.log(Status.PASS, "The test case"+result.getName()+"is pass");
		}
		
		else if (result.getStatus()==ITestResult.FAILURE) {
			test.createNode(result.getName());
			test.log(Status.FAIL, "The test case"+result.getName()+"is failed");
			test.addScreenCaptureFromBase64String(Utilities.captureScreenShot(driver));
		}
		else if (result.getStatus()==ITestResult.SKIP) {
			test.createNode(result.getName());
			test.log(Status.SKIP, "The test case"+result.getName()+"is skipped");
		}
	}
	
	@AfterClass
	public void closeBrowser()
	{
		driver.close();
	}
}
