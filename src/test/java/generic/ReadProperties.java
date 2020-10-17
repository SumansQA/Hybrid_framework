package generic;

import java.io.File;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * 
 * @author suman
 *
 */

public class ReadProperties {
	
	public Properties pro;
	public ReadProperties(){
		try {
			pro=new Properties();
			pro.load(new FileInputStream(new File(System.getProperty("user.dir")+"/src/test/resources/config.properties")));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String getUrl() {
		return pro.getProperty("URL");
	}
	
	public String getEmailId()
	{
		return pro.getProperty("EMAIL");
	}
	
	public String getPassword()
	{
		return pro.getProperty("PASSWORD");
	}
	
	public String getReportMail()
	{
		return pro.getProperty("REPORT_MAIL_ID");
	}
	
	public String getReportPassword()
	{
		return pro.getProperty("REPORT_MAIL_PASSWORD");
	}
	
	public String getToMail()
	{
		return pro.getProperty("ADD_TO_MAIL");
	}
	
	public String getFromMail()
	{
		return pro.getProperty("ADD_FROM_MAIL");
	}
	
	public String getToName()
	{
		return pro.getProperty("ADD_TO_NAME");
	}
	
	public String getFromName()
	{
		return pro.getProperty("ADD_FROM_NAME");
	}
	
	public String getReportName()
	{
		return pro.getProperty("REPORT_NAME");
	}
	
	public String getReportMsg()
	{
		return pro.getProperty("REPORT_MSG");
	}
	
	public String getHostName()
	{
		return pro.getProperty("MAILHOST");
	}
	
	public String getPortName()
	{
		return pro.getProperty("MAILPORT");
	}
	
	public String getMyAccount()
	{
		return pro.getProperty("MY_ACCOUNT");
	}

}
