package generic;

import java.io.FileInputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class Utilities {

	public static String captureScreenShot(WebDriver driver)
	{
		return (String)((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);	
	}

	public static String[][] getExcelData(String fileLocation,String sheetName)
	{
		String[][] data=null;
		try {
			FileInputStream fis=new FileInputStream(fileLocation);
			XSSFWorkbook wb=new XSSFWorkbook(fis);
			XSSFSheet sheet=wb.getSheet(sheetName);
			Row row = sheet.getRow(0);
			int noOfRows = sheet.getPhysicalNumberOfRows();
			int noOfCells = row.getLastCellNum();
			Cell cell;
			data=new String[noOfRows-1][noOfCells];

			for (int i =1; i < noOfRows; i++) {
				for (int j = 0; j < noOfCells; j++) {
					row=sheet.getRow(i);
					cell=row.getCell(j);
					data[ i-1][j]=cell.getStringCellValue();
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;		
	}
}