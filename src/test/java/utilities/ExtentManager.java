package utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentManager {
    public static ExtentReports extent;

    public static ExtentReports createInstance() {

        String fileName = System.getProperty("user.dir") + "/reports/ExtentReport.html";

        ExtentSparkReporter htmlReporter = new ExtentSparkReporter(fileName);

        htmlReporter.config().setTheme(Theme.STANDARD);
        htmlReporter.config().setDocumentTitle("Pepperfry Automation Report");
        htmlReporter.config().setReportName("Test Execution Results");

        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
        extent.setSystemInfo("Tester", "Shyam");
        extent.setSystemInfo("Project", "Pepperfry Hybrid Framework");

        return extent;
    }
}