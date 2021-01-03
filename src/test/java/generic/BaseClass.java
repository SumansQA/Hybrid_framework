package generic;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.MultiPartEmail;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

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

	public static ReadProperties pro=new ReadProperties();

	@BeforeSuite
	public void setUp(){
		reporter=new ExtentHtmlReporter(System.getProperty("user.dir")+"/Reports/ExtentReports.html");
		reporter.config().setDocumentTitle("Automation report");
		reporter.config().setReportName("Test Report");		
		reporter.config().setTheme(Theme.DARK);

		extent=new ExtentReports();
		extent.attachReporter(reporter);
		reporter.loadXMLConfig(System.getProperty("user.dir")+"/Extent-Config.xml");

		System.setProperty(ChromeDriverService.CHROME_DRIVER_SILENT_OUTPUT_PROPERTY, "true");
		WebDriverManager.chromedriver().version("87.0.4280.88").setup();
		WebDriverManager.firefoxdriver().setup();
		WebDriverManager.edgedriver().setup();
	}

	@BeforeClass
	//	@Parameters("browser")
	public void setBrowser(){
		String downloadFilepath = System.getProperty("downloadFilepath");
		HashMap<String, Object> chromePrefs = new HashMap<>();
		chromePrefs.put("profile.default_content_settings.popups", 0);
		chromePrefs.put("download.default_directory", downloadFilepath);
		ChromeOptions options = new ChromeOptions();
		options.setExperimentalOption("prefs", chromePrefs);
		options.addArguments("start-maximized"); 
		options.addArguments("disable-infobars"); 
		options.addArguments("--disable-extensions"); 
		options.addArguments("--disable-gpu"); // applicable to windows os only
		options.addArguments("--disable-dev-shm-usage"); // overcome limited resource problems
		options.addArguments("--no-sandbox"); // Bypass OS security model
		options.addArguments("--headless");
		options.addArguments("--window-size=1920x1080");
		//		options.addArguments("--headless", "--disable-gpu", "--window-size=1920,1200","--ignore-certificate-errors", "--silent");
		//		foptions.addArguments("--headless", "--disable-gpu", "--window-size=1920,1200","--ignore-certificate-errors", "--silent");
		//	if (browser.equalsIgnoreCase("chrome")) {
		driver=new ChromeDriver(options);
		//	} 
		//	else if(browser.equalsIgnoreCase("firefox")){
		//		driver=new FirefoxDriver();
		//	}
		//else if (browser.equalsIgnoreCase("edge")) {
		//		driver=new EdgeDriver();

		//	}

		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get(pro.getUrl());
	}

	@AfterMethod
	public void result(ITestResult result){
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
	public void closeBrowser(){
		//	driver.close();
	}

	@AfterSuite
	public void reportMail()
	{
		extent.flush();

		EmailAttachment attachement=new EmailAttachment();
		attachement.setPath(System.getProperty("user.dir")+"/Reports/ExtentReports.html");
		attachement.setDisposition(EmailAttachment.ATTACHMENT);

		MultiPartEmail email=new MultiPartEmail();
		email.setHostName(pro.getHostName());
		email.setSmtpPort(456);
		email.setAuthenticator(new DefaultAuthenticator(pro.getReportMail(), pro.getReportPassword()));
		email.setSSLOnConnect(true);
		try {
			email.addTo(pro.getToMail());
			email.setFrom(pro.getFromMail());
			email.setMsg(pro.getReportMsg());
			email.setSubject(pro.getReportMsg());
			email.attach(attachement);
			email.send();
		} catch (EmailException e) {
			e.printStackTrace();
		}
	}	
}
