package Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.ITestResult;
import org.testng.annotations.*;

import POM.GoogleSearch;
import Utility_Methods.BaseTest;
import Utility_Methods.CSVFileWriter;
import Utility_Methods.InitialSetup;
import Utility_Methods.ListOfInstancesDataProviderClass;
import Utility_Methods.Utilitymethods;
import Utility_Methods.WordDocument;

import java.io.File;
import java.io.FileInputStream;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.xmlbeans.XmlException;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.concurrent.TimeUnit;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.openqa.selenium.support.PageFactory;
public class TestCasesController extends BaseTest {
	
	
	
	@Factory(dataProviderClass = ListOfInstancesDataProviderClass.class, dataProvider = "dataMethod")	
	public TestCasesController(String VersionNumber, String ApplicationName, String Region, String Env, String URL,
			String Username, String Password, String ReleaseMonth,String Type_of_Testing) throws IOException {
		super(VersionNumber, ApplicationName, Region, Env, URL, Username, Password, ReleaseMonth,Type_of_Testing);
		// TODO Auto-generated constructor stub 
	}

	// **************** UI Validation ****************
	@Test(priority=9,enabled=true)
	public void SearchForYugandharRoyal() throws Exception{Thread.sleep(5000);
	this.outputDocName="Yugandhar_Royal_Search_Result";	
	this.outputCsvFileName="Yugandhar_Royal_Search_Result";	
	this.doc=WordDocument.CreatingWordDocument("Yugandhar_Royal_Search_Result");
			GoogleSearch obj =new GoogleSearch(driver,CSVWriterNew,doc);
			obj.GoogleSearch(SheetName,execution_start_time,TestResultsDocPath,TestResultsScreenshotsPath);
			
	}
	
	
}





