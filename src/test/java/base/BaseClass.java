
package base;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import utilities.ConfigReader;

import java.time.Duration;

public class BaseClass {
    private static final Logger logger = LogManager.getLogger(BaseClass.class);

    private static WebDriver driver;

    public static void initializeDriver() {
        String browserName = ConfigReader.getProperty("browser").toLowerCase();
        logger.info("Initializing WebDriver for browser: {}", browserName);

        switch (browserName) {
            case "chrome":
                driver = new ChromeDriver();
                logger.debug("ChromeDriver initialized");
                break;
            case "firefox":
                driver = new FirefoxDriver();
                logger.debug("FirefoxDriver initialized");
                break;
            case "edge":
                driver = new EdgeDriver();
                logger.debug("EdgeDriver initialized");
                break;
            default:
                driver = new ChromeDriver();
                logger.warn("Unknown browser specified, defaulting to ChromeDriver");
                break;
        }

        driver.manage().window().setSize(new Dimension(1920, 1080));
        int waitTime = Integer.parseInt(ConfigReader.getProperty("implicitWait"));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(waitTime));
        logger.info("Driver window set to 1920x1080 and implicit wait set to {} seconds", waitTime);
    }

    public static WebDriver getDriver() {
        logger.debug("Returning WebDriver instance");
        return driver;
    }

    public static void quitDriver() {
        if (driver != null) {
            logger.info("Quitting WebDriver");
            driver.quit();
            driver = null;
            logger.debug("WebDriver set to null after quit");
        } else {
            logger.warn("quitDriver called but driver was already null");
        }
    }
}
