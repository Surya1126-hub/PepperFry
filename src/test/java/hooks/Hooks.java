
package hooks;

import base.BaseClass;
import io.cucumber.java.After;
import io.cucumber.java.AfterAll;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.Scenario;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

public class Hooks {

    private static final Logger logger = LogManager.getLogger(Hooks.class);

    @BeforeAll
    public static void setup() {
        logger.info(">>> Hook: Launching Browser...");
        BaseClass.initializeDriver();
        BaseClass.getDriver().manage().deleteAllCookies();

    }

    @AfterAll
    public static void tearDown() {
   
        logger.info(">>> Hook: Closing Browser...");
        BaseClass.quitDriver();
    }
    
    @AfterStep
    public void addScreenShot(Scenario scenario) {
    	
    	 if (scenario.isFailed()) {
             logger.error("Scenario '{}' failed. Capturing screenshot...", scenario.getName());
             TakesScreenshot ts = (TakesScreenshot) BaseClass.getDriver();
             byte[] screenshot = ts.getScreenshotAs(OutputType.BYTES);
             scenario.attach(screenshot, "image/png", "Failed Screenshot");
             logger.debug("Screenshot attached to scenario report");
         } else {
             logger.info("Scenario '{}' passed successfully", scenario.getName());
         }
    	
    }
    @Before
    public void beforeScenario() {
        System.out.println("Starting scenario...");
    }

    @After
    public void afterScenario() {
        System.out.println("Ending scenario...");
    }
    

}

