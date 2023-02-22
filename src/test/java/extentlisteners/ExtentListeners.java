package extentlisteners;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;

import Utility_Methods.BaseTest;
import Utility_Methods.InitialSetup;
import Utility_Methods.Utilitymethods;
public class ExtentListeners implements ITestListener {
	static Date d = new Date();
	static String fileName = "ExtentReport" + ".html";
	//private static ExtentReports extent = ExtentManager.createInstance(System.getProperty("user.dir")+"\\reports\\"+fileName);	
	private static ExtentReports extent = ExtentManager.createInstance(fileName);
	private static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<ExtentTest>();
	ExtentTest test;
	public void onTestStart(ITestResult result) {
	 test = extent.createTest(result.getTestClass().getName()+"     @TestCase : "+result.getMethod().getMethodName());
       extentTest.set(test);
	}

	public void onTestSuccess(ITestResult result) {

		String methodName=result.getMethod().getMethodName();
		String logText="<b>"+"TEST CASE:- "+ methodName.toUpperCase()+ " PASSED"+"</b>";		
		Markup m=MarkupHelper.createLabel(logText, ExtentColor.GREEN);
		 extentTest.get().pass(m);
		

	}

	public void onTestFailure(ITestResult result) {

		
		
		String excepionMessage=Arrays.toString(result.getThrowable().getStackTrace());
		extentTest.get().fail("<details>" + "<summary>" + "<b>" + "<font color=" + "red>" + "Exception Occured:Click to see"
				+ "</font>" + "</b >" + "</summary>" +excepionMessage.replaceAll(",", "<br>")+"</details>"+" \n");
		
		InitialSetup obj = new InitialSetup();
		String methodName=result.getMethod().getMethodName();
		String screenshotName =methodName+"_Failed Screenshot"+ ".jpg";
		obj.captureFailedScreenshot(screenshotName);
		extentTest.get().fail("<b>" + "<font color=" + "red>" + "Screenshot of failure" + "</font>" + "</b>",
				MediaEntityBuilder.createScreenCaptureFromPath(obj.screenshotName)
						.build());
		
		String failureLogg="<b>"+"TEST CASE:- "+ methodName+ " FAILED"+"</b>";	
		
		Markup m = MarkupHelper.createLabel(failureLogg, ExtentColor.RED);
		extentTest.get().log(Status.FAIL, m);

	}

	public void onTestSkipped(ITestResult result) {
		String methodName=result.getMethod().getMethodName();
		String logText="<b>"+"Test Case:- "+ methodName+ " Skipped"+"</b>";		
		Markup m=MarkupHelper.createLabel(logText, ExtentColor.YELLOW);
		extentTest.get().skip(m);

	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub

	}

	public void onStart(ITestContext context) {

		

	}

	public void onFinish(ITestContext context) {

		if (extent != null) {

			extent.flush();
		}

	}

}
