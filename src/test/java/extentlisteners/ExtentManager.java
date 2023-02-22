package extentlisteners;
import java.io.File;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import Utility_Methods.BaseTest;
import Utility_Methods.Utilitymethods;
public class ExtentManager {
private static ExtentReports extent;
public static ExtentReports createInstance(String fileName) {

			//Fetching Execution Start Date and Time
			Utilitymethods obj = new Utilitymethods();
			BaseTest.execution_start_time=obj.getCurrentDate();
			  //Setting up Path for Test Results Extent Reports
	       BaseTest. ExtentReportPath = System.getProperty("user.dir")+"/Test Results/Test Results_ "+BaseTest.execution_start_time+"/Extent Report & Failed Tests Screenshots/";
		        File file4 = new File(BaseTest. ExtentReportPath);
		        if (!file4.exists()) {
		        	try{
		        		file4.mkdirs();
		        		System.out.println("Path for Extent Report & Failed Test Results Screenshotss: "+BaseTest. ExtentReportPath);
		        	}catch(Exception e) {
		        		System.out.println("Failed to create Path for Extent Report Test Results");}	
		        }
		
	    	
	    	
	    	
	        ExtentSparkReporter htmlReporter = new ExtentSparkReporter( BaseTest. ExtentReportPath+fileName);	        
	        htmlReporter.config().setTheme(Theme.STANDARD);
	        htmlReporter.config().setDocumentTitle(fileName);
	        htmlReporter.config().setEncoding("utf-8");
	        htmlReporter.config().setReportName(fileName);
	        
	        extent = new ExtentReports();
	        extent.attachReporter(htmlReporter);
	        extent.setSystemInfo("Tester Name", "Yugandhar Royal");
	        extent.setSystemInfo("Tested on",BaseTest.execution_start_time);

	        
	        
	        return extent;
	    }

	    

	

	}
