package Utility_Methods;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import java.io.InputStream;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

public class InitialSetup{

	 protected static   ThreadLocal<ChromeDriver> driver = new ThreadLocal<>();
	
	public Object[][] setupNew(String VersionNumber, String ApplicationName, String Region, String Env,
			String URL, String ReleaseMonth,String Type_of_Testing, String Browser) throws MalformedURLException, InterruptedException{
		
		Properties prop = new Properties();
		File file = new File("Resources/Env.properties");  
		FileInputStream fileInput = null;
		try {
			fileInput = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();}
	try {
			prop.load(fileInput);
		} catch (IOException e) {
			e.printStackTrace();}
		
		
		Object[][] objCSVWebDriver = new Object[1][2];
		CSVFileWriter CSVFileWriterNew = new CSVFileWriter();
		CSVFileWriterNew.setCommonData(URL,VersionNumber,Region,Env,ApplicationName,ReleaseMonth,Type_of_Testing);
		
		 
		if (prop.getProperty("GRID").equalsIgnoreCase("No")) {
			try {
				if (Browser.equals("Chrome")) {
					if (prop.getProperty("PLATFORM").contains("win")) {
						System.setProperty("webdriver.chrome.driver",
								System.getProperty("user.dir") + "/Resources/Drivers/chromedriver.exe");
					} else if (prop.getProperty("PLATFORM").contains("LINUX")) {
						System.setProperty("webdriver.chrome.driver",
								System.getProperty("user.dir") + "/Resources/Drivers/LINUX/chromedriver");
					}
							driver.set(new ChromeDriver());
					driver.get().get(URL);
					driver.get().manage().window().maximize();

				}
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		}
	
			
		
    	objCSVWebDriver[0][0]=driver.get();
    	objCSVWebDriver[0][1]=CSVFileWriterNew;
		return objCSVWebDriver;
	}
public String screenshotName;
public  void captureFailedScreenshot(String screenshotName) {	this.screenshotName=screenshotName;
	  BaseTest. ExtentReportPath = System.getProperty("user.dir")+"/Test Results/Test Results_ "+BaseTest.execution_start_time+"/Extent Report & Failed Tests Screenshots/";
		File scrFile = ((TakesScreenshot)driver.get()).getScreenshotAs(OutputType.FILE);
		Date d = new Date();
		//screenshotName = d.toString().replace(":", "_").replace(" ", "_") + ".jpg";

		try {
			FileUtils.copyFile(scrFile, new File(BaseTest. ExtentReportPath + screenshotName));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	
	}
	 
	 //Quitting Browser CSV Commands
	public void tearDown(String fileName,WebDriver driver,CSVFileWriter CSVWriterNew,String ApplicationName, String Region, String Env, String ReleaseMonth, String DashboardName, String Browser,String execution_start_time,String ReportPath)
	 {
		try{
			driver.quit();
		    CSVWriterNew.writeCsvList("Quit Browser", "Validate Quit Browser", "Exit browser",Browser+ " browser should quit",Browser+" browser has quit", "Pass");	
		}
		 catch(Exception e) 
		 {
		 CSVWriterNew.writeCsvList("Quit Browser", "Validate Quit Browser", "Exit browser",Browser+ " browser should quit",Browser+" browser has quit", "Fail");
		}
		 
		finally{
			CSVWriterNew.writeCsvFile(fileName,ReportPath);
		}
	  }		
}

