package pages;

import org.openqa.selenium.*;
import org.apache.logging.log4j.LogManager; 
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import freemarker.template.SimpleDate;
import utilities.ExcelWriter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.List;

public class GiftCards {
    private WebDriver driver;
    private WebDriverWait wait;
    private static final Logger logger = LogManager.getLogger(GiftCards.class);


    private By hbd = By.xpath("//img[contains(@alt,'Birthday')]"); // Check if this index [8] is consistent for your screen size
    // Note: Sometimes indexes change. If [8] fails, we might need a better locator later.

    private By rname = By.xpath("//*[@formcontrolname='rname']");
    private By sname = By.xpath("//*[@formcontrolname='sname']");
    private By rmob = By.xpath("//*[@formcontrolname='rmob']");
    private By smob = By.xpath("//*[@formcontrolname='smob']");
    private By rmail = By.xpath("//*[@formcontrolname='rmail']");
    private By smail = By.xpath("//*[@formcontrolname='smail']");
    private By rmsg = By.xpath("//*[@formcontrolname='rmsg']");
    private By form = By.xpath("//*[@class='gc-sender-receiver-form-container']");
    private By errors = By.xpath("//*[starts-with(@class,'form-error')]");

    public GiftCards(WebDriver driver){
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        logger.info("GiftCards page object initialized");
    }

    public void selectHBD() {
        // Retry logic: If the element is "stale" (old), find it again and click.
        for (int i = 0; i < 3; i++) { // Try 3 times
            try {
                // 1. Wait for it to be clickable
                WebElement hbdEle = wait.until(ExpectedConditions.elementToBeClickable(hbd));

                // 2. Try to click
                ((JavascriptExecutor) driver).executeScript("arguments[0].click()", hbdEle);

                // 3. If successful, break the loop
                break;
            } catch (StaleElementReferenceException e) {
                // 4. If failed, print message and try again
                System.out.println("Element became stale. Retrying click... attempt " + (i + 1));
            }
        }
    }

    public void fillReceiverDetails(String name, String mobile, String email, String message){

        WebElement rNameEle = wait.until(ExpectedConditions.visibilityOfElementLocated(rname));

        rNameEle.sendKeys(name);
        driver.findElement(rmob).sendKeys(mobile);
        driver.findElement(rmail).sendKeys(email);
        driver.findElement(rmsg).sendKeys(message);
    }

    public void fillSenderDetails(String name, String mobile, String email){
        driver.findElement(sname).sendKeys(name);
        driver.findElement(smob).sendKeys(mobile);
        driver.findElement(smail).sendKeys(email);
    }

    public void displayError(){
        // Wait for errors to appear
        wait.until(ExpectedConditions.visibilityOfElementLocated(errors));
        List<WebElement> errorList = driver.findElements(errors);
        //errorList.forEach(e -> System.out.println("Error: " + e.getText()));
        //String errorMsg=e.getText();
        for(WebElement e : errorList) {
        	String errorMsg=e.getText();
        	System.out.println("Error:" + errorMsg);
        
        try {
        	ExcelWriter.appendRow("output1.xlsx","GiftCardErrors",new Object[] {errorMsg});
        }catch(IOException io) {
        	io.printStackTrace();
        }
        }
    }

    public void screenshot() {
        try {
            // Wait for form to be visible for screenshot
            WebElement formEle = wait.until(ExpectedConditions.visibilityOfElementLocated(form));

            String folderPath ="C:\\Users\\2461930\\Downloads\\Displaybookshelves 1\\Displaybookshelves\\screenshots";
            File folder = new File(folderPath);
            if (!folder.exists()) {
                folder.mkdirs();
            }
            File srcFile = formEle.getScreenshotAs(OutputType.FILE);

            Date date = new Date();  // current date & time
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");

            String timeStamp = formatter.format(date);

            File trgFile = new File(folderPath + File.separator + "error_" + timeStamp + ".png");

            srcFile.renameTo(trgFile);
            System.out.println("Screenshot saved to: " + trgFile.getAbsolutePath());
        } catch (Exception e) {
            System.out.println("Screenshot failed: " + e.getMessage());
        }
    }
    

    


    
  

}