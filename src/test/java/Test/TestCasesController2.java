package Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.ITestResult;
import org.testng.annotations.*;

import POM.GoogleSearch2;
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
public class TestCasesController2 extends BaseTest {
	
	 

	@Factory(dataProviderClass = ListOfInstancesDataProviderClass.class, dataProvider = "dataMethod")
	
	public TestCasesController2(String VersionNumber, String ApplicationName, String Region, String Env, String URL,
			String Username, String Password, String ReleaseMonth,String Type_of_Testing) throws IOException {
		super(VersionNumber, ApplicationName, Region, Env, URL, Username, Password, ReleaseMonth,Type_of_Testing);
		// TODO Auto-generated constructor stub
	}

	@Test(priority=10)
	public void CheGuevara_Search() throws Exception{
		this.outputDocName="Che Guevara_Search_Result";
		this.outputCsvFileName="Che Guevara_Search_Result";	
	this.doc=WordDocument.CreatingWordDocument("Che Guevara_Search_Result");
			GoogleSearch2 obj =new GoogleSearch2(driver,CSVWriterNew,doc);
			SheetsQuickstart.main1();
			//obj.GoogleSearch(SheetName2,execution_start_time,TestResultsDocPath,TestResultsScreenshotsPath);

	}
	
	
}





