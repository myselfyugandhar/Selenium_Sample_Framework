package Utility_Methods;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;

import Utility_Methods.CSVFileWriter;
import Utility_Methods.InitialSetup;
import Utility_Methods.ListOfInstancesDataProviderClass;
import Utility_Methods.Utilitymethods;
import Utility_Methods.WordDocument;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.xmlbeans.XmlException;

import java.io.IOException;
import java.sql.DriverManager;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.openqa.selenium.support.PageFactory;
public class BaseTest {
	public WebDriver driver;
	protected CSVFileWriter CSVWriterNew;
	Object[][] objCSVWebDriver = null;
	InitialSetup objSetup = new InitialSetup();
	private String VersionNumber;
	private String ApplicationName;
	private String Region;
	private String Env;
	private String URL;
	private String ReleaseMonth;
	public String DashboardName = "";
	private String Type_of_Testing="NA";
	public static String execution_start_time;
	public String outputCsvFileName;
	public String callerClassName;
	 protected XWPFDocument doc;
	protected String outputDocName;
	public String TestResultsDocPath,ReportPath,TestResultsScreenshotsPath;
	public static String ExtentReportPath,FailedScreenshotsPath;
	FileInputStream inputStream ;
	XSSFWorkbook workbook ;
	protected XSSFSheet SheetName;
	protected XSSFSheet SheetName2;
		
	//*** Calling Data Array from List of Instances input sheet ***
	//@Factory(dataProviderClass = ListOfInstancesDataProviderClass.class, dataProvider = "dataMethod")
	public BaseTest(String VersionNumber, String ApplicationName, String Region, String Env,String URL,
		String Username, String Password, String ReleaseMonth,String Type_of_Testing) throws IOException {
		this.VersionNumber = VersionNumber;
		this.ApplicationName = ApplicationName;
		this.Region = Region;
		this.Env = Env;
		this.URL = URL;
		this.ReleaseMonth=ReleaseMonth;
		
		//Getting callerclass details
		java.lang.StackTraceElement[] trace = (new Throwable()).getStackTrace();
		if (trace.length >= 2) {
			 callerClassName = trace[1].getClassName();
		}
		
	}
	
	//@Parameters("Browser")
	@BeforeClass
		public void launchUrl( ) throws InterruptedException, IOException {String Browser="Chrome";
		 inputStream = new FileInputStream(System.getProperty("user.dir")+"/Resources/InputData/InputSheet.xlsx");
		workbook = new XSSFWorkbook(inputStream);
		 SheetName= workbook.getSheet("SearchYugandharRoyal");
		SheetName2= workbook.getSheet("SearchCheGuevara");
		
	
	
	
		//Setting up Path for Test Results Word Document
		this.TestResultsDocPath = System.getProperty("user.dir")+"/Test Results/Test Results_ "+execution_start_time+"/Word Document/";
        File file1 = new File(TestResultsDocPath);
        if (!file1.exists()) {
        	try {
        	file1.mkdirs();System.out.println("Path for Word Document Test Results : "+TestResultsDocPath);}
        	catch(Exception e) {
        		System.out.println("Failed to create Path for Word Document Test Results");}
        }
        
        
      //Setting up Path for Test Results CSV Report
        this.ReportPath = System.getProperty("user.dir")+"/Test Results/Test Results_ "+execution_start_time+"/CSV Report/";
        File file2 = new File(ReportPath);
        if (!file2.exists()) {
        	try{
        		file2.mkdirs();
        		System.out.println("Path for CSV Report Test Results : "+ReportPath);
        	}catch(Exception e) {
        		System.out.println("Failed to create Path for CSV Report Test Results");}	
        }
        
        
        //Setting up Path for Test Results Screenshots
        this.TestResultsScreenshotsPath = System.getProperty("user.dir")+"/Test Results/Test Results_ "+execution_start_time+"/Screenshots/";
        File file3 = new File(TestResultsScreenshotsPath);
        if (!file3.exists()) {WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.geeksforgeeks.org/");
        Thread.sleep(5000);
        String text = driver.findElement(By.xpath("/html/body/div[2]/div/div/div/div[1]/div[1]/div[1]")).getText();
        System.out.println(text);
        driver.quit();
        	try{
        	file3.mkdirs();
        	System.out.println("Path for Screenshots Test Results : "+TestResultsScreenshotsPath);}
        	catch(Exception e) {
        		System.out.println("Failed to create Path for Screenshots Test Results");
        	}
        }
        
      
        
        
        
      
        
        
        
		//pumping common data
		try {
			objCSVWebDriver = objSetup.setupNew(VersionNumber, ApplicationName, Region, Env, URL, ReleaseMonth,Type_of_Testing, Browser);

		} catch (Exception e) {
			e.printStackTrace();
		}
		driver = (WebDriver) objCSVWebDriver[0][0];
		CSVWriterNew = (CSVFileWriter) objCSVWebDriver[0][1];
		System.out.println("Before class has been executed successfully for "+callerClassName+" class");
	}	


	
	
	
		@AfterMethod
	public void ExecutionTimeForTest(ITestResult result) throws InterruptedException, IOException, XmlException {
		   double milliseconds = result.getEndMillis()-result.getStartMillis();
		   double divident = 60000;
		  double minutes = milliseconds/divident;
		  DecimalFormat f = new DecimalFormat("0.0");
		 String RoundOffminutes = f.format(minutes);
		   System.out.println("Time taken to run "+outputDocName+" is :"+ RoundOffminutes+" minutes");
		   WordDocument.closingWordDoc(doc,outputDocName,TestResultsDocPath);
		   CSVWriterNew.writeCsvList( "Time taken to Run the Test", "Time taken to Run the Test","Time taken to Run the Test","Time taken to Run the Test is :",RoundOffminutes+" Minutes", "Pass");
		}
	

	
	@AfterClass(alwaysRun = true)

	public void closeBrowser() {	String Browser="Chrome";		
		try {			
			objSetup.tearDown(outputCsvFileName,driver, CSVWriterNew, ApplicationName, Region, Env, ReleaseMonth, DashboardName,Browser,execution_start_time,ReportPath);

		} finally 		{

			System.out.println("After class has been executed successfully for "+callerClassName+" class");
}
	}
}





